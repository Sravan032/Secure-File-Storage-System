package com.sravan.Secure.File.Storage.Security;

import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {
    private static final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    public String GenerateToken(String username){
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()*1000*60*60))
                .signWith(SignatureAlgorithm.HS256,SECRET_KEY)
                .compact();
    }

    public static String extractUsername(String token){
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean istokenvalid(String token,String username){
        String extractedUsername = extractUsername(token);
        return extractedUsername.equals(username);
    }


}
