/*
 * Copyright 2021 Ieris19
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *
 */

package com.ieris19.lib.economy.games;

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
	 *
	 * @param money the subject that will be listened to
	 * @param name  the name associated with said subject
	 */
	public MoneyValueObserver(Money money, String name) {
		currentSubjects = new HashMap<>();
		currentSubjects.put(money, name);
		money.addListener(this);
	}

	/**
	 * Adds a {@link Money} class to be listened to
	 *
	 * @param money the subject that will be listened to
	 * @param name  the name associated with said subject
	 */
	public void addSubject(Money money, String name) {
		currentSubjects.put(money, name);
		money.addListener(this);
	}

	/**
	 * Removes a {@link Money} class that will be no longer listened to
	 *
	 * @param money the subject that will no longer be listened to
	 */
	public void removeSubject(Money money) {
		currentSubjects.remove(money);
		money.removeListener(this);
	}

	/**
	 * This method gets called when a change occurs in the monitored account.
	 *
	 * @param evt A PropertyChangeEvent object describing the event source and the property that has changed.
	 */
	@Override public void propertyChange(PropertyChangeEvent evt) {
		Money source = (Money) evt.getSource();
		String name = currentSubjects.get(source);
		System.out.println(name + ": " + evt.getNewValue());
	}
}
