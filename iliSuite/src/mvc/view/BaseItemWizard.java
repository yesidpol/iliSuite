package mvc.view;

import javafx.scene.Parent;

public interface BaseItemWizard {
	public void goBack(WizardArgs args);
	public void cancel(WizardArgs args);
	public void goForward(WizardArgs args);
	public void execute(WizardArgs args);
	public void end(WizardArgs args);
	public Parent getGraphicComponent();
}