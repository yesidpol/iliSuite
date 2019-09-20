package mvc.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import actions.validateData.FinishDataValidation;
import actions.validateData.ValidateOptionsWizard;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import mvc.enums.EnumProcess;
import mvc.view.BaseItemWizard;
import mvc.view.Wizard;
import view.general.GeneralLayoutWizard;
import view.general.MainOptions;

public class MainController implements SelectProcess {
	private BorderPane contentPane;
	public void startGui(Stage primaryStage) {
		try {
			GeneralLayoutWizard mainViewController = new GeneralLayoutWizard();		
			
			// FIX concrete type
			MainOptions mainOptions = new MainOptions();
			mainOptions.setController(this);
			
			// FIK specific code
			contentPane = mainViewController.getContentPane();		
			//contentPane.setCenter(mainOptions.getGraphicComponent());

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
			primaryStage.getIcons().addAll(icons);
			// FIX to load first screen
			
			Wizard validateData = new Wizard();
			validateData.add(new ValidateOptionsWizard());
			validateData.add(new FinishDataValidation());
			
			validateData.init();
			contentPane.setCenter(validateData.getGraphicComponent());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setProcess(EnumProcess process) {
		System.out.println(process.name());
		
		// FIX 
		try {
			Wizard validateData = new Wizard();
			
			
			contentPane.setCenter(validateData.getGraphicComponent());
			BaseItemWizard validateOptions = new ValidateOptionsWizard();
			
			validateData.add(validateOptions);
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
