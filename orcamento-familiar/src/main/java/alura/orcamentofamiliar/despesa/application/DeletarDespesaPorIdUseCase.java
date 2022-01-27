package alura.orcamentofamiliar.despesa.application;

import alura.orcamentofamiliar.despesa.application.port.out.DeletarDespesaPorIdPort;
import alura.orcamentofamiliar.despesa.application.port.out.FindDespesaByIdPort;
import alura.orcamentofamiliar.util.UseCase;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeletarDespesaPorIdUseCase
        extends UseCase<DeletarDespesaPorIdUseCase.InputValues, DeletarDespesaPorIdUseCase.OutputValues> {

    private final FindDespesaByIdPort findDespesaByIdPort;
    private final DeletarDespesaPorIdPort deletarDespesaPorIdPort;

    @Override
    public OutputValues execute(InputValues input) {

        findDespesaByIdPort.findDespesaPorId(input.getIdDespesa());
        deletarDespesaPorIdPort.deletarDespesaPorId(input.getIdDespesa());

        return DeletarDespesaPorIdUseCase.OutputValues.ofEmpty();
    }

    @Value
    public static class InputValues implements UseCase.InputValues {

        Long idDespesa;
    }

    @NoArgsConstructor(staticName = "ofEmpty")
    public static class OutputValues implements UseCase.OutputValues {

    }

}