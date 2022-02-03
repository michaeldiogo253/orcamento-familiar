package alura.orcamentofamiliar.despesa.application;

import alura.orcamentofamiliar.despesa.application.port.out.ExisteDespesaNoPeriodoPort;
import alura.orcamentofamiliar.despesa.application.port.out.SalvarDespesaPort;
import alura.orcamentofamiliar.despesa.domain.Categoria;
import alura.orcamentofamiliar.despesa.domain.Despesa;
import alura.orcamentofamiliar.usuario.application.port.out.FindUsuarioByIdPort;
import alura.orcamentofamiliar.usuario.domain.Usuario;
import alura.orcamentofamiliar.util.UseCase;
import alura.orcamentofamiliar.util.date.DateUtil;
import alura.orcamentofamiliar.util.exceptions.BussinessRuleException;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CadastrarDespesaUseCase
        extends UseCase<CadastrarDespesaUseCase.InputValues, CadastrarDespesaUseCase.OutputValues> {

    private final ExisteDespesaNoPeriodoPort existeDespesaNoPeriodoPort;
    private final SalvarDespesaPort salvarDespesaPort;
    private final FindUsuarioByIdPort findUsuarioByIdPort;

    @Override
    public OutputValues execute(InputValues input) {

        validaSePodeCadastrar(input);
        Usuario usuario = findUsuarioByIdPort.findUsuarioById(input.getIdUsuario());

        Despesa despesa = new Despesa(input.getDescricao(), input.getValor(), input.getData(), input.getCategoria(), usuario);

        salvarDespesaPort.salvarDespesa(despesa);

        return OutputValues.of(despesa);
    }

    private void validaSePodeCadastrar(CadastrarDespesaUseCase.InputValues input) {

        List<LocalDate> periodos = DateUtil.periodos(input.data);

        if (existeDespesaNoPeriodoPort.existeNoPeriodo(input.descricao, periodos)) {
            throw new BussinessRuleException("Receita já está cadastrada neste mes");
        }
    }

    @Value
    public static class InputValues implements UseCase.InputValues {

        Long idUsuario;
        String descricao;
        BigDecimal valor;
        LocalDate data;
        Categoria categoria;
    }

    @Value
    public static class OutputValues implements UseCase.OutputValues {

        Despesa despesa;

        public static OutputValues of(Despesa despesa) {

            return new OutputValues(despesa);
        }
    }

}