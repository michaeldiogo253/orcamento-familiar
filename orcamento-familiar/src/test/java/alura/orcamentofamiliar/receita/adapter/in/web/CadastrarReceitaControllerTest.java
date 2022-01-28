package alura.orcamentofamiliar.receita.adapter.in.web;

import alura.orcamentofamiliar.receita.application.service.CadastrarReceitaUseCase;
import alura.orcamentofamiliar.receita.domain.Receita;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CadastrarReceitaController.class)
@AutoConfigureMockMvc(addFilters = false)
class CadastrarReceitaControllerTest {

    @MockBean CadastrarReceitaUseCase useCase;

    @Autowired ObjectMapper mapper;
    @Autowired MockMvc mock;

    @Test
    void deveriaCadastrarReceita() throws Exception {

        String url = "/orcamento-familiar/receitas/cadastrar-receita";

        Receita receita = new Receita("Salario", new BigDecimal("2000.00"), LocalDate.now());

        CadastrarReceitaUseCase.InputValues input = new CadastrarReceitaUseCase.InputValues("Salario",
                                                                                            new BigDecimal("2000.00"),
                                                                                            LocalDate.now());

        given(useCase.execute(input)).willReturn(CadastrarReceitaUseCase.OutputValues.of(receita));

        MvcResult result = mock.perform(post(url).header("Content-Type", "application/json")
                                                 .accept("application/json")
                                                 .content(mapper.writeValueAsString(input)))
                               .andExpect(status().isCreated())
                               .andReturn();

        then(useCase).should()
                     .execute(input);
        assertThat(result.getResponse()
                         .getContentAsString()).isEqualTo("{\"descricao\":\"Salario\",\"valor\":2000" +
                                                          ".00,\"data\":\"2022-01-27\"}");
    }

    @Test
    void deveriaLancarUmaBussinessRuleExceptionAoTentarCadastrarUmaReceitaQueJaExiste() {

        String url = "/orcamento-familiar/receitas/cadastrar-receita";

        CadastrarReceitaUseCase.InputValues input = new CadastrarReceitaUseCase.InputValues("Salario",
                                                                                            new BigDecimal("2000.00"),
                                                                                            LocalDate.now());

        String errorMessage = String.format("Receita já está cadastrada neste mes");

        willThrow(new BussinessRuleException(errorMessage)).given(useCase)
                                                     .execute(input);

        assertThatThrownBy(() -> useCase.execute(input)).isInstanceOf(BussinessRuleException.class)
                                                        .hasMessageContaining(errorMessage);

    }

}