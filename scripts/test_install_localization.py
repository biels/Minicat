"""Installer boundary tests, using a temporary Triton directory and the built jar."""
import importlib.util
import json
from pathlib import Path
import tempfile
import unittest
import yaml

spec = importlib.util.spec_from_file_location('installer', Path(__file__).with_name('install-localization.py'))
installer = importlib.util.module_from_spec(spec)
spec.loader.exec_module(installer)
JAR = Path(__file__).resolve().parents[1] / 'build/libs/minicat-1.0-SNAPSHOT.jar'


class InstallerTest(unittest.TestCase):
    def test_preserves_preferences_and_external_copy_and_remembers_sign_bindings(self):
        with tempfile.TemporaryDirectory() as temporary:
            root = Path(temporary)
            config = {'languages': {}, 'storage': {'type': 'local'}, 'language-creation': {key: {} for key in ['chat','guis','items','titles','actionbars','signs','scoreboards','holograms']}}
            (root / 'config.yml').write_text(yaml.safe_dump(config))
            (root / 'players.json').write_text('{"old-player":"es_ES"}\n')
            (root / 'translations').mkdir()
            external = root / 'translations/external.json'
            external.write_text('{"items":[]}')
            (root / 'translations/minicat-common.json').write_text('{"items":[]}')
            bindings = root / 'bindings.json'
            bindings.write_text(json.dumps([{'id':'play','locations':[{'world':'test','x':0,'y':65,'z':2}],'lines':['minicat.lobby.play']}]))
            installer.install(JAR, root, bindings)
            installer.install(JAR, root)
            self.assertEqual((root / 'players.json').read_text(), '{"old-player":"es_ES"}\n')
            self.assertEqual(external.read_text(), '{"items":[]}')
            self.assertFalse((root / 'translations/minicat-common.json').exists())
            signs = json.loads((root / 'translations/minicat-signs.json').read_text())['items']
            self.assertIn('Juga', signs[0]['lines']['ca_ES'][0])
            self.assertNotIn('[lang]', signs[0]['lines']['ca_ES'][0])
            self.assertEqual(set(yaml.safe_load((root / 'config.yml').read_text())['languages']), {'en_US','ca_ES'})


if __name__ == '__main__': unittest.main()
