package mvc.view;

import javafx.scene.Parent;

public abstract class WizardComponent {
	abstract public boolean goForward();
	abstract public boolean goBack();
	abstract public boolean cancel();
	abstract public Parent getGui();
	
	public void onCharged() {
	}
	
	public void add(WizardComponent item) {
	}
	public void remove(WizardComponent item) {
	}
}
