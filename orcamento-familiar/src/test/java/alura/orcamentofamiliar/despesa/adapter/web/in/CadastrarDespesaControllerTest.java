package alura.orcamentofamiliar.despesa.adapter.web.in;

import alura.orcamentofamiliar.despesa.adapter.web.in.request.CadastrarDespesaRequest;
import alura.orcamentofamiliar.despesa.application.CadastrarDespesaUseCase;
import alura.orcamentofamiliar.despesa.domain.Categoria;
import alura.orcamentofamiliar.despesa.domain.Despesa;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CadastrarDespesaController.class)
@AutoConfigureMockMvc(addFilters = false)
class CadastrarDespesaControllerTest {

    @MockBean CadastrarDespesaUseCase useCase;

    @Autowired MockMvc mockMvc;

    @Autowired ObjectMapper mapper;

    @Test
    void deveCadastrarUmaNovaDespesa() throws Exception {

        String url = "/orcamento-familiar/despesas/cadastrar-despesa";
        CadastrarDespesaRequest request = new CadastrarDespesaRequest("agua",
                                                                      new BigDecimal("90.00"),
                                                                      LocalDate.of(2022, 1, 20));

        Despesa despesa = new Despesa(request.getDescricao(), request.getValor(), request.getData(), Categoria.Moradia);

        CadastrarDespesaUseCase.InputValues input = new CadastrarDespesaUseCase.InputValues(request.getDescricao(),
                                                                                            request.getValor(),
                                                                                            request.getData(),
                                                                                            Categoria.Moradia);

        given(useCase.execute(input)).willReturn(CadastrarDespesaUseCase.OutputValues.of(despesa));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(url)
                                                                 .header("Content-Type", "application/json")
                                                                 .accept("application/json")
                                                                 .content(mapper.writeValueAsString(input))
                                                                 .param("categoria", "Moradia"))
                                  .andExpect(status().isCreated())
                                  .andReturn();

        then(useCase).should()
                     .execute(input);

        assertThat(result.getResponse()
                         .getContentAsString()).isEqualTo("{\"descricao\":\"agua\",\"valor\":90.00," +
                                                          "\"data\":\"2022-01-20\",\"categoria\":\"Moradia\"}");

    }
}