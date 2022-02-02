package alura.orcamentofamiliar.usuario.adapter.in.web.request;

import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Value
public class CadastrarUsuarioRequest {

    @NotNull @NotBlank String nome;
    @NotNull @NotBlank String login;
    @NotNull @NotBlank String senha;

}
