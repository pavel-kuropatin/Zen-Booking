package com.kuropatin.zenbooking.security.filter;

import com.kuropatin.zenbooking.model.response.ErrorResponse;
import com.kuropatin.zenbooking.security.config.SecurityConfig;
import com.kuropatin.zenbooking.security.util.CustomHeaders;
import com.kuropatin.zenbooking.security.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Log4j2
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtUtils jwtUtils;
    private final UserDetailsService userDetailsService;

    @Override
    public void doFilter(@NonNull final ServletRequest request, @NonNull final ServletResponse response, @NonNull final FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String jwtToken = httpRequest.getHeader(CustomHeaders.X_AUTH_TOKEN);
        try {
            if (isAuthorizationRequired(httpRequest.getRequestURI())) {
                String username = jwtUtils.getUsernameFromToken(jwtToken);
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    if (jwtUtils.validateToken(jwtToken, userDetails)) {
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            }
            chain.doFilter(request, response);
        } catch (Exception e) {
            log.warn(httpRequest.getRequestURL() + " " + e.getClass().getName() + ": " + e.getMessage());
            processException(e, (HttpServletResponse) response);
        }
    }

    private boolean isAuthorizationRequired(String requestUrl) {
        final List<String> endpoints = SecurityConfig.AUTHORIZED_ENDPOINTS;
        for (final String endpoint : endpoints) {
            if (requestUrl.contains(endpoint)) {
                return true;
            }
        }
        return false;
    }

    private void processException(final Exception e, final HttpServletResponse response) throws IOException {
        final HttpStatus status = HttpStatus.UNAUTHORIZED;
        final ErrorResponse errorResponse = new ErrorResponse(e, status);

        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.getWriter().write(errorResponse.toString());
    }
}