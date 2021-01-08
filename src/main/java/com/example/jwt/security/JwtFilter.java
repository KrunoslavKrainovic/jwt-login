package com.example.jwt.security;

import com.example.jwt.domain.JwtTokenGenerator;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtFilter extends OncePerRequestFilter {

  private JwtTokenGenerator jwtTokenGenerator;

  public JwtFilter(JwtTokenGenerator jwtTokenGenerator) {
    this.jwtTokenGenerator = jwtTokenGenerator;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
      FilterChain filterChain) throws ServletException, IOException {

    String header = httpServletRequest.getHeader("Authorization");

    if (header != null && header.startsWith("Bearer ")) {
      String token = header.substring(7, header.length());

      if (jwtTokenGenerator.isValid(token)) {
        Authentication authentication = jwtTokenGenerator.getAuthentication(token);

        SecurityContextHolder.getContext().setAuthentication(authentication);

      }


    }

    filterChain.doFilter(httpServletRequest, httpServletResponse);

  }
}
