package alura.orcamentofamiliar.despesa.application;

import alura.orcamentofamiliar.databuilder.DespesaCreator;
import alura.orcamentofamiliar.despesa.application.port.out.ExisteDespesaNoPeriodoPort;
import alura.orcamentofamiliar.despesa.application.port.out.FindDespesaByIdPort;
import alura.orcamentofamiliar.despesa.application.port.out.SalvarDespesaPort;
import alura.orcamentofamiliar.despesa.domain.Categoria;
import alura.orcamentofamiliar.despesa.domain.Despesa;
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

class AtualizarDespesaUseCaseTest {

    private final FindDespesaByIdPort findDespesaByIdPort = Mockito.mock(FindDespesaByIdPort.class);
    private final SalvarDespesaPort salvarDespesaPort = Mockito.mock(SalvarDespesaPort.class);
    private final ExisteDespesaNoPeriodoPort existeDespesaNoPeriodoPort =
            Mockito.mock(ExisteDespesaNoPeriodoPort.class);
    private final AtualizarDespesaUseCase useCase = new AtualizarDespesaUseCase(findDespesaByIdPort,
                                                                                salvarDespesaPort,
                                                                                existeDespesaNoPeriodoPort);

    @Test
    void deveVerificarSeDespesaFoiAtualizada() {

        AtualizarDespesaUseCase.InputValues input = new AtualizarDespesaUseCase.InputValues(1L,
                                                                                            "Internet",
                                                                                            new BigDecimal("200.00"),
                                                                                            LocalDate.of(2022, 1, 20));
        List<LocalDate> periodos = DateUtil.periodos(input.getData());

        Despesa despesaBuscada = DespesaCreator.umaDespesa(input.getIdDespesa(),
                                                           "Luz",
                                                           new BigDecimal("150.00"),
                                                           LocalDate.of(2022, 1, 22),
                                                           Categoria.Moradia);

        given(existeDespesaNoPeriodoPort.existeNoPeriodo(input.getDescricao(), periodos)).willReturn(false);
        given(findDespesaByIdPort.findDespesaPorId(input.getIdDespesa())).willReturn(despesaBuscada);

        AtualizarDespesaUseCase.OutputValues output = useCase.execute(input);

        then(findDespesaByIdPort).should()
                                 .findDespesaPorId(input.getIdDespesa());
        then(salvarDespesaPort).should()
                               .salvarDespesa(any());
        then(existeDespesaNoPeriodoPort).should()
                                        .existeNoPeriodo(input.getDescricao(), periodos);

        assertThat(output.getDespesa()
                         .getValor()).isEqualTo(input.getValor());
        assertThat(output.getDespesa()
                         .getData()).isEqualTo(input.getData());
        assertThat(output.getDespesa()
                         .getDescricao()).isEqualTo(input.getDescricao());
        assertThat(output.getDespesa()
                         .getId()).isEqualTo(input.getIdDespesa());
        assertThat(output.getDespesa()
                         .getCategoria()).isEqualTo(Categoria.Moradia);

    }

    @Test
    void deveLancarExceptionAoTentarCadastrarDespesaInvalida() {

        CadastrarDespesaUseCase.InputValues input = new CadastrarDespesaUseCase.InputValues(1L, "Aluguel",
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
        then(salvarDespesaPort).shouldHaveNoInteractions();

    }
}