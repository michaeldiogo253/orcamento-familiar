package alura.orcamentofamiliar.usuario.application.service;

import alura.orcamentofamiliar.usuario.application.port.out.SalvarUsuarioPort;
import alura.orcamentofamiliar.usuario.domain.Usuario;
import alura.orcamentofamiliar.util.UseCase;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CadastrarUsuarioUseCase
        extends UseCase<CadastrarUsuarioUseCase.InputValues, CadastrarUsuarioUseCase.OutputValues> {

    private final SalvarUsuarioPort salvarUsuarioPort;

    @Override
    public OutputValues execute(InputValues input) {

        String senhaCriptografada = new BCryptPasswordEncoder().encode(input.getSenha());

        Usuario usuario = new Usuario(input.getNome(), input.getLogin(), senhaCriptografada);

        salvarUsuarioPort.salvarUsuario(usuario);
        return CadastrarUsuarioUseCase.OutputValues.of(usuario);
    }

    @Value
    public static class InputValues implements UseCase.InputValues {

        String nome;
        String login;
        String senha;
    }

    @Value
    @RequiredArgsConstructor(staticName = "of")
    public static class OutputValues implements UseCase.OutputValues {

        Usuario usuario;
    }

}