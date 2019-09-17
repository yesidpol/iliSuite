package mvc.view;

import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

public class ProccessWizard extends Wizard {

	private BorderPane wrapper;
	
	public ProccessWizard(BorderPane wrapper) {
		this.wrapper = wrapper;
	}
	@Override
	protected void setGui(WizardComponent item) {
		// FIX specific code
		wrapper.setCenter(item.getGui());
	}

	@Override
	public Parent getGui() {
		return null;
	}
}
