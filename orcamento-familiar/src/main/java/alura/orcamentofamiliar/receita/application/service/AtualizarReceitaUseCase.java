package alura.orcamentofamiliar.receita.application.service;

import alura.orcamentofamiliar.receita.application.port.out.ExisteReceitaNoPeriodoPort;
import alura.orcamentofamiliar.receita.application.port.out.FindReceitaByIdPort;
import alura.orcamentofamiliar.receita.application.port.out.SalvarReceitaPort;
import alura.orcamentofamiliar.receita.domain.Receita;
import alura.orcamentofamiliar.util.UseCase;
import alura.orcamentofamiliar.util.date.DateUtil;
import alura.orcamentofamiliar.util.exceptions.BussinessRuleException;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AtualizarReceitaUseCase
        extends UseCase<AtualizarReceitaUseCase.InputValues, AtualizarReceitaUseCase.OutputValues> {

    private final FindReceitaByIdPort findReceitaByIdPort;
    private final SalvarReceitaPort salvarReceitaPort;
    private final ExisteReceitaNoPeriodoPort existeReceitaNoPeriodoPort;

    @Override
    public OutputValues execute(InputValues input) {


        validaSePodeAtualizar(input);

        Receita receitaASerAtualizada = findReceitaByIdPort.findReceitaById(input.getIdReceita());


        receitaASerAtualizada.atualizaDadosReceita(input.getDescricao(), input.getValor(), input.getData());
        salvarReceitaPort.salvarReceita(receitaASerAtualizada);

        return OutputValues.ofEmpty();
    }

    private void validaSePodeAtualizar(AtualizarReceitaUseCase.InputValues input) {

        List<LocalDate> periodos = DateUtil.periodos(input.getData());

        if (existeReceitaNoPeriodoPort.existeNoPeriodo(input.getDescricao(), periodos)) {
            throw new BussinessRuleException("Receita já está cadastrada neste mes");
        }
    }

    @Value
    public static class InputValues implements UseCase.InputValues {

        Long IdReceita;
        @NotNull(message = "Descrição não pode ser nula") String descricao;
        @NotNull(message = "Valor não pode ser nulo") BigDecimal valor;
        @NotNull(message = "Data não pode ser nula") LocalDate data;
    }

    @Value
    @RequiredArgsConstructor(staticName = "ofEmpty")
    public static class OutputValues implements UseCase.OutputValues {

    }

}