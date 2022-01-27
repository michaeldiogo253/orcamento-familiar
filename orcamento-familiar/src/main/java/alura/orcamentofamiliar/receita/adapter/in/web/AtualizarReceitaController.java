package alura.orcamentofamiliar.receita.adapter.in.web;

import alura.orcamentofamiliar.receita.adapter.in.web.request.AtualizarReceitaRequest;
import alura.orcamentofamiliar.receita.application.service.AtualizarReceitaUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/orcamento-familiar")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AtualizarReceitaController {

    private final AtualizarReceitaUseCase atualizarReceitaUseCase;

    @PutMapping("/receitas/atualizar-receita/{idReceita}")
    public ResponseEntity<Void> execute(@PathVariable Long idReceita,
                                        @RequestBody @Valid AtualizarReceitaRequest request) {

        atualizarReceitaUseCase.execute(new AtualizarReceitaUseCase.InputValues(idReceita,
                                                                                request.getDescricao(),
                                                                                request.getValor(),
                                                                                request.getData()));
        return ResponseEntity.ok()
                             .build();
    }

}
