package application;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import application.data.Config;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import mvc.controller.MainController;
import util.exception.IliSuiteSecurityManager;
import util.plugin.PluginsLoader;
import view.util.navigation.EnumPaths;
import view.util.navigation.NavigationUtil;
import view.util.navigation.ResourceUtil;
import view.util.navigation.VisualResource;

public class Main extends Application {

	private final String configFileName = ".config.properties";
	private final String defaultConfigFileName = ".defaultConfig.properties";
	private final String logDirName = "log";
	private final String logAppDirName = "log";
	private final String iliSuiteDirName = ".ilisuite";
	
	private MainController mainController = null;
	
	@Override
	public void init() throws InterruptedException {
		System.setSecurityManager(new IliSuiteSecurityManager());
		Thread.sleep(1000 * 2);
		try {
			String ds = System.getProperty("file.separator");
			
			Config config = Config.getInstance();
			
			String iliDir = System.getProperty("user.home") + ds + iliSuiteDirName;
			config.setIliSuiteDir(iliDir);
			config.setConfigFileName(configFileName);
			config.setLogDir(iliDir + ds + logAppDirName);
			config.setLogAppDir(logAppDirName);
			
			CreateDirectoryStructureAndFiles(config);

			config.loadFromFile();

			setUserAgentStylesheet(STYLESHEET_CASPIAN);
			
			PluginsLoader.Load();
			
			String strLanguage = config.getLanguage();
			
			if(strLanguage != null) {
				Locale lan = new Locale(strLanguage);
				Locale.setDefault(lan);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		mainController = new MainController();
	}
	
	public void CreateDirectoryStructureAndFiles(Config config) throws Exception {
		
		File dirLogAppIliSuite = new File(config.getLogAppDir());
		File dirIliSuite = new File(config.getIliSuiteDir());
		File dirLog = new File(config.getLogDir());
		File configFile = new File(config.getConfigPath());
		File defaultConfigFile = new File(defaultConfigFileName);
		
		if(!dirLogAppIliSuite.exists())
			dirLogAppIliSuite.mkdirs();
			
		if(!dirIliSuite.exists())
			dirIliSuite.mkdirs();

		if(!configFile.exists()){
			if(defaultConfigFile.exists()) {
				Files.copy(defaultConfigFile.toPath(), configFile.toPath());
			} else {
				configFile.createNewFile();
			}
		}
		
		if(!dirLog.exists())
			dirLog.mkdirs();
	}

	@Override
	public void start(Stage primaryStage) {

		try {
			mainController.startGui(primaryStage);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// scene.getStylesheets().add(getClass().getResource("view/application.css").toExternalForm());

	}

	public static void main(String[] args) {
		launch(args);
	}
}
