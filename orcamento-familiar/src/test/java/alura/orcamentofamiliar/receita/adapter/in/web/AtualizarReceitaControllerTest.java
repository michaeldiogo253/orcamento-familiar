package alura.orcamentofamiliar.receita.adapter.in.web;

import alura.orcamentofamiliar.receita.adapter.in.web.request.AtualizarReceitaRequest;
import alura.orcamentofamiliar.receita.application.service.AtualizarReceitaUseCase;
import alura.orcamentofamiliar.util.exceptions.BussinessRuleException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AtualizarReceitaController.class)
@AutoConfigureMockMvc(addFilters = false)
class AtualizarReceitaControllerTest {

    @MockBean AtualizarReceitaUseCase useCase;

    @Autowired ObjectMapper mapper;
    @Autowired MockMvc mockMvc;

    @Test
    void deveriaAtualizarUmaReceita() throws Exception {

        Long idReceita = 1L;
        String url = "/orcamento-familiar/receitas/atualizar-receita/{idReceita}";

        AtualizarReceitaRequest request = new AtualizarReceitaRequest("Salario",
                                                                      new BigDecimal("2000.00"),
                                                                      LocalDate.now());

        AtualizarReceitaUseCase.InputValues input = new AtualizarReceitaUseCase.InputValues(idReceita,
                                                                                            "Salario",
                                                                                            new BigDecimal("2000.00"),
                                                                                            LocalDate.now());

        MvcResult result = mockMvc.perform(put(url, 1L).header("Content-Type", "application/json")
                                                       .accept("application/json")
                                                       .content(mapper.writeValueAsString(request)))
                                  .andExpect(status().isOk())
                                  .andReturn();
        then(useCase).should()
                     .execute(input);

    }

    @Test
    void deveriaLancarUmaBussinessRuleExceptionAoTentarAtualizarUmaReceitaQueJaExiste() throws Exception {

        Long idReceita = 1L;
        String url = "/orcamento-familiar/receitas/atualizar-receita/{idReceita}";
        AtualizarReceitaUseCase.InputValues input = new AtualizarReceitaUseCase.InputValues(idReceita,
                                                                                            "Salario",
                                                                                            new BigDecimal("2000.00"),
                                                                                            LocalDate.now());

        String errorMessage = String.format("Receita já está cadastrada neste mes");

        willThrow(new BussinessRuleException(errorMessage)).given(useCase)
                                                           .execute(input);

        assertThatThrownBy(() -> useCase.execute(input)).isInstanceOf(BussinessRuleException.class)
                                                        .hasMessageContaining(errorMessage);
    }
}