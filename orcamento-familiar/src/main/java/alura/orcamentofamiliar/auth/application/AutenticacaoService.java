package alura.orcamentofamiliar.auth.application;

import alura.orcamentofamiliar.usuario.application.port.out.FindUsuarioByLoginPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AutenticacaoService implements UserDetailsService {

    private final FindUsuarioByLoginPort findUsuarioByLoginPort;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return findUsuarioByLoginPort.findUsuarioByLogin(username);
    }
}
