package com.ieris19.lib.utils.registry;

public interface LocalGenericRegistry<T> extends GenericRegistry<T> {
    default void registerEntry(Class<? extends T> contract, T implementation) throws NullPointerException {
        registerEntry(new RegistryEntry<>(contract, implementation));
    }
    <C extends T> C getEntry(Class<C> contract) throws NullPointerException;
    void registerEntry(RegistryEntry<T> entry) throws NullPointerException;
}
