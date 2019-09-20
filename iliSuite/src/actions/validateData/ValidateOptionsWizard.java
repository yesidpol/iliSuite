package actions.validateData;

import javafx.scene.Parent;
import view.util.navigation.EnumPaths;
import view.util.navigation.ResourceUtil;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import mvc.view.ItemWizard;
import mvc.view.WizardArgs;
import javafx.stage.FileChooser.ExtensionFilter;
import view.dialog.ModelDirDialog;

public class ValidateOptionsWizard extends ItemWizard implements Initializable {

	private Parent mainView;

//	old

	private ResourceBundle applicationBundle;

	@FXML
	private Button btnBrowseXtfFile;

	@FXML
	private TextField tf_XtfFile;

	@FXML
	private TextField tf_pluginsFolder;

	@FXML
	private TextField tf_modelDir;

	@FXML
	private Button btnBrowsePluginsFolder;

	@FXML
	private Button btnBrowseModelDir;

	@FXML
	private CheckBox chk_additionalFunctionality;

	@FXML
	private CheckBox chk_disableAreaValidation;

	@FXML
	private CheckBox chk_forceTypeValidation;

	@FXML
	private CheckBox chk_configFile;
	
	@FXML
	private CheckBox chk_logXtfFile;

	@FXML
	private TextField tf_configFile;

	@FXML
	private TextField tf_logXtfFile;

	@FXML
	private Button btnBrowseConfigFile;

	@FXML
	private Button btnBrowseLogXtfFile;

//	new
	
	public ValidateOptionsWizard() throws IOException {
		mainView = ResourceUtil.loadResource(getClass(), EnumPaths.VAL_DATA_VALIDATE_OPTIONS,
				EnumPaths.RESOURCE_BUNDLE, this);
	}
	
	@Override
	public void goForward(WizardArgs args) {
		super.goForward(args);
		args.setCancel(!validateFields());
	}
	@Override
	public Parent getGraphicComponent() {
		return mainView;
	}

	// old
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		applicationBundle = resources;
		addInitListeners();
		

		tf_configFile.setDisable(true);
		tf_logXtfFile.setDisable(true);
		tf_pluginsFolder.setDisable(true);

		btnBrowseConfigFile.setDisable(true);
		btnBrowseLogXtfFile.setDisable(true);
		btnBrowsePluginsFolder.setDisable(true);

		//tf_modelDir.setText(Config.getInstance().getModelDir());
	}

	private void addInitListeners() {

		tf_XtfFile.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue != null && !newValue.equals("")
						&& (newValue.endsWith(".xtf") || newValue.endsWith(".itf")))
					tf_XtfFile.setStyle(null);
			}
		});

		tf_pluginsFolder.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue != null && !newValue.equals(""))
					tf_pluginsFolder.setStyle(null);
			}
		});

		tf_logXtfFile.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue != null && !newValue.equals(""))
					tf_logXtfFile.setStyle(null);
			}
		});

		tf_configFile.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue != null && !newValue.equals(""))
					tf_configFile.setStyle(null);
			}
		});

	}

	private boolean validateFields() {
		boolean result = true;

		if (tf_XtfFile.getText() == null || tf_XtfFile.getText().equals("")
				|| !(tf_XtfFile.getText().endsWith(".xtf") || tf_XtfFile.getText().endsWith(".itf"))) {
			tf_XtfFile.setStyle("-fx-border-color: red ;");
			result = false;
		}
		if (chk_additionalFunctionality.isSelected()) {

			if (tf_pluginsFolder.getText() == null || tf_pluginsFolder.getText().equals("")) {
				tf_pluginsFolder.setStyle("-fx-border-color: red ;");
				result = false;
			}
		}

		if (chk_logXtfFile.isSelected() && (tf_logXtfFile.getText() == null || tf_logXtfFile.getText().equals(""))) {
			tf_logXtfFile.setStyle("-fx-border-color: red ;");
			result = false;
		}

		if (chk_configFile.isSelected() && (tf_configFile.getText() == null || tf_configFile.getText().equals(""))) {
			tf_configFile.setStyle("-fx-border-color: red ;");
			result = false;
		}

		return result;
	}

	@FXML
	public void onClickBtnBrowseXtfFile(ActionEvent event) throws IOException {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters()
				.addAll(new ExtensionFilter(applicationBundle.getString("general.file.extension.xtf"), "*.xtf"));
		fileChooser.getExtensionFilters()
				.addAll(new ExtensionFilter(applicationBundle.getString("general.file.extension.itf"), "*.itf"));

		// TODO Verificar si el campo de texto con el archivo xtf debe ser de
		// solo lectura
		// TODO Verificar si el t�tulo corresponde
		fileChooser.setTitle(applicationBundle.getString("general.file.choose"));
		Window window = ((Node) event.getSource()).getScene().getWindow();
		File selectedFile = fileChooser.showOpenDialog(window);
		if (selectedFile != null)
			tf_XtfFile.setText(selectedFile.getAbsolutePath());
	}

	public void onClickChk_additionalFunctionality(ActionEvent event) {
		boolean checked = chk_additionalFunctionality.isSelected();

		tf_pluginsFolder.setDisable(!checked);
		btnBrowsePluginsFolder.setDisable(!checked);

		if (!checked)
			tf_pluginsFolder.setStyle(null);
	}

	public void onClickBtnModelDir(ActionEvent event) {
		try {
			ModelDirDialog dialog = new ModelDirDialog();
			dialog.setTitle(applicationBundle.getString("dialog.modeldir.title"));
			
			if (tf_modelDir.getText() != null && !tf_modelDir.getText().isEmpty())
				dialog.setData(Arrays.asList(tf_modelDir.getText().split(";")));

			Optional<List<String>> result = dialog.showAndWait();

			if (result.isPresent()) {
				tf_modelDir.setText(String.join(";", result.get()));
			}
		} catch (IOException E) {
			E.printStackTrace();
		}
	}

	public void onClickBtnBrowsePluginsFolder(ActionEvent event) {
		DirectoryChooser directoryChooser = new DirectoryChooser();

		// TODO Verificar si el campo de texto con la carpeta de plugins debe
		// ser de solo lectura
		// TODO Ajustar el texto de la ventana (dircetoryChoser plugins)
		directoryChooser.setTitle(applicationBundle.getString("general.file.choose"));

		Window window = ((Node) event.getSource()).getScene().getWindow();
		File selectedDirectory = directoryChooser.showDialog(window);

		if (selectedDirectory != null) {
			tf_pluginsFolder.setText(selectedDirectory.getAbsolutePath());
		}
	}

	private void addParams() {

	}

	public void onClickBtnBrowseConfigFile(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(
				new ExtensionFilter(applicationBundle.getString("general.file.extension.toml"), "*.toml"),
				new ExtensionFilter(applicationBundle.getString("general.file.extension.all"), "*.*"));

		// TODO Validar titulo
		fileChooser.setTitle(applicationBundle.getString("general.file.choose"));

		Window window = ((Node) event.getSource()).getScene().getWindow();
		File selectedFile = fileChooser.showOpenDialog(window);

		if (selectedFile != null) {
			tf_configFile.setText(selectedFile.getAbsolutePath());
		}
	}

	public void onClickBtnBrowseLogFile(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters()
				.addAll(new ExtensionFilter(applicationBundle.getString("general.file.extension.log"), "*.log"));

		// TODO Validar titulo
		fileChooser.setTitle(applicationBundle.getString("general.file.choose"));

		Window window = ((Node) event.getSource()).getScene().getWindow();
		File selectedFile = fileChooser.showSaveDialog(window);
	}

	public void onClickBtnBrowseLogXtfFile(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters()
				.addAll(new ExtensionFilter(applicationBundle.getString("general.file.extension.xtf"), "*.xtf"));

		// TODO Validar titulo
		fileChooser.setTitle(applicationBundle.getString("general.file.choose"));

		Window window = ((Node) event.getSource()).getScene().getWindow();
		File selectedFile = fileChooser.showSaveDialog(window);

		if (selectedFile != null) {
			tf_logXtfFile.setText(selectedFile.getAbsolutePath());
		}
	}

	public void onClickChk_configFile(ActionEvent event) {
		boolean checked = chk_configFile.isSelected();

		tf_configFile.setDisable(!checked);
		btnBrowseConfigFile.setDisable(!checked);

		if (!checked)
			tf_configFile.setStyle(null);
	}

	public void onClickChk_logXtfFile(ActionEvent event) {
		// chk_logXtfFile
		boolean checked = chk_logXtfFile.isSelected();

		tf_logXtfFile.setDisable(!checked);
		btnBrowseLogXtfFile.setDisable(!checked);

		if (!checked)
			tf_logXtfFile.setStyle(null);
	}
}
