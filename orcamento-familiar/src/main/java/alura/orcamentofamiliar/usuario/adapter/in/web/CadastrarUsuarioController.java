package alura.orcamentofamiliar.usuario.adapter.in.web;

import alura.orcamentofamiliar.usuario.adapter.in.web.request.CadastrarUsuarioRequest;
import alura.orcamentofamiliar.usuario.adapter.in.web.response.UsuarioResponse;
import alura.orcamentofamiliar.usuario.application.service.CadastrarUsuarioUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/orcamento-familiar")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CadastrarUsuarioController {

    private final CadastrarUsuarioUseCase cadastrarUsuarioUseCase;

    @PostMapping("/cadastrar-usuario")
    public ResponseEntity<UsuarioResponse> execute(@RequestBody @Valid CadastrarUsuarioRequest request) {

        CadastrarUsuarioUseCase.OutputValues output =
                cadastrarUsuarioUseCase.execute(new CadastrarUsuarioUseCase.InputValues(
                request.getNome(),
                request.getLogin(),
                request.getSenha()));

        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(UsuarioResponse.from(output.getUsuario()));
    }

}
