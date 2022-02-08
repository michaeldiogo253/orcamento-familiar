package alura.orcamentofamiliar.auth.adapter.in.web.request;

import lombok.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.validation.constraints.NotBlank;

@Value
public class LoginRequest {

    @NotBlank String login;
    @NotBlank String senha;

    public UsernamePasswordAuthenticationToken converterToken() {

        return new UsernamePasswordAuthenticationToken(login, senha);
    }

}
