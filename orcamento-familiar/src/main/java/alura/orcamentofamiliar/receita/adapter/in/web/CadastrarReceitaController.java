package alura.orcamentofamiliar.receita.adapter.in.web;

import alura.orcamentofamiliar.receita.adapter.in.web.response.ReceitaResponse;
import alura.orcamentofamiliar.receita.application.service.CadastrarReceitaUseCase;
import alura.orcamentofamiliar.receita.domain.Receita;
import alura.orcamentofamiliar.util.exceptions.BussinessRuleException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/orcamento-familiar")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CadastrarReceitaController {

    private final CadastrarReceitaUseCase useCase;

    @PostMapping("/receitas/cadastrar-receita")
    public ResponseEntity<ReceitaResponse> execute(@RequestBody @Valid CadastrarReceitaUseCase.InputValues input) {

        try {
            CadastrarReceitaUseCase.OutputValues output = useCase.execute(input);
            Receita receita = output.getReceita();

            return ResponseEntity.status(HttpStatus.CREATED)
                                 .body(ReceitaResponse.from(receita));

        } catch (BussinessRuleException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

}
