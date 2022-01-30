package alura.orcamentofamiliar.receita.adapter.in.web;

import alura.orcamentofamiliar.receita.adapter.in.web.response.ReceitaResponse;
import alura.orcamentofamiliar.receita.application.port.out.FindTodasAsReceitasPelaDescricaoPort;
import alura.orcamentofamiliar.receita.application.port.out.FindAllReceitasPort;
import alura.orcamentofamiliar.receita.domain.Receita;
import alura.orcamentofamiliar.util.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/orcamento-familiar")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ListarTodasAsReceitasController {

    private final FindAllReceitasPort findAllReceitasPort;
    private final FindTodasAsReceitasPelaDescricaoPort findTodasAsReceitasPelaDescricaoPort;

    @GetMapping("/receitas/listar-todas")
    public ResponseEntity<List<ReceitaResponse>> execute(@RequestParam(required = false) String descricao) {

            List<Receita> receitas = descricao == null ? findAllReceitasPort.findAllReceitas()
                                                       :
                                     findTodasAsReceitasPelaDescricaoPort.listaTodasAsReceitasPorDescricao(
                                                               descricao);

            return ResponseEntity.ok()
                                 .body(ReceitaResponse.from(receitas));


    }

}
