package med.voll.api.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import med.voll.api.domain.usuarios.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {
    @Value("${api.security.secret}") // exportar el valor de la variable
    private String apiSecret;
    public String generarToken(Usuario usuario){
        String token;
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            token = JWT.create()
                    .withIssuer("voll med")
                    .withSubject(usuario.getLogin())
                    .withClaim("id", usuario.getId())
                    .withExpiresAt(generarFechaExp())
                    .sign(algorithm);

        }catch (JWTCreationException e){
            throw new RuntimeException();
        }
        return token;
    }

    private Instant generarFechaExp(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-06:00"));
    }
}
