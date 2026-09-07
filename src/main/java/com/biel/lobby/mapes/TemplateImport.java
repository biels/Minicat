package com.biel.lobby.mapes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import net.kyori.adventure.util.TriState;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;

import com.biel.lobby.Com;

/**
 * Brings a template in the pre-26.2 world format into the format Paper 26.2 stores
 * worlds in, using Paper's own importer. That importer only runs inside
 * {@code createWorld}, on a folder at the server root named after the world and
 * carrying a {@code level.dat}: the template is copied there under a fixed staging
 * key, loaded once, unloaded, and the folder Paper produced becomes the template. The original moves
 * to {@code mapes-originals/} untouched.
 *
 * The order keeps a complete template on disk at every instant: the imported folder
 * is finished (UUID removed, properties copied in) before the original is moved
 * away, and the two moves are renames. A crash between them leaves the original in
 * the archive with nothing in its place, which {@link #restoreInterrupted()} undoes
 * at the next start.
 */
public final class TemplateImport {
	private static final String StagingWorldName = "TemplateImport";
	private static final String OriginalMarker = "level.dat";
	private static final int MoveAttempts = 3;
	private static final String ArchiveTimestampFormat = "yyyyMMdd-HHmmss";
	/** An archive pushed aside by a later import of the same template; never restored. */
	private static final Pattern SupersededArchive = Pattern.compile(".*-\\d{8}-\\d{6}$");

	private TemplateImport() {
	}

	/** A template still in the pre-26.2 format: a world folder with its own level.dat. */
	static boolean isOriginal(File template) {
		return new File(template, OriginalMarker).isFile();
	}

	static void run(File template, File archive) throws IOException {
		long startedAt = System.currentTimeMillis();
		WorldCreator creator = new WorldCreator(MapaResetejable.liveWorldKey(StagingWorldName)).keepSpawnLoaded(TriState.FALSE);
		File stagingRoot = new File(Bukkit.getWorldContainer(), creator.name());
		clearStaging(creator, stagingRoot);
		FileUtils.copyDirectory(template, stagingRoot);
		try {
			File imported = importStaging(creator, template);
			Files.deleteIfExists(new File(imported, MapaResetejable.WorldUuidFile).toPath());
			File properties = new File(template, MapaResetejable.PropertiesFile);
			if (properties.isFile()) {
				FileUtils.copyFile(properties, new File(imported, MapaResetejable.PropertiesFile));
			}
			supersedeExistingArchive(archive);
			Files.createDirectories(archive.toPath().getParent());
			Files.move(template.toPath(), archive.toPath());
			try {
				moveWithRetries(imported, template);
			} catch (IOException moveFailure) {
				Files.move(archive.toPath(), template.toPath());
				throw moveFailure;
			}
			logger().info("Imported template " + template + " in " + (System.currentTimeMillis() - startedAt) / 1000 + "s; original kept at " + archive);
		} catch (IOException | RuntimeException failure) {
			clearStaging(creator, stagingRoot);
			throw failure;
		}
	}

	/** Loads the staged copy once so Paper imports it, and returns the folder Paper wrote. */
	private static File importStaging(WorldCreator creator, File template) throws IOException {
		World world = Bukkit.createWorld(creator);
		if (world == null) throw new IOException("Paper could not load " + template + " for import");
		File imported = world.getWorldFolder();
		if (!Bukkit.unloadWorld(world, true)) throw new IOException("Paper refused to unload the import of " + template);
		return imported;
	}

	/** Leaves no trace of an earlier import: a world still loaded, the staged copy, the imported folder. */
	private static void clearStaging(WorldCreator creator, File stagingRoot) throws IOException {
		World loaded = Bukkit.getWorld(creator.key());
		if (loaded != null && !Bukkit.unloadWorld(loaded, false)) {
			throw new IOException("A previous template import is still loaded and cannot be unloaded");
		}
		FileUtils.deleteDirectory(stagingRoot);
		FileUtils.deleteDirectory(MapaResetejable.liveWorldFolder(StagingWorldName));
	}

	/** An archive already at the target name is a previous original of the same template; it is kept under a timestamp. */
	private static void supersedeExistingArchive(File archive) throws IOException {
		if (!archive.exists()) return;
		String timestamp = new SimpleDateFormat(ArchiveTimestampFormat).format(new Date());
		File superseded = new File(archive.getParentFile(), archive.getName() + "-" + timestamp);
		Files.move(archive.toPath(), superseded.toPath());
		logger().warning("Archive " + archive + " already existed; kept as " + superseded);
	}

	private static void moveWithRetries(File source, File target) throws IOException {
		IOException lastFailure = null;
		for (int attempt = 1; attempt <= MoveAttempts; attempt++) {
			try {
				Files.move(source.toPath(), target.toPath());
				return;
			} catch (IOException failure) {
				lastFailure = failure;
				try {
					Thread.sleep(500L * attempt);
				} catch (InterruptedException interrupted) {
					Thread.currentThread().interrupt();
					throw failure;
				}
			}
		}
		throw lastFailure;
	}

	/**
	 * Puts back an original whose import was cut short between its two moves: the
	 * archive holds it and nothing sits at its place under mapes/. An archive entry is
	 * a template exactly when it carries a level.dat; a game folder without one holds
	 * its templates one level down.
	 */
	public static void restoreInterrupted() {
		File[] gameFolders = new File(MapaResetejable.FolderOriginals).listFiles(File::isDirectory);
		if (gameFolders == null) return;
		for (File gameFolder : gameFolders) {
			File gameTemplate = new File(MapaResetejable.FolderMaps, gameFolder.getName());
			if (isOriginal(gameFolder)) {
				restore(gameFolder, gameTemplate);
				continue;
			}
			File[] mapFolders = gameFolder.listFiles(File::isDirectory);
			if (mapFolders == null) continue;
			for (File mapFolder : mapFolders) {
				if (isOriginal(mapFolder)) restore(mapFolder, new File(gameTemplate, mapFolder.getName()));
			}
		}
	}

	private static void restore(File archived, File template) {
		if (template.exists() || SupersededArchive.matcher(archived.getName()).matches()) return;
		try {
			Files.createDirectories(template.toPath().getParent());
			Files.move(archived.toPath(), template.toPath());
			logger().warning("Restored " + template + " from " + archived + ": its import had been interrupted");
		} catch (IOException failure) {
			logger().log(Level.SEVERE, "Could not restore " + template + " from " + archived, failure);
		}
	}

	private static Logger logger() {
		return Com.getPlugin().getLogger();
	}
}
