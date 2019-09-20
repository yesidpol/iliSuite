package view.util.navigation;

import java.io.IOException;
import java.util.ResourceBundle;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import mvc.view.BaseItemWizard;

public class ResourceUtil {

	public static VisualResource loadResource(Class c, EnumPaths path, EnumPaths resourceBundle) throws IOException {
		Parent component;
		FXMLLoader loader;
		if (resourceBundle != null) {
			ResourceBundle bundle = ResourceBundle.getBundle(resourceBundle.getPath());
			loader = new FXMLLoader(c.getResource(path.getPath()), bundle);
		} else {
			loader = new FXMLLoader(c.getResource(path.getPath()));
		}
		component = loader.load();
		Navigable controller = loader.getController();
		return new VisualResource(component, controller);

	}
	
	// FIX merge with loadResource
	public static Parent loadResource(Class c, EnumPaths path, EnumPaths resourceBundle, Object viewController) throws IOException {
		Parent component;
		FXMLLoader loader;
		if (resourceBundle != null) {
			ResourceBundle bundle = ResourceBundle.getBundle(resourceBundle.getPath());
			loader = new FXMLLoader(c.getResource(path.getPath()), bundle);
		} else {
			loader = new FXMLLoader(c.getResource(path.getPath()));
		}
		loader.setController(viewController);
		component = loader.load();
		return component;
	}

}
