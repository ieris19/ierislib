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

package com.ieris19.lib.log.util;

import com.ieris19.lib.common.text.TextColor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class LogPrinter {
    private final boolean supportsANSI;
    private final boolean writeToFile;
    private final File outputDirectory;
    private final TextField namingScheme;

    public LogPrinter(boolean supportsANSI, boolean writeToFile, File outputDir, TextField namingScheme) {
        this.supportsANSI = supportsANSI;
        this.writeToFile = writeToFile;
        this.outputDirectory = outputDir;
        this.namingScheme = namingScheme;
    }

    public void log(String line, TextColor color) {
        synchronized (outputDirectory) {
            writeToConsole(line, color);
            writeToFile(line);
        }
    }

    private void writeToConsole(String line, TextColor color) {
        if (supportsANSI) {
            System.out.println(TextColor.format(line, color));
        } else {
            System.out.println(line);
        }
    }

    private void writeToFile(String line) {
        if (!writeToFile) {
            return;
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(getFile(), true))) {
            writer.println(line);
            writer.flush();
        } catch (IOException e) {
            System.err.println("Failed to write to file");
        }
    }

    private File getFile() {
        return new File(outputDirectory, namingScheme.getText());
    }
}
