package alura.orcamentofamiliar.receita.adapter.in.web;

import alura.orcamentofamiliar.receita.adapter.in.web.response.ReceitaResponse;
import alura.orcamentofamiliar.receita.application.port.out.ListarReceitasPorMesPort;
import alura.orcamentofamiliar.receita.domain.Receita;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orcamento-familiar")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ListarReceitasPorMesController {

    private final ListarReceitasPorMesPort listarReceitasPorMesPort;

    @GetMapping("/listar-receitas/{ano}/{mes}")
    public ResponseEntity<List<ReceitaResponse>> execute(@PathVariable Integer ano, @PathVariable Integer mes) {

        List<Receita> receitas = listarReceitasPorMesPort.listarReceitasByYearAndMonth(ano, mes);

        return ResponseEntity.ok()
                             .body(ReceitaResponse.from(receitas));

    }

}
