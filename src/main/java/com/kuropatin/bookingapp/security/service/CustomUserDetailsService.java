package com.kuropatin.bookingapp.security.service;

import com.kuropatin.bookingapp.model.User;
import com.kuropatin.bookingapp.security.util.SecuredUser;
import com.kuropatin.bookingapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getUserByLogin(username);
        return new SecuredUser(
                user.getId(),
                user.getLogin(),
                user.getPassword(),
                AuthorityUtils.createAuthorityList(String.valueOf(user.getRole()))
        );
    }
}