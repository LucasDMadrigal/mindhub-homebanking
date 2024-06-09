package com.mindhub.homebanking.ServicesSecurity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.xml.crypto.Data;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtilService {
    private static final SecretKey SECTRET_KEY = Jwts.SIG.HS256.key().build();

    private static final long EXP_TOKEN = 1000 * 60 * 60;

    public Claims extractAllClaims(String token) {
        return Jwts.parser().verifyWith(SECTRET_KEY).build().parseSignedClaims(token).getPayload();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsTFunction) {

        final Claims claims = extractAllClaims(token);
        return claimsTFunction.apply(claims);
    }

    public String extractUserName(String token) {return extractClaim(token,Claims::getSubject);}

    public Date extractExpiration(String token){ return extractClaim(token, Claims::getExpiration);}

    public boolean isTokenExpired(String token){return extractExpiration(token).before(new Date());}

    private String createToken(Map<String,Object> claims, String username){
        return Jwts.builder().claims(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXP_TOKEN))
                .signWith(SECTRET_KEY)
                .compact();
    }

    public String generateToken(UserDetails userDetails){
        Map<String, Object> claims = new HashMap<>();
        String rol = userDetails.getAuthorities().iterator().next().getAuthority();
        claims.put("rol", rol);

        return createToken(claims, userDetails.getUsername());
    }

//    public Boolean validateToken(String token, UserDetails userDetails){}
}
