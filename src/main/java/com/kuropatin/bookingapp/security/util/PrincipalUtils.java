package com.kuropatin.bookingapp.security.util;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Collection;

@Service
public class PrincipalUtils {

    public long getId(Principal principal) {
        Object castedPrincipal = ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        return ((SecuredUser) castedPrincipal).getId();
    }

    public String getUsername(Principal principal) {
        Object castedPrincipal = ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        return ((SecuredUser) castedPrincipal).getUsername();
    }

    public String getPassword(Principal principal) {
        Object castedPrincipal = ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        return ((SecuredUser) castedPrincipal).getPassword();
    }

    public Collection<GrantedAuthority> getAuthorities(Principal principal) {
        Object castedPrincipal = ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        return ((SecuredUser) castedPrincipal).getAuthorities();
    }
}