package lib.ieris19.util.economy;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;

/**
 * Class that will listen and print all changes that happen to the classes that it listens to
 */
public class MoneyValueObserver implements PropertyChangeListener {
	/**
	 * A list of all the {@link Money} classes that are being listened to
	 */
	private final HashMap<Money, String> currentSubjects;

	/**
	 * Creates a monitor object that can listen to at least one {@link Money} class
	 * @param money the subject that will be listened to
	 * @param name the name associated with said subject
	 */
	public MoneyValueObserver(Money money, String name) {
		currentSubjects = new HashMap<>();
		currentSubjects.put(money, name);
		money.addListener(this);
	}

	/**
	 * Adds a {@link Money} class to be listened to
	 * @param money the subject that will be listened to
	 * @param name the name associated with said subject
	 */
	public void addSubject(Money money, String name) {
		currentSubjects.put(money, name);
		money.addListener(this);
	}

	/**
	 * Removes a {@link Money} class that will be no longer listened to
	 * @param money the subject that will no longer be listened to
	 */
	public void removeSubject(Money money) {
		currentSubjects.remove(money);
		money.removeListener(this);
	}

	/**
	 * This method gets called when a change occurs in the monitored account.
	 *
	 * @param evt A PropertyChangeEvent object describing the event source and the property that has
	 *            changed.
	 */
	@Override public void propertyChange(PropertyChangeEvent evt) {
			Money source = (Money) evt.getSource();
			String name = currentSubjects.get(source);
			System.out.println(name + ": " + evt.getNewValue());
	}
}
