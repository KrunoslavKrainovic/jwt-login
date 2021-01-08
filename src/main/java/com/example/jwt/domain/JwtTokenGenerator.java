package com.example.jwt.domain;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenGenerator {


  private static final String AUTHORITIES_KEY = "auth";

  private Key key;

  private long tokenValidityInMilliseconds;

  private int tokenValiditySeconds = 10000;


  private String base64Secret = "2dN4QYD52rsuSEkMPa9m7SFbFwDSS2pkDSaEo3WhFkve4X16GbAnIJicULK5048EuXqlijpsEvCj7yI7xKlMKA==";

  @PostConstruct

  public void init() {
    byte[] keyBytes;
    keyBytes = Decoders.BASE64.decode(base64Secret);
    this.key = Keys.hmacShaKeyFor(keyBytes);
    this.tokenValidityInMilliseconds = 1000 * tokenValiditySeconds;
  }


  public String createToken(String username,String role) {

    long now = (new Date()).getTime();
    Date validity;
    validity = new Date(now + this.tokenValidityInMilliseconds);

    String token = Jwts
        .builder()
        .setSubject(username)
        .claim(AUTHORITIES_KEY, role)
        .signWith(key, SignatureAlgorithm.HS512)
        .setExpiration(validity)
        .compact();

    System.out.println("Created JWT token for user {}. Expires on {}" +  username + validity);

    return token;
  }


  public Authentication getAuthentication(String token) {
    Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();

    Collection<? extends GrantedAuthority> authorities = Arrays
        .stream(claims.get(AUTHORITIES_KEY).toString().split(","))
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toList());

    org.springframework.security.core.userdetails.User principal = new org.springframework.security.core.userdetails.User(
        claims.getSubject(), "", authorities);

    return new UsernamePasswordAuthenticationToken(principal, token, authorities);
  }


  public boolean isValid(String authToken) {
    try {

      Jwts.parser().setSigningKey(key).parseClaimsJws(authToken);
      return true;

    } catch (JwtException | IllegalArgumentException e) {
      System.out.println("Invalid JWT token");
      System.out.println("Invalid JWT token trace.");
    }
    return false;
  }

  }


