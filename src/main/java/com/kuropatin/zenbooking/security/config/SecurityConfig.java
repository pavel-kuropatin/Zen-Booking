package com.kuropatin.zenbooking.security.config;

import com.kuropatin.zenbooking.model.Roles;
import com.kuropatin.zenbooking.security.filter.JwtAuthenticationFilter;
import com.kuropatin.zenbooking.security.service.CustomUserDetailsService;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    public static final String REGISTER_ENDPOINT = "/register";
    public static final String LOGIN_ENDPOINT = "/login";

    private final CustomUserDetailsService userDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CorsConfig corsConfig;
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

    @Autowired
    public void configureAuthentication(final AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(final HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .httpBasic().disable()
                .csrf().disable()
                .cors().configurationSource(corsConfig)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(REGISTER_ENDPOINT).permitAll()
                .antMatchers(LOGIN_ENDPOINT).permitAll()
                .antMatchers("/profile/**").hasRole(Roles.ROLE_USER.label)
                .antMatchers("/hosting/**").hasRole(Roles.ROLE_USER.label)
                .antMatchers("/booking/**").hasRole(Roles.ROLE_USER.label)
                .antMatchers("/order/**").hasRole(Roles.ROLE_USER.label)
                .antMatchers("/review/**").hasRole(Roles.ROLE_USER.label)
                .antMatchers("/moderation/**").hasRole(Roles.ROLE_MODER.label)
                .antMatchers("/administration/**").hasRole(Roles.ROLE_ADMIN.label)
                .anyRequest().authenticated();
    }

    //For swagger access only
    @Override
    public void configure(final WebSecurity web) {
        web.ignoring()
                .antMatchers("/v2/api-docs","/configuration/ui/**", "/swagger-resources/**", "/configuration/security/**", "/swagger-ui/**", "/webjars/**");
    }
}