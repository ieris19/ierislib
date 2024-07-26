/*
 * Copyright 2024 Ieris19
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

package com.ieris19.lib.economy;

import java.math.BigDecimal;

public class Currency {
    private BigDecimal value;
    private final String code;

    public Currency(BigDecimal value, String code) {
        this.value = value;
        this.code = code;
    }

    public Currency(double value, String code) {
        this.value = new BigDecimal(value);
        this.code = code;
    }

    public Currency(String code) {
        this.value = new BigDecimal(0);
        this.code = code;
    }

    public Currency(BigDecimal value) {
        this.value = value;
        this.code = "EUR";
    }

    public Currency(double value) {
        this.value = new BigDecimal(value);
        this.code = "EUR";
    }

    public BigDecimal getValue() {
        return value;
    }

    public String getCode() {
        return code;
    }
}
