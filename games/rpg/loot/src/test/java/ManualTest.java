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

import com.ieris19.lib.games.rpg.loot.LootObject;
import com.ieris19.lib.games.rpg.tables.LootTable;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class ManualTest {
	@Test void testRandomLoot() throws IOException {
		LootTable table = new LootTable();
		table.addLoot(new LootObject("Sword", 20));
		table.addLoot(new LootObject("Shield", 20));
		table.addLoot(new LootObject("Potion", 20));
		table.addLoot(new LootObject("Gold", 20));
		table.addLoot(new LootObject("Nothing", 20));
		File file = new File("loot.txt");
		file.createNewFile();
		for (int i = 0; i < 100; i++) {
			Scanner scanner = new Scanner(file);
			LootObject loot = table.randomLoot()[0];
			System.out.println(loot);
			boolean assertion = switch (loot.name()) {
				case "Sword" -> testLoot(scanner, new int[] {0, 20});
				case "Shield" -> testLoot(scanner, new int[] {20, 40});
				case "Potion" -> testLoot(scanner, new int[] {40, 60});
				case "Gold" -> testLoot(scanner, new int[] {60, 80});
				case "Nothing" -> testLoot(scanner, new int[] {80, 100});
				default -> false;
			};
		}
	}

	private boolean testLoot(Scanner fileReader, int[] expected) {
		int actual = 0;
		if (fileReader.hasNext()) {
			actual = Integer.parseInt(fileReader.nextLine());
		}
		System.out.println("Actual: " + actual + " Range: " + Arrays.toString(expected));
		return actual <= expected[1] && actual > expected[0];
	}
}
