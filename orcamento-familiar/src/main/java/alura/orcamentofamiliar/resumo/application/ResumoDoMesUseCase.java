package alura.orcamentofamiliar.resumo.application;

import alura.orcamentofamiliar.despesa.application.port.out.FindDespesasNomesPorCategoriaPort;
import alura.orcamentofamiliar.despesa.application.port.out.FindSomaDasDespesasNoMesPort;
import alura.orcamentofamiliar.receita.application.port.out.FindSomaDasReceitasNoMesPort;
import alura.orcamentofamiliar.resumo.adapter.in.web.response.DespesasPorCategoriaResponse;
import alura.orcamentofamiliar.util.UseCase;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ResumoDoMesUseCase extends UseCase<ResumoDoMesUseCase.InputValues, ResumoDoMesUseCase.OutputValues> {

    private final FindSomaDasDespesasNoMesPort findSomaDasDespesasNoMesPort;
    private final FindSomaDasReceitasNoMesPort findSomaDasReceitasNoMesPort;
    private final FindDespesasNomesPorCategoriaPort findDespesasNomesPorCategoriaPort;

    @Override
    public OutputValues execute(InputValues input) {

        BigDecimal somaDespesas = findSomaDasDespesasNoMesPort.somaDasDespesasNoMes(input.getAno(), input.getMes());
        BigDecimal somaReceitas = findSomaDasReceitasNoMesPort.somaDasReceitasNoMes(input.getAno(), input.getMes());
        BigDecimal saldoFinal = somaReceitas.subtract(somaDespesas);
        List<DespesasPorCategoriaResponse> despesasByCategoria =
                findDespesasNomesPorCategoriaPort.findDespesasByAnoAndMesGroupByCategoria(
                input.getAno(),
                input.getMes());

        return new OutputValues(somaDespesas, somaReceitas, saldoFinal, despesasByCategoria);
    }

    @Value
    public static class InputValues implements UseCase.InputValues {

        int ano;
        int mes;
    }

    @Value
    public static class OutputValues implements UseCase.OutputValues {

        BigDecimal valorDespesaMensal;
        BigDecimal valorReceitaMensal;
        BigDecimal saldoFinalDoMes;
        List<DespesasPorCategoriaResponse> despesasMensaisPorCategoria;

        private static ResumoDoMesUseCase.OutputValues buildOutput(BigDecimal valorDespesaMensal,
                                                                   BigDecimal valorReceitaMensal,
                                                                   BigDecimal saldoFinalDoMes,
                                                                   List<DespesasPorCategoriaResponse> despesasMensaisPorCategoria) {

            return new OutputValues(valorDespesaMensal,
                                    valorReceitaMensal,
                                    saldoFinalDoMes,
                                    despesasMensaisPorCategoria);
        }

    }

}