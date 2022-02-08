package alura.orcamentofamiliar.usuario.application.port.out;

import alura.orcamentofamiliar.usuario.domain.Usuario;

public interface FindUsuarioByLoginPort {

    Usuario findUsuarioByLogin(String login);
}
