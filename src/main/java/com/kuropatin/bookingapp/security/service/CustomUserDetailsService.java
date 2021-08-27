package com.kuropatin.bookingapp.security.service;

import com.kuropatin.bookingapp.exception.BadCredentialsException;
import com.kuropatin.bookingapp.model.Admin;
import com.kuropatin.bookingapp.model.User;
import com.kuropatin.bookingapp.security.model.SecurityUser;
import com.kuropatin.bookingapp.service.AdminService;
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
    private final AdminService adminService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(userService.existsByLogin(username)) {
            User user = userService.getUserByLogin(username);
            return new SecurityUser(
                    user.getId(),
                    user.getLogin(),
                    user.getPassword(),
                    AuthorityUtils.createAuthorityList(String.valueOf(user.getRole()))
            );
        } else if (adminService.existsByLogin(username)) {
            Admin admin = adminService.getAdminByLogin(username);
            return new SecurityUser(
                    admin.getId(),
                    admin.getLogin(),
                    admin.getPassword(),
                    AuthorityUtils.createAuthorityList(String.valueOf(admin.getRole()))
            );
        } else {
            throw new BadCredentialsException();
        }
    }
}