package com.hcmute.project.instagram.backend.jwt;

import com.hcmute.project.instagram.backend.domain.aggregate.useraggregate.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.function.Function;

/**
 * Provider for JWT Token Authorization
 */
@Component
public class JwtTokenProvider {
    private static final Logger LOG = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Autowired
    private JwtConfigurationModel jwtConfigurationModel;

    public JwtTokenProvider() {

    }

    public void testJWTIsLoaded() {
        System.out.println("SECRET = " + this.jwtConfigurationModel.getSecret());
        System.out.println("ISSUER = " + this.jwtConfigurationModel.getIssuer());
        System.out.println("AUDIENCE = " + this.jwtConfigurationModel.getAudience());
        System.out.println("EXPIRED = " + this.jwtConfigurationModel.getExpired());
    }

    public Claims extractAllClaims(String jwtToken) {
        return Jwts.parser()
                .setSigningKey(this.jwtConfigurationModel.getSecret())
                .parseClaimsJws(jwtToken)
                .getBody();
    }

    public Integer getUserIdFromToken(String jwtToken) {
        Claims claims = this.extractAllClaims(jwtToken);
        String subject = claims.getSubject();
        try {
            return Integer.parseInt(subject);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public String extractUsername(String jwtToken) {
        return this.extractClaim(jwtToken, Claims::getSubject);
    }

    public <T> T extractClaim(String jwtToken, Function<Claims, T> claimsResolver) {
        final Claims claims = this.extractAllClaims(jwtToken);
        return claimsResolver.apply(claims);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        Date now = new Date();
        Date expired = new Date(now.getTime() + this.jwtConfigurationModel.getExpired());

        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(now)
                .setExpiration(expired)
                .signWith(SignatureAlgorithm.HS512, this.jwtConfigurationModel.getSecret())
                .compact();
    }

    private Date extractExpiration(String jwtToken) {
        return this.extractClaim(jwtToken, Claims::getExpiration);
    }

    private boolean isTokenExpired(String jwtToken) {
        return this.extractExpiration(jwtToken).before(new Date());
    }

    public boolean isTokenValid(String jwtToken, UserDetails userDetails) {
        final String username = this.extractUsername(jwtToken);
        return username.equals(userDetails.getUsername()) && !this.isTokenExpired(jwtToken);
    }

    public String generateToken(User userEntity) {
        Date now = new Date();
        Date expired = new Date(now.getTime() + this.jwtConfigurationModel.getExpired());
        return Jwts.builder()
                .setSubject(userEntity.getUsername())
                .setIssuedAt(now)
                .setExpiration(expired)
                .signWith(SignatureAlgorithm.HS512, this.jwtConfigurationModel.getSecret())
                .compact();
    }
}