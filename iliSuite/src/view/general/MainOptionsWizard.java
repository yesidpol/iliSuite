package view.general;

import java.io.IOException;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;
import mvc.view.WizardComponent;
import view.util.navigation.EnumPaths;
import view.util.navigation.ResourceUtil;

public class MainOptionsWizard extends WizardComponent {

	private Parent mainView;
	@FXML
	private ResourceBundle applicationBundle;

	@FXML
	private ToggleGroup tg_mainOptions;

	@FXML
	private ToggleButton btn_openUmlEditor;
	@FXML
	private ToggleButton btn_validateModel;
	@FXML
	private ToggleButton btn_generatePhysicalModel;
	@FXML
	private ToggleButton btn_importData;
	@FXML
	private ToggleButton btn_validateData;
	@FXML
	private ToggleButton btn_exportData;

	@FXML
	private Label lbl_helpTitle;
	@FXML
	private Text txt_helpContent;
	
	public MainOptionsWizard() throws IOException {
		mainView = ResourceUtil.loadResource(getClass(), EnumPaths.MAIN_OPTIONS,
		EnumPaths.RESOURCE_BUNDLE, this);
	}
	
	@Override
	public boolean goForward() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean goBack() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean cancel() {
		return true;
	}

	@Override
	public Parent getGui() {
		return mainView;
	}
	
	// old
	
	@FXML
	public void initialize() {
		applicationBundle = ResourceBundle.getBundle("resources.languages.application");
		addListenerToToggleGroup();
	}
	
	private void addListenerToToggleGroup() {
		tg_mainOptions.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			@Override
			public void changed(ObservableValue<? extends Toggle> arg0, Toggle arg1, Toggle newToggle) {
				if (newToggle == null) {
					lbl_helpTitle.setText("");
					txt_helpContent.setText("");
				} else if (newToggle == btn_openUmlEditor) {
					lbl_helpTitle.setText(applicationBundle.getString("main.openUmlEditor"));
					txt_helpContent.setText(applicationBundle.getString("main.content.openUmlEditor"));
				} else if (newToggle == btn_generatePhysicalModel) {
					lbl_helpTitle.setText(applicationBundle.getString("main.generatePhysicalModel"));
					txt_helpContent.setText(applicationBundle.getString("main.content.generatePhysicalModel"));
				} else if (newToggle == btn_importData) {
					lbl_helpTitle.setText(applicationBundle.getString("main.importOrModifyData"));
					txt_helpContent.setText(applicationBundle.getString("main.content.importOrModifyData"));
				} else if (newToggle == btn_validateData) {
					lbl_helpTitle.setText(applicationBundle.getString("main.validateData"));
					txt_helpContent.setText(applicationBundle.getString("main.content.validateData"));
				} else if (newToggle == btn_exportData) {
					lbl_helpTitle.setText(applicationBundle.getString("main.exportData"));
					txt_helpContent.setText(applicationBundle.getString("main.content.exportData"));
				}

			}
		});
	}
}
