package com.ieris19.lib.utils.registry;

import java.util.HashMap;
import java.util.Map;

public class BaseLocalRegistry<T> implements LocalGenericRegistry<T> {
    protected final Map<Class<? extends T>, T> entries;

    public BaseLocalRegistry() {
        super();
        this.entries = new HashMap<>();
    }

    public synchronized void registerEntry(Class<? extends T> contract, T implementation) throws NullPointerException {
        this.registerEntry(new RegistryEntry<>(contract, implementation));
    }

    @Override
    public <C extends T> C getEntry(Class<C> contract) throws NullPointerException {
        return contract.cast(entries.get(contract));
    }

    @Override
    public void registerEntry(RegistryEntry<T> entry) throws NullPointerException {
        entries.put(entry.contract(), entry.implementation());
    }
}
