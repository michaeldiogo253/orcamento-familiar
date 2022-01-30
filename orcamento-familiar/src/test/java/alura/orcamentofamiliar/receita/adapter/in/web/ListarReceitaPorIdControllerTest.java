package alura.orcamentofamiliar.receita.adapter.in.web;

import alura.orcamentofamiliar.receita.application.port.out.FindReceitaByIdPort;
import alura.orcamentofamiliar.receita.domain.Receita;
import alura.orcamentofamiliar.util.exceptions.ResourceNotFoundException;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ListarReceitaPorIdController.class)
@AutoConfigureMockMvc(addFilters = false)
class ListarReceitaPorIdControllerTest {

    @MockBean private FindReceitaByIdPort findReceitaByIdPort;

    @Autowired ObjectMapper mapper;
    @Autowired MockMvc mockMvc;

    @Test
    void deveriaListarUmaReceitaPorIdValido() throws Exception {

        Long idReceita = 1L;
        Receita receita = new Receita("salario", new BigDecimal("1000"), LocalDate.now());

        given(findReceitaByIdPort.findReceitaById(idReceita)).willReturn(receita);

        String url = "/orcamento-familiar/receitas/{idReceita}";

        MvcResult result = mockMvc.perform(get(url, idReceita).header("Content-Type", "application/json")
                                                              .accept("application/json"))
                                  .andExpect(status().isOk())
                                  .andReturn();

        then(findReceitaByIdPort).should()
                                 .findReceitaById(idReceita);

        assertThat(result.getResponse()
                         .getContentAsString()).isEqualTo("{\"descricao\":\"salario\",\"valor\":1000," +
                                                          "\"data\":\"2022-01-28\"}");
    }

    @Test
    void deveLancarUmaBadRequestExceptionQuandoNaoEncontrarIdReceitaValido() throws Exception {

        Long idReceita = 1L;
        String errorMessage = String.format("Receita já está cadastrada neste mes");

        willThrow(new ResourceNotFoundException(errorMessage)).given(findReceitaByIdPort)
                                                              .findReceitaById(idReceita);

        String url = "/orcamento-familiar/receitas/{idReceita}";

        MvcResult result = mockMvc.perform(get(url, idReceita).header("Content-Type", "application/json")
                                                              .accept("application/json"))
                                  .andExpect(status().isBadRequest())
                                  .andReturn();

        assertThatThrownBy(() -> findReceitaByIdPort.findReceitaById(idReceita)).isInstanceOf(ResourceNotFoundException.class)
                                                        .hasMessageContaining(errorMessage);

    }
}