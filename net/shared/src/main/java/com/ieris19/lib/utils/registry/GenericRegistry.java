package com.ieris19.lib.utils.registry;

public interface GenericRegistry<T> {
    default void registerEntry(Class<? extends T> contract, T implementation) throws Exception {
        registerEntry(new RegistryEntry<>(contract, implementation));
    }
    <C extends T> C getEntry(Class<C> contract) throws Exception;
    void registerEntry(RegistryEntry<T> entry) throws Exception;
}
