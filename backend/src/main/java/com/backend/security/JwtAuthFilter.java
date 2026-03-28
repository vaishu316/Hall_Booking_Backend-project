package com.backend.security;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.backend.Repositories.AdminReposistory;
import com.backend.Repositories.UserReposistoy;
import com.backend.model.Admin;
import com.backend.model.User;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserReposistoy userReposistoy;
    private final AdminReposistory adminReposistory;

    public JwtAuthFilter(JwtUtil jwtUtil, UserReposistoy userReposistoy, AdminReposistory adminReposistory) {
        this.jwtUtil = jwtUtil;
        this.userReposistoy = userReposistoy;
        this.adminReposistory = adminReposistory;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        String token = null;
        String email = null;

        try {
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                token = authHeader.substring(7);
                email = jwtUtil.extractUsername(token);
                System.out.println("Extracted email: " + email);
            } else {
                System.out.println("No token found, continuing without auth...");
                filterChain.doFilter(request, response);
                return;
            }

            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                User user = userReposistoy.findByEmail(email);
                Admin admin = adminReposistory.findByEmail(email);

                if (jwtUtil.validateToken(token, email)) {
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(email, null, new ArrayList<>());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    System.out.println("User authenticated: " + email);
                } else {
                    System.out.println("Token validation failed.");
                }
            }

        } catch (ExpiredJwtException ex) {
            System.out.println("Token expired: " + ex.getMessage());
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token expired");
            return;

        } catch (UnsupportedJwtException | MalformedJwtException  | IllegalArgumentException ex) {
            System.out.println("Invalid JWT: " + ex.getMessage());
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
            return;
        }

        filterChain.doFilter(request, response);
    }
}
