package alura.orcamentofamiliar.despesa.adapter.web.in;

import alura.orcamentofamiliar.despesa.adapter.web.in.response.DespesaResponse;
import alura.orcamentofamiliar.despesa.application.port.out.ListarDespesasPorMesPort;
import alura.orcamentofamiliar.despesa.domain.Despesa;
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
public class ListarDespesasPorMesController {

    private final ListarDespesasPorMesPort listarDespesasPorMesPort;

    @GetMapping("/listar-despesas/{ano}/{mes}")
    public ResponseEntity<List<DespesaResponse>> execute(@PathVariable Integer ano, @PathVariable Integer mes) {

        List<Despesa> despesas = listarDespesasPorMesPort.listarDespesasPorMes(ano, mes);

        return ResponseEntity.ok()
                             .body(DespesaResponse.from(despesas));
    }
}
