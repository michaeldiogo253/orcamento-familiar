package alura.orcamentofamiliar.usuario.application.port.out;

import alura.orcamentofamiliar.usuario.domain.Usuario;

public interface FindUsuarioByIdPort {

    Usuario findUsuarioById(Long id);

}
