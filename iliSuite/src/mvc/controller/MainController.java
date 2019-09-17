package mvc.controller;

import java.util.ArrayList;
import java.util.List;

import actions.validateData.ValidateOptionsWizard;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import mvc.view.ProccessWizard;
import mvc.view.WizardComponent;
import view.general.GeneralLayoutWizard;
import view.general.MainOptionsWizard;
import view.util.navigation.EnumPaths;
import view.util.navigation.ResourceUtil;

public class MainController {

	public void startGui(Stage primaryStage) {
		try {
			GeneralLayoutWizard mainViewController = new GeneralLayoutWizard();		
			WizardComponent mainOptions = new MainOptionsWizard();
			
			mainViewController.add(mainOptions);

			Scene scene = new Scene(mainViewController.getGui());
			scene.getStylesheets().add(getClass().getResource("/resources/css/styles.css").toExternalForm());			
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
			primaryStage.setTitle("iliSuite");
			
			List<Image> icons = new ArrayList<Image>();
			icons.add(new Image(getClass().getResource("/resources/images/icon128.png").toExternalForm()));
			icons.add(new Image(getClass().getResource("/resources/images/icon64.png").toExternalForm()));
			icons.add(new Image(getClass().getResource("/resources/images/icon32.png").toExternalForm()));
			icons.add(new Image(getClass().getResource("/resources/images/icon48.png").toExternalForm()));
			icons.add(new Image(getClass().getResource("/resources/images/icon16.png").toExternalForm()));
			
			// FIX to load first screen
			mainViewController.goForward();
			
			primaryStage.getIcons().addAll(icons);
			
			////
			BorderPane wrapper = mainViewController.getContentPane();
			
			ProccessWizard validateData = new ProccessWizard(wrapper);
			
			WizardComponent validateOptions = new ValidateOptionsWizard(); 
			validateData.add(validateOptions);
			
			mainViewController.add(validateData);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	
	
}
