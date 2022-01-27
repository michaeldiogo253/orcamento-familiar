package alura.orcamentofamiliar.receita.adapter.in.web;

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
public class DeletarReceitaPorIdController {

    private final DeletarReceitaPorIdUseCase useCase;

    @DeleteMapping("/receitas/deletar-receita/{idReceita}")
    public ResponseEntity<Void> execute(@PathVariable Long idReceita){
        useCase.execute(new DeletarReceitaPorIdUseCase.InputValues(idReceita));
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
