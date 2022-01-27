package alura.orcamentofamiliar.despesa.adapter.web.in;

import alura.orcamentofamiliar.despesa.application.DeletarDespesaPorIdUseCase;
import alura.orcamentofamiliar.receita.application.service.DeletarReceitaPorIdUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
@RequestMapping("/orcamento-familiar")
public class DeletarDespesaController {

    private final DeletarDespesaPorIdUseCase useCase;

    @DeleteMapping("/despesas/deletar-despesa/{idDespesa}")
    public ResponseEntity<Void> execute(@PathVariable Long idDespesa){
        useCase.execute(new DeletarDespesaPorIdUseCase.InputValues(idDespesa));
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
