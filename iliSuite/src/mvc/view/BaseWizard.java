package mvc.view;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseWizard {
	protected List<BaseItemWizard> pages;
	protected int index = -1;
	
	public BaseWizard() {
		pages = new ArrayList<BaseItemWizard>();
	}
	
	public BaseWizard(List<BaseItemWizard> children) {
		children = new ArrayList<>(children);
	}
	
	abstract protected void drawPage(BaseItemWizard item);

	public void goForward() {
		BaseItemWizard actualItem = null;
		
		if (!pages.isEmpty() && index >= 0 && index < pages.size()) {
			actualItem = pages.get(index);
		}
		WizardArgs args = new WizardArgs();
		if(actualItem != null) {
			actualItem.goForward(args);
		}
		
		if(!args.isCancel() && index < pages.size()) {
			index++;
			if(index < pages.size()) {
				BaseItemWizard nextItem = pages.get(index);
				drawPage(nextItem);
			}
		}
	}
	
	public void init() throws Exception {
		if(pages.isEmpty()) {
			throw new Exception("The wizard does not have pages.");
		}
		index = 0;
		BaseItemWizard actualItem = pages.get(index);
		drawPage(actualItem);
	}

	public void goBack() {
		if(index >= 0 && index < pages.size()) {
			BaseItemWizard actualItem = pages.get(index);
			WizardArgs args = new WizardArgs();
			actualItem.goBack(args);
			if(!args.isCancel()) {
				index--;
				if(index>=0) {
					actualItem = pages.get(index);
					drawPage(actualItem);
				}
			}
		}
	}

	public void cancel() {
		BaseItemWizard actualItem = pages.get(index);
		WizardArgs args = new WizardArgs();
		actualItem.cancel(args);
	}
	
	public void add(BaseItemWizard item) {
		pages.add(item);
	}

	public void remove(BaseItemWizard item) {
		pages.remove(item);
	}
}
