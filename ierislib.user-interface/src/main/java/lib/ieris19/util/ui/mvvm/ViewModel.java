package lib.ieris19.util.ui.mvvm;

import javafx.beans.property.Property;

public abstract class ViewModel {
	public void setModel(Model model) {
	}

	public abstract void bind(String name, Property<?> property);
}
