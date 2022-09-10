package lib.ieris19.util.ui.core;

import java.util.HashMap;

public class ViewMap {
	private ViewMap() {
	}

	/**
	 * An absolute map of the views included in the program running, it will be the source of truth for the
	 * {@link ViewManager ViewManager}
	 */
	private static final HashMap<String, UIComponent> views = new HashMap<>();

	/**
	 * Fetches the {@link lib.ieris19.util.ui.core.UIComponent UI Component} from the map
	 *
	 * @param key unique name identifier for the View
	 *
	 * @return the corresponding UI Component
	 *
	 * @throws IllegalArgumentException if the UI component is null
	 */
	public static UIComponent get(String key) {
		UIComponent view = views.get(key);
		if (view != null) {
			return view;
		} else {
			throw new IllegalArgumentException("View does not exist");
		}
	}

	/**
	 * Adds a new {@link lib.ieris19.util.ui.core.UIComponent UI Component} to the map
	 *
	 * @param key  unique name identifier for the View
	 * @param view the UI Component to add to the map
	 */
	public static void add(String key, UIComponent view) {
		if (views.get(key) != null) {
			throw new IllegalArgumentException("View ID is not unique, it already exists");
		}
		views.put(key, view);
	}
}
