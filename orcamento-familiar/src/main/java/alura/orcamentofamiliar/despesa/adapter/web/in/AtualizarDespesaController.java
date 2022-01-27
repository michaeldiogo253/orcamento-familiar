package alura.orcamentofamiliar.despesa.adapter.web.in;

import alura.orcamentofamiliar.despesa.adapter.web.in.request.AtualizarDespesaRequest;
import alura.orcamentofamiliar.despesa.application.AtualizarDespesaUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/orcamento-familiar")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AtualizarDespesaController {

    private final AtualizarDespesaUseCase atualizarDespesaUseCase;

    @PutMapping("despesa-atualizar/{idDespesa}")
    public ResponseEntity<Void> execute(@PathVariable Long idDespesa,
                                        @RequestBody @Valid AtualizarDespesaRequest request) {

        atualizarDespesaUseCase.execute(new AtualizarDespesaUseCase.InputValues(idDespesa,
                                                                                request.getDescricao(),
                                                                                request.getValor(),
                                                                                request.getData()));

        return ResponseEntity.ok()
                             .build();
    }

}
