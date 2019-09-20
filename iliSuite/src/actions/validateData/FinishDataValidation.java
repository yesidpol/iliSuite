package actions.validateData;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.StyleClassedTextArea;
import org.interlis2.validator.Main;

import application.data.AppData;
import ch.ehi.basics.logging.EhiLogger;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import mvc.view.ItemWizard;
import util.exception.ExitException;
import util.log.LogListenerExt;
import util.params.ParamsContainer;
import view.util.console.NoOpUndoManager;
import view.util.navigation.EnumPaths;
import view.util.navigation.Navigable;
import view.util.navigation.ResourceUtil;

public class FinishDataValidation extends ItemWizard implements Initializable {
	private Parent mainView;
	
	private StyleClassedTextArea txtConsole;
	
	private LogListenerExt log;

	@FXML
	private VBox verticalWrapper;

	public FinishDataValidation() throws IOException {
		mainView = ResourceUtil.loadResource(getClass(), EnumPaths.VAL_DATA_FINISH_VALIDATION,
		EnumPaths.RESOURCE_BUNDLE, this);
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initTxtConsole();
		
		// FIX remove logger?
		log = new LogListenerExt(txtConsole, "");
		EhiLogger.getInstance().addListener(log);
	}

	private void initTxtConsole() {
		txtConsole = new StyleClassedTextArea();
		
		VirtualizedScrollPane<StyleClassedTextArea> vsPane = new VirtualizedScrollPane<>(txtConsole);
		verticalWrapper.getChildren().add(vsPane);
        	VBox.setVgrow(vsPane, Priority.ALWAYS);
        
        	txtConsole.setWrapText(true);
        	txtConsole.setMinHeight(400);
	        txtConsole.getStyleClass().add("text_console");
		txtConsole.setEditable(false);
		txtConsole.setUndoManager(new NoOpUndoManager());
	}

	@Override
	public Parent getGraphicComponent() {
		return mainView;
	}
}
