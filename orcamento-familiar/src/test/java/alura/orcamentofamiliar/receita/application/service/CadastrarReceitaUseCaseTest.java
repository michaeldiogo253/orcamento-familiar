package alura.orcamentofamiliar.receita.application.service;

import alura.orcamentofamiliar.receita.application.port.out.ExisteReceitaNoPeriodoPort;
import alura.orcamentofamiliar.receita.application.port.out.SalvarReceitaPort;
import alura.orcamentofamiliar.usuario.application.port.out.FindUsuarioByIdPort;
import alura.orcamentofamiliar.usuario.domain.Usuario;
import alura.orcamentofamiliar.util.date.DateUtil;
import alura.orcamentofamiliar.util.exceptions.BussinessRuleException;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

@RequiredArgsConstructor
class CadastrarReceitaUseCaseTest {

    private final SalvarReceitaPort salvarReceitaPort = Mockito.mock(SalvarReceitaPort.class);
    private final ExisteReceitaNoPeriodoPort existeReceitaNoPeriodoPort =
            Mockito.mock(ExisteReceitaNoPeriodoPort.class);
    private final FindUsuarioByIdPort findUsuarioByIdPort = Mockito.mock(FindUsuarioByIdPort.class);

    CadastrarReceitaUseCase useCase = new CadastrarReceitaUseCase(salvarReceitaPort,
                                                                  existeReceitaNoPeriodoPort,
                                                                  findUsuarioByIdPort);

    @Test
    void deveCadastrarReceitaValida() {

        Usuario usuario = new Usuario("Michael", "michael", "michael");

        CadastrarReceitaUseCase.InputValues input = new CadastrarReceitaUseCase.InputValues(1L,
                                                                                            "Salario",
                                                                                            new BigDecimal("2000.00"),
                                                                                            LocalDate.of(2022, 1, 23));

        List<LocalDate> periodos = DateUtil.periodos(input.getData());
        given(existeReceitaNoPeriodoPort.existeNoPeriodo(input.getDescricao(), periodos)).willReturn(false);
        given(findUsuarioByIdPort.findUsuarioById(1L)).willReturn(usuario);

        CadastrarReceitaUseCase.OutputValues output = useCase.execute(input);

        then(existeReceitaNoPeriodoPort).should()
                                        .existeNoPeriodo(input.getDescricao(), periodos);
        then(salvarReceitaPort).should()
                               .salvarReceita(any());
        then(findUsuarioByIdPort).should()
                                 .findUsuarioById(1L);

        assertThat(output.getReceita()
                         .getDescricao()).isEqualTo(input.getDescricao());
        assertThat(output.getReceita()
                         .getValor()).isEqualTo(input.getValor());
        assertThat(output.getReceita()
                         .getData()).isEqualTo(input.getData());
    }

    @Test
    void deveLancarExceptionAoTentarCadastrarReceitaInvalida() {

        Usuario usuario = new Usuario("Michael", "michael", "michael");

        CadastrarReceitaUseCase.InputValues input = new CadastrarReceitaUseCase.InputValues(1L,
                                                                                            "Salario",
                                                                                            new BigDecimal("2000.00"),
                                                                                            LocalDate.of(2022, 1, 23));

        List<LocalDate> periodos = DateUtil.periodos(input.getData());
        String menssagem = "Receita já cadastrada neste mes";

        given(findUsuarioByIdPort.findUsuarioById(1L)).willReturn(usuario);

        willThrow(new BussinessRuleException(menssagem)).given(existeReceitaNoPeriodoPort)
                                                        .existeNoPeriodo(input.getDescricao(), periodos);

        assertThatThrownBy(() -> existeReceitaNoPeriodoPort.existeNoPeriodo(input.getDescricao(),
                                                                            periodos)).hasMessageContaining(menssagem)
                                                                                      .isInstanceOf(
                                                                                              BussinessRuleException.class);
        then(findUsuarioByIdPort).shouldHaveNoInteractions();

        then(existeReceitaNoPeriodoPort).should()
                                        .existeNoPeriodo(input.getDescricao(), periodos);
        then(salvarReceitaPort).shouldHaveNoInteractions();

    }
}