package alura.orcamentofamiliar.despesa.adapter.web.in;

import alura.orcamentofamiliar.despesa.adapter.web.in.request.AtualizarDespesaRequest;
import alura.orcamentofamiliar.despesa.application.AtualizarDespesaUseCase;
import alura.orcamentofamiliar.util.exceptions.BussinessRuleException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AtualizarDespesaController.class)
@AutoConfigureMockMvc(addFilters = false)
class AtualizarDespesaControllerTest {

    @MockBean AtualizarDespesaUseCase useCase;

    @Autowired ObjectMapper mapper;
    @Autowired MockMvc mockMvc;

    @Test
    void deveAtualizarDespesa() throws Exception {

        Long idDespesa = 1L;
        String url = "/orcamento-familiar/despesa-atualizar/{idDespesa}";
        AtualizarDespesaRequest request = new AtualizarDespesaRequest("internet",
                                                                      new BigDecimal("2000.00"),
                                                                      LocalDate.now());

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put(url, idDespesa)
                                                                 .header("Content-Type", "application/json")
                                                                 .accept("application/json")
                                                                 .content(mapper.writeValueAsString(request)))
                                  .andExpect(status().isOk())
                                  .andReturn();

        then(useCase).should()
                     .execute(new AtualizarDespesaUseCase.InputValues(idDespesa,
                                                                      request.getDescricao(),
                                                                      request.getValor(),
                                                                      request.getData()));

        assertThat(result.getResponse()
                         .getContentAsString()).isEmpty();
    }

    @Test
    void deveLancarExcecaoAoTentarAtualizarDespesaComIdInvalido() {

        Long idDespesa = 1L;
        String url = "/orcamento-familiar/despesa-atualizar/{idDespesa}";
        AtualizarDespesaUseCase.InputValues input = new AtualizarDespesaUseCase.InputValues(idDespesa,
                                                                                            "internet",
                                                                                            new BigDecimal("2000.00"),
                                                                                            LocalDate.now());

        String errorMessage = String.format("Despesa já está cadastrada neste mes");

        willThrow(new BussinessRuleException(errorMessage)).given(useCase)
                                                           .execute(input);

        assertThatThrownBy(() -> useCase.execute(input)).isInstanceOf(BussinessRuleException.class)
                                                        .hasMessageContaining(errorMessage);
    }
}