package view.general;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import application.data.Config;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import menu.dialog.AboutDialog;
import menu.dialog.HelpDialog;
import menu.dialog.PreferencesController;
import menu.dialog.ProxyDialog;
import mvc.view.Wizard;
import mvc.view.WizardComponent;
import view.dialog.ModelDirDialog;
import view.util.navigation.EnumPaths;
import view.util.navigation.NavigationUtil;
import view.util.navigation.ResourceUtil;

public class GeneralLayoutWizard extends Wizard implements Initializable {
	
	private Parent mainView;
	
	@FXML
	private AnchorPane generalLayout;
	
	@FXML
	private BorderPane contentPane;
	
	public GeneralLayoutWizard() throws IOException {
		mainView = ResourceUtil.loadResource(getClass(), EnumPaths.GENERAL_LAYOUT,
		EnumPaths.RESOURCE_BUNDLE, this);		
	}
	
	public Parent getGui() {
		return mainView;
	}
	
	@Override
	protected void setGui(WizardComponent item) {
		Parent content = item.getGui();
		if(content != null) {
			contentPane.setCenter(content);
		}
	}
	
	@Override
	public boolean cancel() {
		// TODO Auto-generated method stub
		if(super.cancel()) {
			if(this.index<=0) {
				Stage s = (Stage) mainView.getScene().getWindow();
				s.close();
				return true;
			} else {
				this.goBack();
			}
		}
		
		return true;
	}
	
	/// --- old
	private ResourceBundle bundle;
	
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

	public BorderPane getContentPane() {
		return contentPane;
	}
}
