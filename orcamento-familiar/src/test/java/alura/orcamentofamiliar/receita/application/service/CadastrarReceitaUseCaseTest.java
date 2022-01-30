package alura.orcamentofamiliar.receita.application.service;

import alura.orcamentofamiliar.receita.application.port.out.ExisteReceitaNoPeriodoPort;
import alura.orcamentofamiliar.receita.application.port.out.SalvarReceitaPort;
import alura.orcamentofamiliar.receita.domain.Receita;
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
    private final ExisteReceitaNoPeriodoPort existeReceitaNoPeriodoPort = Mockito.mock(ExisteReceitaNoPeriodoPort.class);

    CadastrarReceitaUseCase useCase = new CadastrarReceitaUseCase(salvarReceitaPort, existeReceitaNoPeriodoPort);

    @Test
    void deveCadastrarReceitaValida() {

        CadastrarReceitaUseCase.InputValues input = new CadastrarReceitaUseCase.InputValues("Salario",
                                                                                            new BigDecimal("2000.00"),
                                                                                            LocalDate.of(2022, 1, 23));

        List<LocalDate> periodos = DateUtil.periodos(input.getData());
        given(existeReceitaNoPeriodoPort.existeNoPeriodo(input.getDescricao(), periodos)).willReturn(false);

        CadastrarReceitaUseCase.OutputValues output = useCase.execute(input);

        then(existeReceitaNoPeriodoPort).should()
                                        .existeNoPeriodo(input.getDescricao(), periodos);
        then(salvarReceitaPort).should()
                               .salvarReceita(any());

        assertThat(output.getReceita()
                         .getDescricao()).isEqualTo(input.getDescricao());
        assertThat(output.getReceita()
                         .getValor()).isEqualTo(input.getValor());
        assertThat(output.getReceita()
                         .getData()).isEqualTo(input.getData());
    }

    @Test
    void deveLancarExceptionAoTentarCadastrarReceitaInvalida(){

        CadastrarReceitaUseCase.InputValues input = new CadastrarReceitaUseCase.InputValues("Salario",
                                                                                            new BigDecimal("2000.00"),
                                                                                            LocalDate.of(2022, 1, 23));

        List<LocalDate> periodos = DateUtil.periodos(input.getData());
        String menssagem = "Receita já cadastrada neste mes";

        willThrow(new BussinessRuleException(menssagem))
                .given(existeReceitaNoPeriodoPort).existeNoPeriodo(input.getDescricao(), periodos);

        assertThatThrownBy(()-> existeReceitaNoPeriodoPort.existeNoPeriodo(input.getDescricao(), periodos))
                .hasMessageContaining(menssagem)
                .isInstanceOf(BussinessRuleException.class);

        then(existeReceitaNoPeriodoPort).should().existeNoPeriodo(input.getDescricao(),periodos);
        then(salvarReceitaPort).shouldHaveNoInteractions();

    }
}