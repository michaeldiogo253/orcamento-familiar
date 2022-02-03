package alura.orcamentofamiliar.receita.application.service;

import alura.orcamentofamiliar.receita.application.port.out.ExisteReceitaNoPeriodoPort;
import alura.orcamentofamiliar.receita.application.port.out.SalvarReceitaPort;
import alura.orcamentofamiliar.receita.domain.Receita;
import alura.orcamentofamiliar.usuario.application.port.out.FindUsuarioByIdPort;
import alura.orcamentofamiliar.usuario.domain.Usuario;
import alura.orcamentofamiliar.util.UseCase;
import alura.orcamentofamiliar.util.date.DateUtil;
import alura.orcamentofamiliar.util.exceptions.BussinessRuleException;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CadastrarReceitaUseCase
        extends UseCase<CadastrarReceitaUseCase.InputValues, CadastrarReceitaUseCase.OutputValues> {

    private final SalvarReceitaPort salvarReceitaPort;
    private final ExisteReceitaNoPeriodoPort existeReceitaNoPeriodoPort;
    private final FindUsuarioByIdPort findUsuarioByIdPort;

    @Override
    public OutputValues execute(InputValues input) {

        validaSePodeCadastrar(input);
        Usuario usuario = findUsuarioByIdPort.findUsuarioById(input.getIdUsuario());

        Receita receitaParaSalvar = new Receita(input.descricao, input.valor, input.data, usuario);
        salvarReceitaPort.salvarReceita(receitaParaSalvar);
        return new OutputValues(receitaParaSalvar);
    }

    private void validaSePodeCadastrar(InputValues input) {

        List<LocalDate> periodos = DateUtil.periodos(input.data);

        if (existeReceitaNoPeriodoPort.existeNoPeriodo(input.descricao, periodos)) {
            throw new BussinessRuleException("Receita já está cadastrada neste mes");
        }
    }

    @Value
    public static class InputValues implements UseCase.InputValues {

        @NotNull(message = "ID Usuario não pode ser nulo") Long idUsuario;
        @NotEmpty(message = "Descricao nao pode ser nula") String descricao;
        @NotNull(message = "Valor não pode ser nulo") BigDecimal valor;
        @NotNull(message = "Data não pode ser nula") LocalDate data;
    }

    @Value
    public static class OutputValues implements UseCase.OutputValues {

        Receita receita;

        public static OutputValues of(Receita receita) {

            return new OutputValues(receita);
        }
    }

}