package view.general;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import application.data.AppData;
import application.data.Config;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import menu.dialog.AboutDialog;
import menu.dialog.HelpDialog;
import menu.dialog.PreferencesController;
import menu.dialog.ProxyDialog;
import view.dialog.ModelDirDialog;
import view.util.navigation.EnumPaths;
import view.util.navigation.Navigable;
import view.util.navigation.NavigationUtil;
import view.util.navigation.ResourceUtil;
import view.util.navigation.VisualResource;

public class GeneralLayoutController implements Navigable, Initializable {

	@FXML
	private Button btnBack;
	@FXML
	private Button btnNext;
	@FXML
	private Button btnCancel;
	@FXML
	private AnchorPane generalLayout;
	@FXML
	private ImageView iv_functionIcon;
	@FXML
	private Label lbl_functionTitle;
	private ResourceBundle bundle;
	private Thread commandExecutionThread;

	public void goForward(ActionEvent e) throws IOException {
		Navigable currentController = (Navigable) NavigationUtil.getCurrentScreen().getController();
		EnumPaths nextPath = currentController.getNextPath();
		boolean isFinalPage = currentController.isFinalPage();
		if(nextPath!=null && !btnCancel.getText().equals(bundle.getString("buttons.cancel")))
			btnCancel.setText(bundle.getString("buttons.cancel"));

		if (isFinalPage && btnNext.getText().equals(bundle.getString("buttons.finish"))) {

			NavigationUtil.setFirstScreen();
			
			changeTitle(EnumPaths.APP_ICON, bundle.getString("main.function.home.title"));
			changeNextButtonLabel(false);
			btnCancel.setText(bundle.getString("buttons.exit"));
		} else if(isFinalPage && !btnNext.getText().equals(bundle.getString("buttons.finish"))){
			SimpleBooleanProperty booleanResult = new SimpleBooleanProperty();
			Task<Boolean> task = new Task<Boolean>(){
				@Override
				protected Boolean call() throws Exception {

					boolean isValid = currentController.validate();
					return isValid;
				}
			};
			task.setOnSucceeded(workerStateEvent ->{
				btnNext.setDisable(false);
				if(booleanResult.getValue()==true){
					btnNext.setText(bundle.getString("buttons.finish"));
				}
				});
			booleanResult.bind(task.valueProperty());
			btnNext.setDisable(true);
			
			commandExecutionThread = new Thread(task);
			commandExecutionThread.start();			

		}else {
			boolean isValid = currentController.validate();
			if (isValid && isFinalPage) {
				btnNext.setText(bundle.getString("buttons.finish"));
			} else if (isValid && nextPath != null && !isFinalPage) {
				VisualResource nextScreen = ResourceUtil.loadResource(getClass(), nextPath,EnumPaths.RESOURCE_BUNDLE);
				NavigationUtil.setNextScreen(nextScreen);
				Navigable nextController = (Navigable) nextScreen.getController();
				changeNextButtonLabel(nextController.isFinalPage());
				
				switch(nextPath){
				case OPEN_UML_EDITOR: 
					changeTitle(EnumPaths.UMLEDITOR_ICON, bundle.getString("main.function.openUml.title"));
					break;
				case VAL_DATA_VALIDATE_OPTIONS: 
					changeTitle(EnumPaths.VALIDATE_ICON, bundle.getString("main.function.validateData.title"));
					break;
				case ILI2DB_COMMON_DATABASE_SELECTION:
					switch(AppData.getInstance().getActionIli2Db()){
					case IMPORT_SCHEMA:
						changeTitle(EnumPaths.GENERATEPHYSICALMODEL_ICON, bundle.getString("main.function.generatePhysicalModel.title"));
						break;
					case IMPORT:
						changeTitle(EnumPaths.IMPORT_ICON, bundle.getString("main.function.importData.title"));
						break;
					case EXPORT:
						changeTitle(EnumPaths.EXPORT_ICON, bundle.getString("main.function.exportData.title"));
						break;
					}
				}
			}
		}

	}

	public void goBack() {
		if (NavigationUtil.getStepStack().size() > 1) {
			NavigationUtil.setPreviousScreen();
			Navigable currentController = (Navigable) NavigationUtil.getCurrentScreen().getController();
			if(NavigationUtil.getStepStack().size()==1){
				changeTitle(EnumPaths.APP_ICON, bundle.getString("main.function.home.title"));
				btnCancel.setText(bundle.getString("buttons.exit"));
			}
			changeNextButtonLabel(currentController.isFinalPage());
			if(commandExecutionThread!=null) {
				commandExecutionThread.stop();
				btnNext.setDisable(false);
				commandExecutionThread=null;
			}
		}
	}

	public void cancel() {
		if(NavigationUtil.getStepStack().size()>1){
			NavigationUtil.setFirstScreen();
			
			changeTitle(EnumPaths.APP_ICON, bundle.getString("main.function.home.title"));
			changeNextButtonLabel(false);
			btnCancel.setText(bundle.getString("buttons.exit"));
			if(commandExecutionThread!=null) {
				commandExecutionThread.stop();
				btnNext.setDisable(false);
				commandExecutionThread=null;
			}
		}else{
			Stage s = (Stage) NavigationUtil.getMainScreen().getComponent().getScene().getWindow();
			s.close();
		}
	}

	@Override
	public boolean validate() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public EnumPaths getNextPath() {
		return null;
	}

	@Override
	public boolean isFinalPage() {
		return false;
	}

	private void changeNextButtonLabel(boolean isFinal) {

		if (isFinal) {
			btnNext.setText(bundle.getString("buttons.execute"));
		} else {
			btnNext.setText(bundle.getString("buttons.next"));
		}
	}

	public void onClick_MenuItemModelDir() {
		try {
			ModelDirDialog dialog = new ModelDirDialog();
			dialog.setTitle(bundle.getString("dialog.modeldirdefault.title"));

			Config config = Config.getInstance();

			if (config.getModelDir() != null && !config.getModelDir().isEmpty())
				dialog.setData(Arrays.asList(config.getModelDir().split(";")));

			Optional<List<String>> result = dialog.showAndWait();

			if (result.isPresent()) {
				config.setModelDir(String.join(";", result.get()));
				config.saveToFile();
			}

			// return controller.isOkButton();
		} catch (IOException E) {
			E.printStackTrace();
			// return false;
		}
	}
	
	public void onClick_MenuItemPreferences() {
		try {
			Config config = Config.getInstance();

			PreferencesController dialog = new PreferencesController();

			dialog.setTitle(bundle.getString("menu.item.preferences"));
			Optional<Boolean> result = dialog.showAndWait();

		} catch (IOException E) {
			// TODO Unimplemented
			E.printStackTrace();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		bundle = ResourceBundle.getBundle(EnumPaths.RESOURCE_BUNDLE.getPath());
		
		//TODO cambiar a una mejor forma de cargar, se pone de manera temporal
		changeTitle(EnumPaths.APP_ICON,bundle.getString("main.function.home.title"));
       
	}

	public void onClick_MenuItemProxyConf() {
		try {
			Config config = Config.getInstance();

			ProxyDialog dialog = new ProxyDialog(config.getProxyHost(), config.getProxyPort());
			dialog.setTitle(bundle.getString("dialog.proxy.title"));

			Optional<String> result = dialog.showAndWait();

			if (result.isPresent()) {
				config.setProxyHost(dialog.getProxyHost());
				config.setProxyPort(dialog.getProxyPort());
				config.saveToFile();
			}

		} catch (IOException E) {
			// TODO Unimplemented
			E.printStackTrace();
		}
	}
	
	@FXML
	private void onClick_MenuItemHelpContents(){
		HelpDialog dialog;
		try {
			dialog = new HelpDialog();
			dialog.setTitle(bundle.getString("dialog.help.title"));
			dialog.initModality(Modality.NONE);
			dialog.initStyle(StageStyle.DECORATED);
			dialog.resizableProperty().setValue(true);
			dialog.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	private void onClick_MenuItemAbout() throws IOException{
		AboutDialog about = new AboutDialog();
		about.setTitle(bundle.getString("menu.item.about"));
		about.show();
	}
	
	
	private void changeTitle(EnumPaths iconPath, String title){
		//TODO cambiar a una mejor forma de cargar, se pone de manera temporal
		lbl_functionTitle.setText(title);

		String url = getClass().getResource(iconPath.getPath()).toExternalForm();
		Image image = new Image(url);
		iv_functionIcon.setImage(image);
	}

}