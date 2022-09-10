package lib.ieris19.util.ui.core;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import lib.ieris19.util.log.Log;
import lib.ieris19.util.properties.FileProperties;
import lib.ieris19.util.ui.mvvm.ViewController;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

/**
 * Manager for all the views in the application. This class is responsible for loading the view from the FXML and
 * displaying it.
 */
public class ViewManager {
	/**
	 * The name of the application being run
	 */
	private final String name;
	/**
	 * The window that the application is running on
	 */
	private Stage stage;
	/**
	 * Current view being displayed
	 */
	private Scene currentScene;
	/**
	 * Properties file for the application
	 */
	private final FileProperties appProperties;

	/**
	 * Creates a new ViewManager to handle the views in the application
	 */
	public ViewManager() {
		this.appProperties = FileProperties.getInstance("app");
		this.name = appProperties.getProperty("name");
		if (name == null) {
			Log.getInstance().fatal("Name property not found in app.properties");
			try (FileWriter fileWriter = new FileWriter(new File(new File("config"), "app.properties"))) {
				String[] properties = {"name", "version", "icon", "resizable", "fullscreen", "width", "height", "default-view"};
				for (String property : properties) {
					fileWriter.write(property + "=null\n");
				}
			} catch (Exception e) {
				Log.getInstance().fatal("Could not write app.properties");
			}
		}
	}

	/**
	 * Starts the view manager and displays the first view
	 *
	 * @param stage the window that the application is running on
	 */
	public void start(Stage stage) {
		this.stage = stage;
		this.currentScene = new Scene(new Region());
		InputStream icon = this.getClass().getResourceAsStream(appProperties.getProperty("icon"));
		setIcon();
		Log.getInstance().success("Icon set, trying to load the view");
		openView(appProperties.getProperty("default-view"));
	}

	public void openView(String viewId) {
		showScene(loadView(viewId));
		Log.getInstance().success("Successfully opened view: " + viewId);
	}

	private Region loadView(String viewId) {
		UIComponent view = ViewMap.get(viewId);
		ViewController controller = view.getController();
		if (controller.getRoot() == null)
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(view.getFXML());
				Region root = loader.load();
				controller = loader.getController();
				controller.init(this, view, root);
				Log.getInstance().success("Correctly loaded view: " + viewId);
			} catch (Exception e) {
				e.printStackTrace();
				Log.getInstance().error("Cannot load view");
				throw new IllegalStateException("View couldn't be loaded", e);
			}
		else {
			controller.reset();
		}
		return controller.getRoot();
	}

	protected void showScene(Region root) {
		currentScene.setRoot(root);
		String title = name;
		if (root.getUserData() != null) {
			title += " - " + root.getUserData();
		}
		stage.setTitle(title);
		stage.sizeToScene();
		stage.setResizable(appProperties.getPropertyBoolean("resizable"));
		stage.setScene(currentScene);
		stage.show();
		stage.centerOnScreen();
	}

	public void setIcon() {
		InputStream icon = this.getClass().getResourceAsStream(appProperties.getProperty("icon"));
		if (icon != null) {
			try {
				stage.getIcons().setAll(new Image(icon));
				icon.close();
			} catch (IOException e) {
				Log.getInstance().error("IOException while setting icon");
			} catch (NullPointerException e) {
				Log.getInstance().error("Icon doesn't exit or is null");
			}
		}
	}
}
