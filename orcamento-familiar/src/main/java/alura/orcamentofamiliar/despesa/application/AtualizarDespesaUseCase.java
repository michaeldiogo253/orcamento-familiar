package alura.orcamentofamiliar.despesa.application;

import alura.orcamentofamiliar.despesa.application.port.out.ExisteDespesaNoPeriodoPort;
import alura.orcamentofamiliar.despesa.application.port.out.FindDespesaByIdPort;
import alura.orcamentofamiliar.despesa.application.port.out.SalvarDespesaPort;
import alura.orcamentofamiliar.despesa.domain.Despesa;
import alura.orcamentofamiliar.util.UseCase;
import alura.orcamentofamiliar.util.date.DateUtil;
import alura.orcamentofamiliar.util.exceptions.BussinessRuleException;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AtualizarDespesaUseCase
        extends UseCase<AtualizarDespesaUseCase.InputValues, AtualizarDespesaUseCase.OutputValues> {

    private final FindDespesaByIdPort findDespesaByIdPort;
    private final SalvarDespesaPort salvarDespesaPort;
    private final ExisteDespesaNoPeriodoPort existeDespesaNoPeriodoPort;

    @Override
    @Transactional
    public OutputValues execute(InputValues input) {

        validaSePodeAtualizar(input);

        Despesa despesa = findDespesaByIdPort.findDespesaPorId(input.getIdDespesa());

        despesa.atualizaDadosDespesa(input.getDescricao(), input.getValor(), input.getData());
        salvarDespesaPort.salvarDespesa(despesa);

        return  OutputValues.of(despesa);
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

        Despesa despesa;

        public static OutputValues of(Despesa despesa) {

            return new OutputValues(despesa);
        }
    }

    private void validaSePodeAtualizar(AtualizarDespesaUseCase.InputValues input) {

        List<LocalDate> periodos = DateUtil.periodos(input.getData());

        if (existeDespesaNoPeriodoPort.existeNoPeriodo(input.getDescricao(), periodos)) {
                throw new BussinessRuleException("Descrição da Despesa já está cadastrada neste mes, não é possivel atualiza-la");
        }
    }
}