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

package com.ieris19.lib.log.core.impl;

import com.ieris19.lib.log.core.IerisLogger;
import com.ieris19.lib.log.util.Context;
import com.ieris19.lib.log.util.LogPrinter;
import com.ieris19.lib.log.util.TemporalHandler;

import java.time.Instant;

public class BaseLogger implements IerisLogger {
    private final int logLevel;
    private final LogPrinter output;

    public BaseLogger(int logLevel, LogPrinter output) {
        this.output = output;
        this.logLevel = logLevel;
    }

    private String logHeader(Context context) {
        String timestamp = String.format("%14s", timestamp());
        String level = String.format("%7s", context.name());
        String thread = String.format("%-10s", Thread.currentThread().getName());
        return timestamp + " " + level + " " + thread + " ";
    }

    private String timestamp() {
        return "[" + TemporalHandler.getFormatter("timeISO").format(Instant.now()) + "]";
    }

    protected void log(String message, Context context) {
        if (context.getLevel() <= this.logLevel) {
            output.log(logHeader(context) + message, context.getColor());
        }
    }

    public void
}