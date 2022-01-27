package alura.orcamentofamiliar.despesa.application;

import alura.orcamentofamiliar.despesa.application.port.out.FindDespesaByIdPort;
import alura.orcamentofamiliar.despesa.application.port.out.SalvarDespesaPort;
import alura.orcamentofamiliar.despesa.domain.Despesa;
import alura.orcamentofamiliar.util.UseCase;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class AtualizarDespesaUseCase
        extends UseCase<AtualizarDespesaUseCase.InputValues, AtualizarDespesaUseCase.OutputValues> {

    private final FindDespesaByIdPort findDespesaByIdPort;
    private final SalvarDespesaPort salvarDespesaPort;

    @Override
    @Transactional
    public OutputValues execute(InputValues input) {

        Despesa despesa = findDespesaByIdPort.findDespesaPorId(input.getIdDespesa());

        despesa.atualizaDadosDespesa(input.getDescricao(), input.getValor(), input.getData());
        salvarDespesaPort.salvarDespesa(despesa);

        return OutputValues.ofEmpty();
    }

    @Value
    public static class InputValues implements UseCase.InputValues {

        Long idDespesa;
        String descricao;
        BigDecimal valor;
        LocalDate data;

    }

    @Value
    @RequiredArgsConstructor(staticName = "ofEmpty")
    public static class OutputValues implements UseCase.OutputValues {

    }

}