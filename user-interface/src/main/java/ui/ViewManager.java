package ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.util.ArrayList;

//TODO: Full Rewrite of the Library is probably needed to comply with MVVM
public abstract class ViewManager {
	Model control;
	protected ArrayList<ViewController> controllers;
	protected Scene currentScene;
	protected Stage stage;

	//Config Values
	protected Image icon;
	protected String title;
	protected boolean resizable = true;
	protected double wHeight = 0.00;
	protected double wWidth = 0.00;

	public ViewManager(Model control) {
		this.control = control;
		this.currentScene = new Scene(new Region());
		this.controllers = new ArrayList<>();
	}

	public void start(Stage stage) {
		this.stage = stage;
		initialize();
		openView(0);
	}

	private void initialize() {
		if (icon != null) {
			stage.getIcons().add(icon);
		}
		stage.setResizable(resizable);
		if (wHeight != 0)
			stage.setHeight(wHeight);
		if (wWidth != 0)
			stage.setWidth(wWidth);
	}

	public void setResizable(boolean resizable) {
		this.resizable = resizable;
	}

	public void setIcon(String filepath) {
		//Recommended Filepath : Replicate the package structure in
		try {
			this.icon = new Image(this.getClass().getResourceAsStream("images/icon.png"));
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	public void setHeight(double height) {
		wHeight = height;
	}

	public void setWidth(double width) {
		wWidth = width;
	}

	public abstract void openView(int viewId);
		/*Region root = loadView(".fxml", controllers.get(0));
		switch (viewId) {
			case 0 -> {
				root = loadView(".fxml", controllers.get(0));
			}
		}
		*/

	protected void showScene(Region root) {
		currentScene.setRoot(root);
		if (root.getUserData() != null) {
			title += root.getUserData();
		}
		stage.setTitle(title);
		if (wHeight == 0 && wWidth == 0) {
			stage.sizeToScene();
		}
		stage.setScene(currentScene);
		stage.show();

		currentScene.setRoot(root);
		if (root.getUserData() != null) {
			title += root.getUserData();
		}
		stage.setTitle(title);
		stage.setScene(currentScene);
		stage.show();
	}

	protected Region loadView(String fxmlId, ViewController controller) {
		if (controller.getRoot() == null)
			try {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(this.getClass().getResource("fxml/" + fxmlId));
				Region root = loader.load();
				controller = loader.getController();
				controller.init(this, control, root);
			} catch (Exception e) {
				e.printStackTrace();
				throw new IllegalStateException("View couldn't be loaded");
			}
		else {
			controller.reset();
		}
		return controller.getRoot();
	}
}
