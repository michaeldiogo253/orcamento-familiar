package alura.orcamentofamiliar.resumo.adapter.in.web;

import alura.orcamentofamiliar.resumo.application.ResumoDoMesUseCase;
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
public class ResumoDoMesController {

    private final ResumoDoMesUseCase resumoDoMesUseCase;

    @GetMapping("/resumo/{ano}/{mes}")
    public ResponseEntity<ResumoDoMesUseCase.OutputValues> execute(@PathVariable Integer ano,
                                                                   @PathVariable Integer mes) {

        ResumoDoMesUseCase.OutputValues output = resumoDoMesUseCase.execute(new ResumoDoMesUseCase.InputValues(ano,
                                                                                                               mes));
        return ResponseEntity.ok()
                             .body(output);
    }

}
