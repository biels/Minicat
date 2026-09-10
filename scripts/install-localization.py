#!/usr/bin/env python3
"""Install copy from the built jar into a stopped Triton instance. Requires PyYAML."""
import argparse
import datetime
import json
from pathlib import Path
import shutil
import zipfile
import yaml


def install(jar_path, triton_directory, sign_bindings=None, private_catalan=None):
    config_path = triton_directory / 'config.yml'
    config = yaml.safe_load(config_path.read_text())
    with zipfile.ZipFile(jar_path) as jar:
        definition = json.loads(jar.read('i18n/languages.json'))
        resources = json.loads(jar.read('i18n/catalog-index.json'))
        # Prefix output files so ownership is explicit and unrelated collections survive.
        collections = {'minicat-' + Path(resource).name: jar.read(resource) for resource in resources}
        contracts = json.loads(jar.read('i18n/contract-index.json'))['keys']
    binding_path = sign_bindings or triton_directory / 'minicat-sign-bindings.json'
    if sign_bindings is not None and not sign_bindings.is_file():
        raise ValueError("Sign binding file does not exist")
    if binding_path.exists():
        catalog = {item['key']: item for content in collections.values() for item in json.loads(content)['items']}
        signs = []
        for binding in json.loads(binding_path.read_text()):
            lines = binding['lines']
            if len(lines) > 8 or any(key is not None and (key not in catalog or contracts[key]['arguments']) for key in lines):
                raise ValueError('Sign bindings require at most eight argument-free catalog keys')
            signs.append({'type': 'sign', 'key': 'minicat.sign.' + binding['id'], 'locations': binding['locations'],
                'lines': {entry['id']: [catalog[key]['languages'][entry['id']] if key else '' for key in lines] + [''] * (8 - len(lines)) for entry in definition['languages']}})
        collections['minicat-signs.json'] = (json.dumps({'items': signs}, ensure_ascii=False, indent=2) + '\n').encode()

    if private_catalan is None:
        private_catalan = config.get('minicat', {}).get('private-catalan', False)
    config.setdefault('minicat', {})['private-catalan'] = private_catalan
    active_languages = [entry for entry in definition['languages'] if not private_catalan or entry['id'] == 'ca_ES']
    main_language = 'ca_ES' if private_catalan else definition['default']
    existing = config.get('languages', {})
    config['languages'] = {
        entry['id']: {
            'flag': existing.get(entry['id'], {}).get('flag', 'eapwplpnpmbzbj'),
            'minecraft-code': entry['minecraftCodes'],
            'display-name': '<gold>' + entry['displayName'],
            'fallback-languages': [] if entry['id'] == main_language else [main_language],
        } for entry in active_languages
    }
    config['main-language'] = main_language
    config['force-client-locale-on-join'] = False
    config['message-parser'] = 'adventure'
    config['storage']['type'] = 'local'
    config['command-aliases'] = [alias for alias in config.get('command-aliases', []) if alias.lower() not in {'lang','language','llengua','idioma'}]
    features = config['language-creation']
    features['chat'].update({'enabled': False, 'signed-enabled': False})
    for surface in ['guis','items','actionbars','titles','signs','scoreboards']:
        features[surface]['enabled'] = True
    features['items'].update({'books': True, 'allow-in-inventory': True})
    # Minicat's canonical markers are shared across these supported packet channels.
    for surface in ['guis','items','actionbars','titles','signs','scoreboards','holograms']:
        features[surface].update({'syntax-lang': 'lang', 'syntax-arg': 'arg'})
    manifest_path = triton_directory / 'minicat-catalog-files.json'
    old_files = json.loads(manifest_path.read_text()) if manifest_path.exists() else ['minicat-common.json']
    if any(Path(name).name != name or not name.startswith('minicat-') for name in old_files):
        raise ValueError('Invalid Minicat collection ownership manifest')
    translations = triton_directory / 'translations'
    translations.mkdir(exist_ok=True)
    backup = triton_directory / ('minicat-backups/' + datetime.datetime.now(datetime.timezone.utc).strftime('%Y%m%dT%H%M%S%f'))
    backup.mkdir(parents=True, mode=0o700)
    shutil.copy2(config_path, backup / 'config.yml')
    for name in set(old_files) | set(collections):
        if (translations / name).exists(): shutil.copy2(translations / name, backup / name)
    if manifest_path.exists(): shutil.copy2(manifest_path, backup / manifest_path.name)
    saved_bindings = triton_directory / 'minicat-sign-bindings.json'
    if saved_bindings.exists(): shutil.copy2(saved_bindings, backup / saved_bindings.name)
    if sign_bindings is not None and sign_bindings.resolve() != saved_bindings.resolve(): shutil.copy2(sign_bindings, saved_bindings)
    for name, content in collections.items(): (translations / name).write_bytes(content)
    for name in set(old_files) - set(collections): (translations / name).unlink(missing_ok=True)
    config_path.write_text(yaml.safe_dump(config, allow_unicode=True, sort_keys=False))
    manifest_path.write_text(json.dumps(sorted(collections), indent=2) + '\n')
    print(f'Installed {len(collections)} collections; configuration backup: {backup}')


if __name__ == '__main__':
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('jar', type=Path)
    parser.add_argument('triton_directory', type=Path)
    parser.add_argument('--sign-bindings', type=Path, help='World locations and catalog keys; no duplicated translations')
    parser.add_argument('--server-stopped', action='store_true', required=True, help='Confirm the target server is stopped; restart after installation')
    mode = parser.add_mutually_exclusive_group()
    mode.add_argument('--private-catalan', dest='private_catalan', action='store_true', default=None)
    mode.add_argument('--public-languages', dest='private_catalan', action='store_false')
    arguments = parser.parse_args()
    install(arguments.jar, arguments.triton_directory, arguments.sign_bindings, arguments.private_catalan)
