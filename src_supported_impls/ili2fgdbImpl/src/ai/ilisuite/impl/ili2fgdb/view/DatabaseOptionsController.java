package ai.ilisuite.impl.ili2fgdb.view;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import ai.ilisuite.impl.controller.IController;
import ai.ilisuite.impl.dbconn.AbstractConnection;
import ai.ilisuite.view.util.FileChooserHelper;
import ai.ilisuite.view.util.navigation.ResourceUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.stage.Window;
import javafx.stage.FileChooser.ExtensionFilter;

public class DatabaseOptionsController implements IController, Initializable {
	@FXML
	private ResourceBundle applicationBundle;

	private AbstractConnection connection;

	private boolean createSchema;

	@FXML
	private TextField tf_folder;

	@FXML
	private Button btnBrowse;
	
	private List<Node> listOfRequired;
	private Parent viewRootNode;

	public DatabaseOptionsController(AbstractConnection connection, boolean createSchema) throws IOException {
		this.connection = connection;
		this.createSchema = createSchema;
		
		String strBundlePath = "ai.ilisuite.impl.ili2fgdb.resources.application";
		String strResourcePath = "/ai/ilisuite/impl/ili2fgdb/view/DatabaseOptions.fxml";
		// TODO Posible carga de componentes antes de ser necesario
		viewRootNode = ResourceUtil.loadResource(strResourcePath, strBundlePath, this);
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		applicationBundle = arg1;
		listOfRequired = new ArrayList<>();
		listOfRequired.add(tf_folder);
	}

	@Override
	public Map<String, String> getParams() {
		Map<String, String> result = null;

		if (validateRequiredFields()) {

			String file = tf_folder.getText();

			Map<String, String> params = new HashMap<String, String>();

			params.put("dbfile", file);

			connection.setConnectionParams(params);

			result = new HashMap<String, String>();

			if (file != null)
				result.put("--dbfile", file);

		}

		return result;
	}
	
	public void setCreateSchema(boolean createSchema) {
		this.createSchema = createSchema;
	}

	public void onClickBrowse(ActionEvent e) {
		Window window = ((Node) e.getSource()).getScene().getWindow();

		File selectedFolder = null;

		if (createSchema) {
			selectedFolder = FileChooserHelper.showSaveDialog(window, FileChooserHelper.CAT_GDB,
					applicationBundle.getString("datafolder.choose"), new ExtensionFilter("gdb","*.gdb"));
		} else {
			selectedFolder = FileChooserHelper.showDirectoryDialog(window, FileChooserHelper.CAT_GDB,
					applicationBundle.getString("datafolder.choose"));
		}

		if (selectedFolder != null)
			tf_folder.setText(selectedFolder.getAbsolutePath());

	}
	
	protected boolean validateRequiredFields(){
		boolean toValid = true;
		for (Node n : listOfRequired) {
			if (n instanceof TextField) {
				if (((TextField) n).getText().isEmpty()) {
					((TextField) n).setStyle("-fx-text-box-border: red ; -fx-focus-color: red ;");
					((TextField) n).setTooltip(new Tooltip(applicationBundle.getString("general.required")));
					((TextField) n).setOnKeyReleased(event -> {
						((TextField) n).setStyle(null);
						((TextField) n).setTooltip(null);
						});
					toValid = false;
				}
			}
		}
		return toValid;
	}

	@Override
	public Parent getGraphicComponent() {
		return viewRootNode;
	}
}
