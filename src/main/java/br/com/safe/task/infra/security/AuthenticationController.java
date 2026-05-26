package br.com.safe.task.infra.security;

import br.com.safe.task.domain.user.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService service;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDto dto) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(dto.login(), dto.password());

        var authentication = manager.authenticate(authenticationToken);

        var token = service.generateToken((User) authentication.getPrincipal());

        return ResponseEntity.ok(new TokenJWTDto(token));
    }
}
