package alura.orcamentofamiliar.receita.application.service;

import alura.orcamentofamiliar.receita.application.port.out.DeletarReceitaByIdPort;
import alura.orcamentofamiliar.receita.application.port.out.FindReceitaByIdPort;
import alura.orcamentofamiliar.util.UseCase;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DeletarReceitaPorIdUseCase
        extends UseCase<DeletarReceitaPorIdUseCase.InputValues, DeletarReceitaPorIdUseCase.OutputValues> {

    private final FindReceitaByIdPort findReceitaByIdPort;
    private final DeletarReceitaByIdPort deletarReceitaByIdPort;

    @Override
    public OutputValues execute(InputValues input) {

        findReceitaByIdPort.findReceitaById(input.getIdReceita());
        deletarReceitaByIdPort.deletarReceitaPorId(input.getIdReceita());

        return OutputValues.ofEmpty();
    }

    @Value
    public static class InputValues implements UseCase.InputValues {

        Long idReceita;
    }

    @NoArgsConstructor(staticName = "ofEmpty")
    public static class OutputValues implements UseCase.OutputValues {

    }

}