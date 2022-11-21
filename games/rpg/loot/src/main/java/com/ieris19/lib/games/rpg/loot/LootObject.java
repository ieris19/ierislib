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

/**
 * Data for loot objects. This record defines objects that can be looted. This means that it can be stored in LootTables
 * and returned by Lootable objects. <br> <br>
 *
 * @param name   The name string is used to identify the object, meaning it can be parsed higher up to create objects
 *               rather than Strings
 * @param weight The weight is used to determine the chance of the object being returned. The higher the weight, the
 *               higher the chance of the object being returned. Weights can be negative, but these define objects that
 *               will always be returned
 */
public record LootObject(String name, int weight) {}
