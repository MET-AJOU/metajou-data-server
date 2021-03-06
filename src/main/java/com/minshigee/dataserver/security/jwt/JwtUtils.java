package com.minshigee.dataserver.security.jwt;

import com.minshigee.dataserver.exception.ErrorCode;
import com.minshigee.dataserver.security.entity.CustomAuthentication;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {

    private static JwtUtils instance;

    @Synchronized
    public static JwtUtils getInstance (){
        return JwtUtils.instance;
    }

    private JwtUtils() {
        instance = this;
    }

    @Value("${spring.project.jjwt.secretkey}")
    private String secret;
    @Value("${spring.project.jjwt.tokenname}")
    private String tokenName;
    @Value("${spring.service.root.domain}")
    private String serviceDomains;

    private Key key;

    @PostConstruct
    public void initialize() {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    public Long getUserCodeFromToken(String token) {
        return Long.valueOf(getAllClaimsFromToken(token).getSubject());
    }

    public Date getExpirationDateFromToken(String token) {
        return getAllClaimsFromToken(token).getExpiration();
    }

    private Boolean isTokenExpired(String token) {
        try {
            final Date expiration = getExpirationDateFromToken(token);
            return expiration.before(new Date());
        } catch (Exception e) {
            return true;
        }
    }

    public Boolean validateToken(String token) {
        return !isTokenExpired(token);
    }

    public Authentication getAuthentication(String token) {
        CustomAuthentication authentication = new CustomAuthentication(
                getUserCodeFromToken(token),
                getAllClaimsFromToken(token),
                token
        );
        authentication.setIsAuthenticated(validateToken(token));

        return authentication;
    }

    public Boolean isAppropriateRequestForFilter(ServerHttpRequest request) {
        if (!request.getCookies().containsKey(tokenName))
            return false;
        String token = request.getCookies().getFirst(tokenName).getValue();
        return validateToken(token);
    }

    public Boolean isAppropriateRequestForFilter(MultiValueMap<String,HttpCookie> cookies) {
        if (!cookies.containsKey(tokenName))
            return false;
        String token = resolveToken(cookies);
        return validateToken(token);
    }

    public String resolveToken(ServerHttpRequest request) {
        if(request.getCookies().getFirst(tokenName) == null)
            throw ErrorCode.NOT_FOUND_AUTHINFO.build();
        return request.getCookies().getFirst(tokenName).getValue();
    }

    public String resolveToken(MultiValueMap<String,HttpCookie> cookies) {
        if(cookies.getFirst(tokenName) == null)
            throw ErrorCode.NOT_FOUND_AUTHINFO.build();
        return cookies.getFirst(tokenName).getValue();
    }
}