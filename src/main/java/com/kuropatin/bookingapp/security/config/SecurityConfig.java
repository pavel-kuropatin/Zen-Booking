package com.kuropatin.bookingapp.security.config;

import com.kuropatin.bookingapp.security.filter.AuthenticationTokenFilter;
import com.kuropatin.bookingapp.security.util.TokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final TokenUtils tokenUtils;
    private final BCryptPasswordEncoder passwordEncoder;

    @Bean
    public static BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public AuthenticationTokenFilter authenticationTokenFilterBean(AuthenticationManager authenticationManager) {
        AuthenticationTokenFilter authenticationTokenFilter = new AuthenticationTokenFilter(tokenUtils, userDetailsService);
        authenticationTokenFilter.setAuthenticationManager(authenticationManager);
        return authenticationTokenFilter;
    }

    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                .exceptionHandling()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/register/**").permitAll()
                .antMatchers("/login/**").permitAll()
                .antMatchers("/user/**").hasRole("USER")
                .antMatchers("/profile/**").hasRole("USER")
                .antMatchers("/hosting/**").hasRole("USER")
                .antMatchers("/booking/**").hasRole("USER")
                .antMatchers("/order/**").hasRole("USER")
                .antMatchers("/review/**").hasRole("USER")
                .antMatchers("/administration/**").hasRole("ADMIN")
                .antMatchers("/moderation/**").hasRole("MODER")
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(authenticationTokenFilterBean(authenticationManagerBean()), UsernamePasswordAuthenticationFilter.class);
    }

    //For swagger access only
    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers("/v2/api-docs","/configuration/ui/**", "/swagger-resources/**", "/configuration/security/**", "/swagger-ui/**", "/webjars/**");
    }
}