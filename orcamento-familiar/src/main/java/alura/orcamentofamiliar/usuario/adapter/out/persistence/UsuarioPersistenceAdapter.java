package alura.orcamentofamiliar.usuario.adapter.out.persistence;

import alura.orcamentofamiliar.usuario.application.port.out.SalvarUsuarioPort;
import alura.orcamentofamiliar.usuario.domain.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UsuarioPersistenceAdapter implements SalvarUsuarioPort {

    private final UsuarioRepository usuarioRepository;

    @Override
    public void salvarUsuario(Usuario usuario) {

        usuarioRepository.save(usuario);
    }
}
