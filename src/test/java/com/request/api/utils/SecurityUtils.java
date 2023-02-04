package com.request.api.utils;

import org.mockito.Mockito;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    public static void MockAdminLogged(Object admin) {
        Authentication mockAuth = Mockito.mock(Authentication.class);
        Mockito.when(mockAuth.getPrincipal()).thenReturn(admin);

        SecurityContext mockSecurityContext = Mockito.mock(SecurityContext.class);

        Mockito.when(mockSecurityContext.getAuthentication()).thenReturn(mockAuth);
        SecurityContextHolder.setContext(mockSecurityContext);
    }

}
