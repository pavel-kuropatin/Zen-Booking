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
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
@Log4j2
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final UserDetailsService userDetailsService;

    @Override
    public void doFilterInternal(@NonNull final HttpServletRequest request, @NonNull final HttpServletResponse response, @NonNull final FilterChain chain) throws IOException, ServletException {
        String jwtToken = request.getHeader(CustomHeaders.X_AUTH_TOKEN);
        try {
            if (isAuthorizationRequired(String.valueOf(request.getRequestURL()))) {
                String username = jwtUtils.getUsernameFromToken(jwtToken);
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    if (jwtUtils.validateToken(jwtToken, userDetails)) {
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            }
            chain.doFilter(request, response);
        } catch (Exception e) {
            log.warn(request.getRequestURL() + " " + e.getClass().getName() + ": " + e.getMessage());
            processException(e, response);
        }
    }

    private boolean isAuthorizationRequired(String requestUrl) {
        final List<String> endpoints = List.of(SecurityConfig.REGISTER_ENDPOINT, SecurityConfig.LOGIN_ENDPOINT);
        for (final String endpoint : endpoints) {
            if (requestUrl.endsWith(endpoint)) {
                return false;
            }
        }
        return true;
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