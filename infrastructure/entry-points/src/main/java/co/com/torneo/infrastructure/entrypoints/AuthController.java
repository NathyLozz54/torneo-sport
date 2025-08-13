package co.com.torneo.infrastructure.entrypoints;

import com.torneo.infra.drive.jwt.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtUtil jwtUtil;

    public AuthController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public Mono<ResponseEntity<Map<String, String>>> login(@RequestBody LoginRequest loginRequest) {
        // Aquí debes validar usuario y contraseña con tu lógica real
        if ("Nath".equals(loginRequest.getUsername()) && "admin123".equals(loginRequest.getPassword())) {
            List<String> roles = List.of("ROLE_USER");
            String token = jwtUtil.generateToken(loginRequest.getUsername(), roles);
            return Mono.just(ResponseEntity.ok(Map.of("token", token)));
        } else {
            return Mono.just(ResponseEntity.status(401).build());
        }
    }

    public static class LoginRequest {
        private String username;
        private String password;

        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }

        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }
}
