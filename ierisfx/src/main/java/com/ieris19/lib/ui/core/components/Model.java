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

package com.ieris19.lib.ui.core.components;

/**
 * This interface represents a model. A model is responsible for handling all operations that are not directly related
 * to the user interface. This includes all business logic and data management. The model should be as independent as
 * possible from the view and the view model. <br> This interface is empty because it is only used as a marker
 * interface. All concrete models should extend this interface.<br> <br>
 *
 * This interface usually represents a remote object in a different process. Because of this, it is recommended to use
 * interfaces as models. This allows the view model to be unaware of the implementation of the model, and allows the
 * model to be implemented in a different process.
 */
public interface Model extends Cloneable {}
