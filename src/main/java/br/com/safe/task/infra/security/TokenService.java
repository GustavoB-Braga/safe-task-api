package br.com.safe.task.infra.security;

import br.com.safe.task.domain.user.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(User user) {

        try {

            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.create()
                    .withIssuer("task-api")
                    .withSubject(user.getLogin())
                    .withExpiresAt(generateExpirationDate())
                    .sign(algorithm);

        } catch (JWTCreationException exception) {

            throw new RuntimeException("Erro ao gerar token JWT", exception);

        }

    }

    private Instant generateExpirationDate() {
        return LocalDateTime.now()
                .plusHours(2)
                .toInstant(ZoneOffset.of("-03:00"));
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    // specify any specific claim validations
                    .withIssuer("task-api")
                    .build();

            DecodedJWT decodedJWT = verifier.verify(token);

            return decodedJWT.getSubject();
        } catch (JWTVerificationException exception) {
            // Invalid signature/claims
        }
        return null;
    }

}