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

package com.ieris19.lib.reflection.annotations;

import java.lang.annotation.*;

/**
 * An informative annotation that indicates an interface is a marker interface.
 * Marker interfaces are interfaces that do not contain any methods, and are used
 * to indicate that a class implements a certain feature. For example, the
 * {@link java.io.Serializable} interface is a marker interface, and is used to
 * indicate that a class can be serialized.
 * <p>
 *   This annotation is used by programmers to indicate that an interface is a
 *   marker interface, and is not used by the compiler.
 *   <br>
 *   This annotation is not inherited. If a class implements an interface that is
 *   annotated with this annotation, the class is not considered to be a marker interface.
 *   <br>
 *   This annotation is not repeatable.
 */
@Documented
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface MarkerInterface {}
