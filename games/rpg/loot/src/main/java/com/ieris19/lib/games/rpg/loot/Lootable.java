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

package com.ieris19.lib.games.rpg.loot;

import com.ieris19.lib.games.rpg.tables.LootTable;

/**
 * Lootable interface marks an object that can be looted. This means that the object can drop loot. For this purpose, the object must implement the loot method.
 * the loot method returns a LootObject, which can be anything. This can be a String, an Item, a Potion, gold, etc.
 */
public interface Lootable {
	default LootObject[] loot(LootTable table) {
		return table.randomLoot();
	}
}
