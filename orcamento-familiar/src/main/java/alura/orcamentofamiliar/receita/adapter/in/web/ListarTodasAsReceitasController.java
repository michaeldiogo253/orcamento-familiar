package alura.orcamentofamiliar.receita.adapter.in.web;

import alura.orcamentofamiliar.receita.adapter.in.web.response.ReceitaResponse;
import alura.orcamentofamiliar.receita.application.port.out.FindAllReceitasPort;
import alura.orcamentofamiliar.receita.application.port.out.FindTodasAsReceitasPelaDescricaoPort;
import alura.orcamentofamiliar.receita.domain.Receita;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orcamento-familiar")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ListarTodasAsReceitasController {

    private final FindTodasAsReceitasPelaDescricaoPort findTodasAsReceitasPelaDescricaoPort;
    private final FindAllReceitasPort findAllReceitasPort;

    @GetMapping("/receitas/listar-todas")
    public ResponseEntity<Page<ReceitaResponse>> execute(@RequestParam(required = false) String descricao,
                                                         Pageable paginacao) {

        Page<Receita> receitas = descricao == null ? findAllReceitasPort.findAllReceitas(paginacao)
                                                   :
                                 findTodasAsReceitasPelaDescricaoPort.listaTodasAsReceitasPorDescricao(
                                                           descricao,
                                                           paginacao);

        return ResponseEntity.ok()
                             .body(ReceitaResponse.fromPage(receitas));

    }

}
