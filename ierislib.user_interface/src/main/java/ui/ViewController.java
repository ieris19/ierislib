package ui;

import javafx.scene.layout.Region;

public abstract class ViewController {
	protected Region root;
	protected Model control;
	protected ViewManager view;

	public ViewController() {
	}

	public void init(ViewManager manager, Model control, Region root) {
		this.view = manager;
		this.root = root;
		this.control = control;
	}

	public void reset() {
	}

	public Region getRoot() {
		return root;
	}
}
