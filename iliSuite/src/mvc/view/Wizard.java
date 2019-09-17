package mvc.view;

import java.util.ArrayList;
import java.util.List;

public abstract class Wizard extends WizardComponent {
	protected List<WizardComponent> children;
	protected int index = -1;
	
	public Wizard() {
		children = new ArrayList<WizardComponent>();
	}
	
	public Wizard(List<WizardComponent> children) {
		children = new ArrayList<>(children);
	}
	
	abstract protected void setGui(WizardComponent item);
	
	@Override
	public boolean goForward() {
		System.out.println("next");
		
		int indexNextItem = index + 1;
		WizardComponent actualItem = null;
		
		if (!children.isEmpty() && index>=0 && index < children.size()) {
			actualItem = children.get(index);
		}
			
		if(actualItem == null || actualItem.goForward()) {
			if(indexNextItem < children.size()) {
				WizardComponent nextItem = children.get(indexNextItem);
				setGui(nextItem);
				nextItem.onCharged();
			}
			index = indexNextItem;
		}
		
		return (index >= children.size());
	}
	@Override
	public boolean goBack() {
		if(index >= 0) {
			WizardComponent actualItem = children.get(index);
			boolean back = actualItem.goBack();
			if(back) {
				index--;
				if(index>=0) {
					actualItem = children.get(index);
					setGui(actualItem);
				}
			}
		}
		
		return (index == -1);
	}
	
	@Override
	public boolean cancel() {
		WizardComponent actualItem = children.get(index);
		return actualItem.cancel();
	}
	
	@Override
	public void add(WizardComponent item) {
		children.add(item);
	}
	@Override
	public void remove(WizardComponent item) {
		children.remove(item);
	}
	@Override
	public void onCharged() {
		super.onCharged();
		if(index==-1) {
			index=0;
			WizardComponent actualItem = children.get(index);
			setGui(actualItem);
		}
	}
}
