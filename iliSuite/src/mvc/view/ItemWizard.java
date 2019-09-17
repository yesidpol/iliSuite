package mvc.view;

import javafx.scene.Parent;

public class ItemWizard extends WizardComponent {

	@Override
	public boolean goForward() {
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
		// TODO Auto-generated method stub
		return null;
	}
}
