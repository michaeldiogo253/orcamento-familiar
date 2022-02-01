package alura.orcamentofamiliar.despesa.adapter.web.in;

import alura.orcamentofamiliar.despesa.adapter.web.in.request.CadastrarDespesaRequest;
import alura.orcamentofamiliar.despesa.adapter.web.in.response.DespesaResponse;
import alura.orcamentofamiliar.despesa.application.CadastrarDespesaUseCase;
import alura.orcamentofamiliar.despesa.domain.Categoria;
import alura.orcamentofamiliar.util.exceptions.BussinessRuleException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
@RequestMapping("/orcamento-familiar")
public class CadastrarDespesaController {

    private final CadastrarDespesaUseCase useCase;

    @PostMapping("/despesas/cadastrar-despesa")
    public ResponseEntity<DespesaResponse> execute(@RequestBody @Valid CadastrarDespesaRequest request,
                                                   @RequestParam(name = "categoria", required = false)
                                                           String categoria) {

        try {

            CadastrarDespesaUseCase.OutputValues output = useCase.execute(new CadastrarDespesaUseCase.InputValues(
                    request.getDescricao(),
                    request.getValor(),
                    request.getData(),
                    inicializaCategoria(categoria)));

            return ResponseEntity.status(HttpStatus.CREATED)
                                 .body(DespesaResponse.from(output.getDespesa()));

        } catch (BussinessRuleException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    private Categoria inicializaCategoria(String categoria) {

        return categoria == null ? Categoria.Outras : Categoria.valueOf(categoria);
    }

}
