package org.basvalk.ipwrcback.Security;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.basvalk.ipwrcback.Util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String path = request.getRequestURI();
        System.out.println("JWT Filter - Incoming request: " + path);


        if (path.startsWith("/api/auth") || path.startsWith("/api/categories") || path.startsWith("/api/products")) {
            chain.doFilter(request, response);
            return;
        }
        
        final String authHeader = request.getHeader("Authorization");
        System.out.println("JWT Filter - Authorization header: " + authHeader);

       if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("JWT Filter - No bearer token, continuing filter chain");
           chain.doFilter(request, response);
           return;
       }

        String jwt = authHeader.substring(7);
        String username = jwtUtil.extractUsername(jwt);
        System.out.println("JWT Filter - Extracted username: " + username);


        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (jwtUtil.validateToken(jwt, userDetails.getUsername())) {
                UsernamePasswordAuthenticationToken token =
                        new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities()
                        );
                token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                System.out.println("JWT Filter - Token validated for: " + username);
                SecurityContextHolder.getContext().setAuthentication(token);
            } else {
                System.out.println("JWT Filter - Invalid token for: " + username);
            }
        }

        chain.doFilter(request, response);
    }
}
