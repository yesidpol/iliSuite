package ai.iliSuite.controller;

import java.io.IOException;
import java.util.Map;

import ai.iliSuite.base.InterlisExecutable;
import ai.iliSuite.view.DatabaseOptionsView;
import ai.iliSuite.view.DatabaseSelectionView;
import ai.iliSuite.view.FinishActionView;
import ai.iliSuite.view.ModelConvertOptionsView;
import ai.iliSuite.view.OpenUmlEditorView;
import ai.iliSuite.view.wizard.EmptyWizardException;
import ai.iliSuite.view.wizard.Wizard;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;


public class OpenUmlEditorController implements ParamsController {
	private InterlisExecutable model;
	private Wizard wizard;
	
	private EventHandler<ActionEvent> finishHandler;
	private EventHandler<ActionEvent> goBackHandler;
	
	public OpenUmlEditorController(InterlisExecutable model) throws IOException {
		this.model = model;
		initWizard();
	}

	private void initWizard() throws IOException {
		EventHandler<ActionEvent> goBack = 
				(ActionEvent e) -> { if(goBackHandler != null) { goBackHandler.handle(e); }};
		EventHandler<ActionEvent> finish = 
				(ActionEvent e) -> { if(finishHandler != null) { finishHandler.handle(e); }};

		wizard = new Wizard();
		wizard.setOnBack(goBack);
		wizard.setOnCancel(goBack);
		wizard.setOnFinish(finish);

		wizard.add(new OpenUmlEditorView());

		try {
			wizard.init();
		} catch (EmptyWizardException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void addParams(Map<String, String> params) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeParams(Map<String, String> params) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public String getTextParams() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean execute() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setOnFinish(EventHandler<ActionEvent> handler) {
		this.finishHandler = handler;
	}
	
	@Override
	public void setOnGoBack(EventHandler<ActionEvent> handler) {
		this.goBackHandler = handler;
	}

	@Override
	public Parent getGraphicComponent() {
		return wizard.getGraphicComponent();
	}

}
