package com.scaler.authdemo.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {
    //secret key should be stored in a secure place like aws password manager and not in the code and used for signing the token
    public static final String SECRET = "khb7k2jmn4b67k2jn35bl2j4bk5n2b6kn42";
    // algorithm used for signing the token
    Algorithm algorithm = Algorithm.HMAC256(SECRET);

    //algorithm used for creating the token
    public String createJwt(String username) {
        if (username == null) {
            throw new IllegalArgumentException("Cannot create JWT with blank username");
        }

        return JWT.create()
                .withSubject(username)
                .withIssuedAt(new Date())
                .sign(algorithm);
    }

    //algorithm used for verifying the token
    public String getUsernameFromJwt(String token) {
        return JWT.require(algorithm)
                .build()
                .verify(token)
                .getSubject();
    }
}
