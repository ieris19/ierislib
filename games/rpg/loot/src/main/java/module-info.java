/**
 * Module to that implements the necessary classes and methods to create a loot system. In short, the simplest use of
 * this module is to create a {@link com.ieris19.lib.games.rpg.tables.LootTable Loot Table} and add
 * {@link com.ieris19.lib.games.rpg.loot.LootObject Loot Objects} to it.
 */
module ierislib.games.rpg.loot {
	requires java.base;

	exports com.ieris19.lib.games.rpg.loot;
	exports com.ieris19.lib.games.rpg.tables;
}