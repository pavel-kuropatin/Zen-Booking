package com.kuropatin.zenbooking.security.service;

import com.kuropatin.zenbooking.exception.BadCredentialsException;
import com.kuropatin.zenbooking.model.Admin;
import com.kuropatin.zenbooking.model.User;
import com.kuropatin.zenbooking.security.model.SecurityUser;
import com.kuropatin.zenbooking.service.AdminService;
import com.kuropatin.zenbooking.service.UserService;
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
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        if (userService.existsByLogin(username)) {
            final User user = userService.getUserByLogin(username);
            return new SecurityUser(
                    user.getId(),
                    user.getLogin(),
                    user.getPassword(),
                    AuthorityUtils.createAuthorityList(String.valueOf(user.getRole()))
            );
        } else if (adminService.existsByLogin(username)) {
            final Admin admin = adminService.getAdminByLogin(username);
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