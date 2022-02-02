package alura.orcamentofamiliar.usuario.adapter.in.web.response;

import alura.orcamentofamiliar.usuario.domain.Usuario;
import lombok.Value;

import java.util.List;
import java.util.stream.Collectors;

@Value
public class UsuarioResponse {

    String nome;
    String login;
    String senha;

    public static UsuarioResponse from(Usuario usuario) {

        return new UsuarioResponse(usuario.getNome(), usuario.getLogin(), usuario.getSenha());
    }

    public static List<UsuarioResponse> from(List<Usuario> usuarios) {

        return usuarios.stream()
                       .map(UsuarioResponse::from)
                       .collect(Collectors.toList());
    }

}
