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

package lib.ieris19.util.log.custom;

/**
 * A functional interface for implementing formatting lambdas
 */
@FunctionalInterface
public interface TextField {
	/**
	 * A method to be used in lambda expressions to return a string based on an arbitrarily big string arguments
	 *
	 * @param args the array of arguments to be used in the lambda expression
	 *
	 * @return the text field
	 */
	String getText(String... args);
}
