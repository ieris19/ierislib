package com.ieris19.lib.net.auth.spi;

public interface AuthenticationProvider {
    AuthenticationValidator getValidator();
}
