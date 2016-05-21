package com.sanjnan.server.sanjnan.security;

import com.sanjnan.server.sanjnan.security.token.SanjnanPrincipal;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created by vinay on 1/28/16.
 */

public class SanjnanAuditorAware implements AuditorAware<String> {

    @Override
    public String getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        return ((SanjnanPrincipal) authentication.getPrincipal()).getName();
    }
}