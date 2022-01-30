package alura.orcamentofamiliar.receita.application.service;

import alura.orcamentofamiliar.databuilder.ReceitaCreator;
import alura.orcamentofamiliar.receita.application.port.out.ExisteReceitaNoPeriodoPort;
import alura.orcamentofamiliar.receita.application.port.out.FindReceitaByIdPort;
import alura.orcamentofamiliar.receita.application.port.out.SalvarReceitaPort;
import alura.orcamentofamiliar.receita.domain.Receita;
import alura.orcamentofamiliar.util.date.DateUtil;
import alura.orcamentofamiliar.util.exceptions.BussinessRuleException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.*;

class AtualizarReceitaUseCaseTest {

    private FindReceitaByIdPort findReceitaByIdPort = Mockito.mock(FindReceitaByIdPort.class);
    private SalvarReceitaPort salvarReceitaPort = Mockito.mock(SalvarReceitaPort.class);
    private ExisteReceitaNoPeriodoPort existeReceitaNoPeriodoPort = Mockito.mock(ExisteReceitaNoPeriodoPort.class);

    AtualizarReceitaUseCase useCase = new AtualizarReceitaUseCase(findReceitaByIdPort,
                                                                  salvarReceitaPort,
                                                                  existeReceitaNoPeriodoPort);

    @Test
    void deveVerificarSeReceitaFoiAtualizada() {

        AtualizarReceitaUseCase.InputValues input = new AtualizarReceitaUseCase.InputValues(1L,
                                                                                            "Internet",
                                                                                            new BigDecimal("200.00"),
                                                                                            LocalDate.of(2022, 1, 20));
        List<LocalDate> periodos = DateUtil.periodos(input.getData());

        Receita receitaBuscada = ReceitaCreator.umaReceita(input.getIdReceita(),
                                                           "Salario",
                                                           new BigDecimal("1200"),
                                                           LocalDate.of(2022, 1, 22));

        given(existeReceitaNoPeriodoPort.existeNoPeriodo(input.getDescricao(), periodos)).willReturn(false);
        given(findReceitaByIdPort.findReceitaById(input.getIdReceita())).willReturn(receitaBuscada);

        AtualizarReceitaUseCase.OutputValues output = useCase.execute(input);

        then(existeReceitaNoPeriodoPort).should()
                                        .existeNoPeriodo(input.getDescricao(), periodos);
        then(findReceitaByIdPort).should()
                                 .findReceitaById(input.getIdReceita());
        then(salvarReceitaPort).should()
                               .salvarReceita(receitaBuscada);

        assertThat(output.getReceitaAtualizada()
                         .getValor()).isEqualTo(input.getValor());
        assertThat(output.getReceitaAtualizada()
                         .getData()).isEqualTo(input.getData());
        assertThat(output.getReceitaAtualizada()
                         .getDescricao()).isEqualTo(input.getDescricao());
        assertThat(output.getReceitaAtualizada()
                         .getId()).isEqualTo(input.getIdReceita());

    }

    @Test
    void deveLancaExcecaoQuandoReceitaJaPossuiDescricaoNoMesmoMes() {

        AtualizarReceitaUseCase.InputValues input = new AtualizarReceitaUseCase.InputValues(1L,
                                                                                            "Internet",
                                                                                            new BigDecimal("200.00"),
                                                                                            LocalDate.of(2022, 1, 20));

        List<LocalDate> periodos = DateUtil.periodos(input.getData());
        willThrow(new BussinessRuleException("Receita já está cadastrada neste mes")).given(existeReceitaNoPeriodoPort)
                                                                                     .existeNoPeriodo(any(), any());

        assertThatThrownBy(() -> existeReceitaNoPeriodoPort.existeNoPeriodo(input.getDescricao(),
                                                                            periodos)).isInstanceOf(
                                                                                              BussinessRuleException.class)
                                                                                      .hasMessageContaining(
                                                                                              "Receita já está " +
                                                                                              "cadastrada neste mes");
        then(existeReceitaNoPeriodoPort).should()
                                        .existeNoPeriodo(input.getDescricao(), periodos);
        then(findReceitaByIdPort).shouldHaveNoInteractions();
        then(salvarReceitaPort).shouldHaveNoInteractions();

    }

}