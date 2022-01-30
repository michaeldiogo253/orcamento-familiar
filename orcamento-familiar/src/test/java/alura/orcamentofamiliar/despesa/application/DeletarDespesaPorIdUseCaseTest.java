package alura.orcamentofamiliar.despesa.application;

import alura.orcamentofamiliar.databuilder.DespesaCreator;
import alura.orcamentofamiliar.despesa.application.port.out.DeletarDespesaPorIdPort;
import alura.orcamentofamiliar.despesa.application.port.out.FindDespesaByIdPort;
import alura.orcamentofamiliar.despesa.domain.Categoria;
import alura.orcamentofamiliar.despesa.domain.Despesa;
import alura.orcamentofamiliar.util.exceptions.ResourceNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.BDDMockito.*;

class DeletarDespesaPorIdUseCaseTest {

    private final FindDespesaByIdPort findDespesaByIdPort = Mockito.mock(FindDespesaByIdPort.class);
    private final DeletarDespesaPorIdPort deletarDespesaPorIdPort = Mockito.mock(DeletarDespesaPorIdPort.class);
    private final DeletarDespesaPorIdUseCase useCase = new DeletarDespesaPorIdUseCase(findDespesaByIdPort,
                                                                                      deletarDespesaPorIdPort);

    @Test
    void deveDeletarDespesaComIdValido() {

        Long idDespesa = 1L;
        Despesa despesa = DespesaCreator.umaDespesa(idDespesa,
                                                    "Aluguel",
                                                    new BigDecimal("800"),
                                                    LocalDate.of(2022, 1, 22),
                                                    Categoria.Moradia);

        given(findDespesaByIdPort.findDespesaPorId(idDespesa)).willReturn(despesa);

        useCase.execute(new DeletarDespesaPorIdUseCase.InputValues(idDespesa));

        then(findDespesaByIdPort).should()
                                 .findDespesaPorId(idDespesa);
        then(deletarDespesaPorIdPort).should()
                                     .deletarDespesaPorId(idDespesa);

    }

    @Test
    void deveLancarExcecaoAoTentarDeletarDespesaComIdInvalido() {

        Long idDespesa = 1000L;
        String mensagem = "Despesa não encontrada";
        willThrow(new ResourceNotFoundException(mensagem)).given(findDespesaByIdPort)
                                                          .findDespesaPorId(idDespesa);
        Assertions.assertThatThrownBy(() -> findDespesaByIdPort.findDespesaPorId(idDespesa))
                  .isInstanceOf(ResourceNotFoundException.class)
                  .hasMessageContaining(mensagem);

        then(findDespesaByIdPort).should()
                                 .findDespesaPorId(idDespesa);
        then(deletarDespesaPorIdPort).shouldHaveNoInteractions();
    }

}