package alura.orcamentofamiliar.usuario.adapter.out.persistence;

import alura.orcamentofamiliar.usuario.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByLogin(String login);
}
