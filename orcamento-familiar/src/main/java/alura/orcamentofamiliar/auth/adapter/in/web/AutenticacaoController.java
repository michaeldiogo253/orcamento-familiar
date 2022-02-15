package alura.orcamentofamiliar.auth.adapter.in.web;

import alura.orcamentofamiliar.auth.adapter.in.web.request.LoginRequest;
import alura.orcamentofamiliar.auth.adapter.in.web.response.TokenResponse;
import alura.orcamentofamiliar.auth.application.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Profile("prod")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AutenticacaoController {

    private  AuthenticationManager authManager;

    private final TokenService tokenService;

    @PostMapping
    public ResponseEntity<TokenResponse> autenticar(@RequestBody @Valid LoginRequest request) {

        UsernamePasswordAuthenticationToken dadosLogin = request.converterToken();

        try {

            Authentication authentication = authManager.authenticate(dadosLogin);

            String token = tokenService.gerarToken(authentication);

            return ResponseEntity.ok(new TokenResponse(token, "Bearer"));

        } catch (AuthenticationException e) {

            return ResponseEntity.badRequest()
                                 .build();
        }
    }

}
