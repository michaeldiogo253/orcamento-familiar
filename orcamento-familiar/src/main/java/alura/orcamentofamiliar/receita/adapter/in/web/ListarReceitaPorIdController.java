package alura.orcamentofamiliar.receita.adapter.in.web;

import alura.orcamentofamiliar.receita.adapter.in.web.response.ReceitaResponse;
import alura.orcamentofamiliar.receita.application.port.out.FindReceitaByIdPort;
import alura.orcamentofamiliar.receita.domain.Receita;
import alura.orcamentofamiliar.util.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/orcamento-familiar")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ListarReceitaPorIdController {

    private final FindReceitaByIdPort findReceitaByIdPort;

    @GetMapping("/receitas/{idReceita}")
    public ResponseEntity<ReceitaResponse> execute(@PathVariable Long idReceita) {

        try {

            Receita receita = findReceitaByIdPort.findReceitaById(idReceita);
            return ResponseEntity.ok()
                                 .body(ReceitaResponse.from(receita));

        } catch (ResourceNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
