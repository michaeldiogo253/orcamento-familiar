package alura.orcamentofamiliar.receita.application.service;

import alura.orcamentofamiliar.databuilder.ReceitaCreator;
import alura.orcamentofamiliar.receita.application.port.out.DeletarReceitaByIdPort;
import alura.orcamentofamiliar.receita.application.port.out.FindReceitaByIdPort;
import alura.orcamentofamiliar.receita.domain.Receita;
import alura.orcamentofamiliar.util.exceptions.BussinessRuleException;
import alura.orcamentofamiliar.util.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.*;

class DeletarReceitaPorIdUseCaseTest {

    private FindReceitaByIdPort findReceitaByIdPort = Mockito.mock(FindReceitaByIdPort.class);
    private DeletarReceitaByIdPort deletarReceitaByIdPort = Mockito.mock(DeletarReceitaByIdPort.class);
    private DeletarReceitaPorIdUseCase useCase = new DeletarReceitaPorIdUseCase(findReceitaByIdPort,
                                                                                deletarReceitaByIdPort);

    @Test
    void deveriaExcluirReceitaComIdValido() {

        Long id = 1L;
        Receita receita = ReceitaCreator.umaReceita(1L, "Internet", BigDecimal.TEN, LocalDate.now());
        given(findReceitaByIdPort.findReceitaById(id)).willReturn(receita);

        useCase.execute(new DeletarReceitaPorIdUseCase.InputValues(id));

        then(findReceitaByIdPort).should()
                                 .findReceitaById(id);
        then(deletarReceitaByIdPort).should()
                                    .deletarReceitaPorId(1L);

    }

    @Test
    void naoDeveriaExcluirReceitaComIdInvalido() {

        String errorMessage = String.format("Receita não encontrada");
        Long idReceita = 100L;

        willThrow(new ResourceNotFoundException(errorMessage)).given(findReceitaByIdPort)
                                                              .findReceitaById(idReceita);

        assertThatThrownBy(() -> findReceitaByIdPort.findReceitaById(idReceita)).isInstanceOf(ResourceNotFoundException.class)
                                                        .hasMessageContaining(errorMessage);

        then(findReceitaByIdPort).should().findReceitaById(idReceita);
        then(deletarReceitaByIdPort).shouldHaveNoInteractions();
    }

}