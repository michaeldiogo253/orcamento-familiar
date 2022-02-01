package alura.orcamentofamiliar.despesa.adapter.web.in;

import alura.orcamentofamiliar.databuilder.DespesaCreator;
import alura.orcamentofamiliar.despesa.adapter.web.in.response.DespesaResponse;
import alura.orcamentofamiliar.despesa.application.port.out.FindDespesaByIdPort;
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

@WebMvcTest(ListarDespesaPorIdController.class)
@AutoConfigureMockMvc(addFilters = false)
class ListarDespesaPorIdControllerTest {

    @Autowired private MockMvc mockMvc;

    @MockBean private FindDespesaByIdPort findDespesaByIdPort;

    @Autowired private ObjectMapper mapper;

    @Test
    void deveListarDespesaComIdValido() throws Exception {

        Long idDespesa = 1L;
        String url = "/orcamento-familiar/despesas/{idDespesa}";

        Despesa despesa = DespesaCreator.umaDespesa(idDespesa,
                                                    "Internet",
                                                    new BigDecimal("100.00"),
                                                    LocalDate.of(2022, 1, 10),
                                                    Categoria.Moradia);

        DespesaResponse response = DespesaResponse.from(despesa);

        given(findDespesaByIdPort.findDespesaPorId(idDespesa)).willReturn(despesa);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(url, idDespesa)
                                                                 .header("Content-Type", "application/json")
                                                                 .accept("application/json"))
                                  .andExpect(status().isOk())
                                  .andReturn();

        then(findDespesaByIdPort).should()
                                 .findDespesaPorId(idDespesa);
        assertThat(result.getResponse()
                         .getContentAsString()).isEqualTo("{\"descricao\":\"Internet\",\"valor\":100" +
                                                          ".00,\"data\":\"2022-01-10\",\"categoria\":\"Moradia\"}");

        assertThat(result.getResponse()
                         .getContentAsString()).isEqualTo(mapper.writeValueAsString(response));
    }

}