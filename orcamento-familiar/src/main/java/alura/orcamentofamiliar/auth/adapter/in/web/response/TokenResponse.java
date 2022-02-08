package alura.orcamentofamiliar.auth.adapter.in.web.response;

import lombok.Value;

@Value
public class TokenResponse {

    String token;
    String tipo;

}
