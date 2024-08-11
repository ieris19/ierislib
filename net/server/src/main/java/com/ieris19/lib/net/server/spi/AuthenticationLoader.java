package com.ieris19.lib.net.server.spi;

import com.ieris19.lib.net.auth.spi.AuthenticationProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

public class AuthenticationLoader {
    private static String preferredProvider;

    public static void setPreferredProvider(String provider) {
        preferredProvider = provider;
    }

    public static List<AuthenticationProvider> providers() {
        List<AuthenticationProvider> services = new ArrayList<>();
        ServiceLoader<AuthenticationProvider> loader = ServiceLoader.load(AuthenticationProvider.class);
        loader.forEach(services::add);
        return services;
    }

    public static AuthenticationProvider defaultProvider() {
        return DefaultAuthenticationValidator::new;
    }

    public static AuthenticationProvider loadProvider() {
        List<AuthenticationProvider> providers = providers();
        if (providers.isEmpty()) {
            return defaultProvider();
        }
        for (AuthenticationProvider provider : providers) {
            if (preferredProvider == null) {
                return provider;
            }
            if (provider.getClass().getName().equals(preferredProvider)) {
                return provider;
            }
        }
        return defaultProvider();
    }
}
