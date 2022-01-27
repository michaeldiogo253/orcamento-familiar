package alura.orcamentofamiliar.despesa.adapter.web.in;

import alura.orcamentofamiliar.despesa.adapter.web.in.response.DespesaResponse;
import alura.orcamentofamiliar.despesa.application.port.out.ListarTodasAsDespesasPorDescricaoPort;
import alura.orcamentofamiliar.despesa.application.port.out.ListarTodasAsDespesasPort;
import alura.orcamentofamiliar.despesa.domain.Despesa;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orcamento-familiar")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ListarTodasAsDespesasController {

    private final ListarTodasAsDespesasPorDescricaoPort listarTodasAsDespesasPorDescricaoPort;
    private final ListarTodasAsDespesasPort listarTodasAsDespesasPort;

    @GetMapping("/despesas/listar-todas")
    public ResponseEntity<List<DespesaResponse>> execute(@RequestParam(required = false) String descricao) {

        List<Despesa> despesas = descricao == null ? listarTodasAsDespesasPort.listarTodasAsDespesas()
                                                   : listarTodasAsDespesasPorDescricaoPort.listarDespesasPorDescricao(
                                                           descricao);

        return ResponseEntity.ok()
                             .body(DespesaResponse.from(despesas));
    }
}
