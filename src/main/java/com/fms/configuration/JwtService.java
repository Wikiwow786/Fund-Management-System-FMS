package com.fms.configuration;
import com.fms.security.SecurityUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${SECRET_KEY}")
    public String SECRET_KEY;
    @Value("${jwt.expiration}")
    private Long expiredAt;


    public Date getExpiredAt() {
        return new Date(System.currentTimeMillis() + expiredAt);
    }
    public Long extractUserId(String token) {
        return Long.parseLong(extractClaim(token, Claims::getSubject));
    }


    public <T> T extractClaim(String token, Function<Claims,T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);

    }

    public String generateToken(Map<String, Objects> extraClaims,
                                UserDetails userDetails){
        return Jwts.builder()
                .claims(extraClaims)
              //  .subject(userDetails.getUsername())
                .subject(String.valueOf(((SecurityUser) userDetails).getUserId()))
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(getExpiredAt())
                .signWith(getSignInKey())
                .compact();

    }
    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(),userDetails);
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        Long tokenUserId = extractUserId(token);
        return tokenUserId.equals(((SecurityUser) userDetails).getUserId()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token,Claims::getExpiration);
    }

    private Claims extractAllClaims(String token){
        return Jwts.parser()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }


}
