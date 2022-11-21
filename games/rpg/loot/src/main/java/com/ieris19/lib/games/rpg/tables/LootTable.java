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

package com.ieris19.lib.games.rpg.tables;

import com.ieris19.lib.games.rpg.loot.LootObject;

import java.io.FileWriter;
import java.util.*;

/**
 * Loot tables are used to store lootable objects and return them based on their weight. The higher the weight, the
 * higher the chance of the object being returned.
 */
public class LootTable {
	/**
	 * The list of loot objects in the table
	 */
	private LinkedList<LootObject> lootTable;
	/**
	 * The random number generator used to determine the loot
	 */
	private Random random;
	/**
	 * The total weight of the loot table
	 */
	private int totalWeight;

	/**
	 * Creates a new loot table without any loot objects
	 */
	public LootTable() {
		this.lootTable = new LinkedList<>();
		this.random = new Random();
		this.totalWeight = 0;
	}

	/**
	 * Creates a new loot table with the specified loot objects
	 *
	 * @param tableContents The loot objects to add to the table
	 */
	public LootTable(List<LootObject> tableContents) {
		this.lootTable = new LinkedList<>(tableContents);
		this.random = new Random();
		lootTable.forEach(lootObject -> totalWeight += lootObject.weight());
	}

	/**
	 * Creates a new loot table with the specified loot objects
	 *
	 * @param tableContents The loot objects to add to the table
	 */
	public LootTable(LootObject[] tableContents) {
		this.lootTable = new LinkedList<>(Arrays.asList(tableContents));
		this.random = new Random();
		lootTable.forEach(lootObject -> totalWeight += lootObject.weight());
	}

	/**
	 * Adds a loot object to the table
	 *
	 * @param lootObject The loot object to add
	 */
	public void addLoot(LootObject lootObject) {
		lootTable.add(lootObject);
		totalWeight += lootObject.weight();
	}

	/**
	 * Adds a loot object to the table
	 *
	 * @param name   The name of the loot object
	 * @param weight The weight of the loot object
	 */
	public void addLoot(String name, int weight) {
		if (lootTable.add(new LootObject(name, weight)))
			totalWeight += weight;
	}

	/**
	 * Removes a loot object from the table
	 *
	 * @param lootObject The loot object to remove
	 */
	public void removeLoot(LootObject lootObject) {
		if (lootTable.remove(lootObject))
			totalWeight -= lootObject.weight();
	}

	/**
	 * Picks a random loot object from the table based on the weight of the objects and returns it
	 */
	public LootObject[] randomLoot() {
		ArrayList<LootObject> loot = new ArrayList<>();
		ArrayList<Integer> ranges = new ArrayList<>();
		ranges.add(0);
		for (int i = 1; i < lootTable.size(); i++) {
			ranges.add(ranges.get(i - 1) + lootTable.get(i).weight());
		}
		int randomInt = random.nextInt(ranges.get(ranges.size() - 1)) + 1;
		try (FileWriter writer = new FileWriter("loot.txt")) {
			writer.write(randomInt + "\n");
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (int i = 1; i < ranges.size(); i++) {
			if (randomInt <= ranges.get(i) && randomInt > ranges.get(i - 1)) {
				loot.add(lootTable.get(i));
			}
		}
		return loot.toArray(new LootObject[0]);
	}
}
