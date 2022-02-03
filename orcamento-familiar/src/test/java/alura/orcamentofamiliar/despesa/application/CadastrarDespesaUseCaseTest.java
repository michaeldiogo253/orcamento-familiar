package alura.orcamentofamiliar.despesa.application;

import alura.orcamentofamiliar.despesa.application.port.out.ExisteDespesaNoPeriodoPort;
import alura.orcamentofamiliar.despesa.application.port.out.SalvarDespesaPort;
import alura.orcamentofamiliar.despesa.domain.Categoria;
import alura.orcamentofamiliar.usuario.application.port.out.FindUsuarioByIdPort;
import alura.orcamentofamiliar.usuario.domain.Usuario;
import alura.orcamentofamiliar.util.date.DateUtil;
import alura.orcamentofamiliar.util.exceptions.BussinessRuleException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

class CadastrarDespesaUseCaseTest {

    private final ExisteDespesaNoPeriodoPort existeDespesaNoPeriodoPort =
            Mockito.mock(ExisteDespesaNoPeriodoPort.class);
    private final SalvarDespesaPort salvarDespesaPort = Mockito.mock(SalvarDespesaPort.class);
    private final FindUsuarioByIdPort findUsuarioByIdPort = Mockito.mock(FindUsuarioByIdPort.class);
    private final CadastrarDespesaUseCase useCase = new CadastrarDespesaUseCase(existeDespesaNoPeriodoPort,
                                                                                salvarDespesaPort,
                                                                                findUsuarioByIdPort);

    @Test
    void deveCadastrarDespesaComCategoriaPassadaNaRequest() {

        Usuario usuario = new Usuario("Michael", "mic", "mic");

        CadastrarDespesaUseCase.InputValues input = new CadastrarDespesaUseCase.InputValues(1L,
                                                                                            "Aluguel",
                                                                                            new BigDecimal("600.00"),
                                                                                            LocalDate.of(2022, 1, 23),
                                                                                            Categoria.Moradia);
        List<LocalDate> periodos = DateUtil.periodos(input.getData());
        given(existeDespesaNoPeriodoPort.existeNoPeriodo(input.getDescricao(), periodos)).willReturn(false);
        given(findUsuarioByIdPort.findUsuarioById(1L)).willReturn(usuario);

        CadastrarDespesaUseCase.OutputValues output = useCase.execute(input);

        then(existeDespesaNoPeriodoPort).should()
                                        .existeNoPeriodo(input.getDescricao(), periodos);
        then(salvarDespesaPort).should()
                               .salvarDespesa(any());

        then(findUsuarioByIdPort).should()
                                 .findUsuarioById(1L);

        assertThat(output.getDespesa()
                         .getDescricao()).isEqualTo(input.getDescricao());
        assertThat(output.getDespesa()
                         .getValor()).isEqualTo(input.getValor());
        assertThat(output.getDespesa()
                         .getData()).isEqualTo(input.getData());
        assertThat(output.getDespesa()
                         .getCategoria()).isEqualTo(input.getCategoria());
    }

    @Test
    void deveLancarExceptionAoTentarCadastrarDespesaInvalida() {

        CadastrarDespesaUseCase.InputValues input = new CadastrarDespesaUseCase.InputValues(1L,
                                                                                            "Aluguel",
                                                                                            new BigDecimal("600.00"),
                                                                                            LocalDate.of(2022, 1, 23),
                                                                                            Categoria.Moradia);

        List<LocalDate> periodos = DateUtil.periodos(input.getData());
        String menssagem = "Receita já cadastrada neste mes";

        willThrow(new BussinessRuleException(menssagem)).given(existeDespesaNoPeriodoPort)
                                                        .existeNoPeriodo(input.getDescricao(), periodos);

        assertThatThrownBy(() -> existeDespesaNoPeriodoPort.existeNoPeriodo(input.getDescricao(),
                                                                            periodos)).hasMessageContaining(menssagem)
                                                                                      .isInstanceOf(
                                                                                              BussinessRuleException.class);

        then(existeDespesaNoPeriodoPort).should()
                                        .existeNoPeriodo(input.getDescricao(), periodos);
        then(findUsuarioByIdPort).shouldHaveNoInteractions();
        then(salvarDespesaPort).shouldHaveNoInteractions();

    }
}