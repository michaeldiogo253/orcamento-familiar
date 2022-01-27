package alura.orcamentofamiliar.despesa.adapter.web.in;

import alura.orcamentofamiliar.despesa.adapter.web.in.response.DespesaResponse;
import alura.orcamentofamiliar.despesa.application.port.out.FindDespesaByIdPort;
import alura.orcamentofamiliar.despesa.domain.Despesa;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orcamento-familiar")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ListarDespesaPorIdController {

    private final FindDespesaByIdPort findDespesaByIdPort;

    @GetMapping("/despesas/{idDespesa}")
    public ResponseEntity<DespesaResponse> execute(@PathVariable Long idDespesa) {

        Despesa despesa = findDespesaByIdPort.findDespesaPorId(idDespesa);

        return ResponseEntity.ok()
                             .body(DespesaResponse.from(despesa));
    }
}
