package com.ieris19.lib.files.config.internal;

import com.ieris19.lib.files.config.base.BaseConfigManager;
import com.ieris19.lib.files.config.base.BaseFileConfigManager;
import org.ini4j.Ini;

import java.io.IOError;
import java.io.IOException;
import java.net.URI;
import java.util.Optional;

public class IniConfigManager extends BaseFileConfigManager {
    Ini config;

    public IniConfigManager(URI path) {
        super(path);
        try {
            this.config = new Ini(path.toURL());
        } catch (IOException e) {
            throw new IOError(e);
        }
    }

    @Override
    public void loadProperties() {
        try {
            this.config.load();
        } catch (IOException e) {
            throw new IOError(e);
        }
    }

    @Override
    public Optional<String> getProperty(String key) {
        IniKey iniKey = new IniKey(key);
        return Optional.ofNullable(this.config.fetch(iniKey.section, iniKey.key));
    }

    @Override
    public void setProperty(String key, String value) {
        IniKey iniKey = new IniKey(key);
        this.config.put(iniKey.section, iniKey.key, value);
    }

    @Override
    public void deleteProperty(String key) {
        IniKey iniKey = new IniKey(key);
        this.config.remove(iniKey.section, iniKey.key);
    }

    @Override
    public boolean propertyPresent(String key) {
        IniKey iniKey = new IniKey(key);
        return this.config.containsKey(iniKey.section) && this.config.get(iniKey.section).containsKey(iniKey.key);
    }

    @Override
    public void save() {
        try {
            this.config.store();
        } catch (IOException e) {
            throw new IOError(e);
        }
    }

    /**
     * This class represents a key in an INI file, which can have a section and a key
     * or just a key
     */
    private static class IniKey {
        private static final String SECTION_SEPARATOR = ".";
        private String section;
        private String key;

        private IniKey(String section, String key) {
            this.section = section;
            this.key = key;
        }

        private IniKey(String key) {
            String[] parts = key.split("/");
            if (parts.length > 2) {
                throw new IllegalArgumentException("Ini key can only have one section separator '/'");
            } else if (parts.length == 2) {
                this.section = parts[0];
                this.key = parts[1];
            } else {
                this.section = "";
                this.key = key;
            }
        }
    }
}
