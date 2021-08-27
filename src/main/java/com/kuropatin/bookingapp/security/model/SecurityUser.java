package com.kuropatin.bookingapp.security.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class SecurityUser extends User {

    long id;

    public SecurityUser(long id, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        setId(id);
    }
}
