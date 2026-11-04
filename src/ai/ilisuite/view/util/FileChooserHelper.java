package ai.ilisuite.view.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import ai.ilisuite.application.data.Config;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Window;

public class FileChooserHelper {

	public static final String CAT_XTF = "xtf";
	public static final String CAT_XTF_LOG = "xtflog";
	public static final String CAT_ILI = "ili";
	public static final String CAT_SQL = "sql";
	public static final String CAT_TOML = "toml";
	public static final String CAT_GPKG = "gpkg";
	public static final String CAT_GDB = "gdb";
	public static final String CAT_PLUGINS_DIR = "pluginsdir";
	public static final String CAT_LOG_DIR = "logdir";
	public static final String CAT_MODEL_DIR = "modeldir";

	private static final String STORE_FILE_NAME = ".lastdirs.properties";

	private static Properties lastDirs;

	private FileChooserHelper() {
	}

	public static File showOpenDialog(Window owner, String category, String title, ExtensionFilter... filters) {
		FileChooser fileChooser = createFileChooser(category, title, filters);
		File selectedFile = fileChooser.showOpenDialog(owner);
		rememberSelectedFile(category, selectedFile);
		return selectedFile;
	}

	public static File showSaveDialog(Window owner, String category, String title, ExtensionFilter... filters) {
		FileChooser fileChooser = createFileChooser(category, title, filters);
		File selectedFile = fileChooser.showSaveDialog(owner);
		rememberSelectedFile(category, selectedFile);
		return selectedFile;
	}

	public static File showDirectoryDialog(Window owner, String category, String title) {
		return showDirectoryDialog(owner, category, title, null);
	}

	public static File showDirectoryDialog(Window owner, String category, String title, File fallbackDir) {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setTitle(title);

		File initialDir = getLastDirectory(category);
		if (initialDir == null && fallbackDir != null && fallbackDir.isDirectory())
			initialDir = fallbackDir;
		if (initialDir != null)
			directoryChooser.setInitialDirectory(initialDir);

		File selectedDirectory = directoryChooser.showDialog(owner);
		if (selectedDirectory != null)
			saveDirectory(category, selectedDirectory);
		return selectedDirectory;
	}

	private static FileChooser createFileChooser(String category, String title, ExtensionFilter... filters) {
		FileChooser fileChooser = new FileChooser();
		if (filters != null)
			fileChooser.getExtensionFilters().addAll(filters);
		fileChooser.setTitle(title);

		File initialDir = getLastDirectory(category);
		if (initialDir != null)
			fileChooser.setInitialDirectory(initialDir);

		return fileChooser;
	}

	public static File getLastDirectory(String category) {
		String path = getStore().getProperty(category);
		if (path != null && !path.isEmpty()) {
			File dir = new File(path);
			if (dir.isDirectory())
				return dir;
		}
		return null;
	}

	private static void rememberSelectedFile(String category, File selectedFile) {
		if (selectedFile == null)
			return;
		File dir = selectedFile.isDirectory() ? selectedFile : selectedFile.getParentFile();
		if (dir != null)
			saveDirectory(category, dir);
	}

	private static void saveDirectory(String category, File dir) {
		getStore().setProperty(category, dir.getAbsolutePath());

		File file = getStoreFile();
		File parent = file.getParentFile();
		if (parent != null && !parent.exists())
			parent.mkdirs();

		try (OutputStream out = new FileOutputStream(file)) {
			getStore().store(out, "Last used directories");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static synchronized Properties getStore() {
		if (lastDirs == null) {
			lastDirs = new Properties();
			File file = getStoreFile();
			if (file.isFile()) {
				try (InputStream in = new FileInputStream(file)) {
					lastDirs.load(in);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return lastDirs;
	}

	private static File getStoreFile() {
		String dir = Config.getInstance().getIliSuiteDir();
		if (dir == null || dir.isEmpty())
			dir = System.getProperty("user.home") + File.separator + ".ilisuite";
		return new File(dir, STORE_FILE_NAME);
	}
}
