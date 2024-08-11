package com.ieris19.lib.utils.registry;

import java.lang.reflect.Modifier;
import java.util.Optional;

public record RegistryEntry<T>(Class<? extends T> contract, T implementation) {
    public RegistryEntry {
        validateEntry(contract, implementation).ifPresent(e -> {throw e;});
    }

    public static Optional<RuntimeException> validateEntry(Class<?> contract, Object implementation) {
        if (contract == null && implementation == null) {
            return Optional.of(new NullPointerException("Entry contract and implementation cannot be null"));
        }
        if (Modifier.isAbstract(implementation.getClass().getModifiers())) {
            return Optional.of(new IllegalArgumentException("Entry " + implementation.getClass().getName() + " implementation must be an instance of a concrete class"));
        }
        if (!contract.isInterface()) {
            return Optional.of(new IllegalArgumentException("Entry contract " + contract.getName() + " must be a class instance representing an interface"));
        }
        if (!contract.isAssignableFrom(implementation.getClass())) {
            return Optional.of(new IllegalArgumentException("Entry " + implementation.getClass().getName() + " must implement the contract" + contract.getName()));
        }
        return Optional.empty();
    }
}
