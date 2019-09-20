package mvc.view;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import view.util.navigation.EnumPaths;
import view.util.navigation.ResourceUtil;

public class Wizard extends BaseWizard{
	private Parent mainView;
	@FXML
	private BorderPane contentPane;
	
	public Wizard() throws IOException {
		mainView = ResourceUtil.loadResource(getClass(), EnumPaths.WIZARD_LAYOUT,
		EnumPaths.RESOURCE_BUNDLE, this);
	}
	
	public Parent getGraphicComponent() {
		return mainView;
	}
	
	@Override
	protected void drawPage(BaseItemWizard item) {
		contentPane.setCenter(item.getGraphicComponent());
	}
	
	@FXML
	private void btnNextOnAction(ActionEvent e) {
		this.goForward();
	}
	@FXML
	private void btnBackOnAction(ActionEvent e) {
		this.goBack();
	}
	@FXML
	private void btnCancelOnAction(ActionEvent e) {
		this.cancel();
	}
}
