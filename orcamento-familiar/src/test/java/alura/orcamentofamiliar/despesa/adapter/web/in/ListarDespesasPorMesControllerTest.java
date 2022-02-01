package alura.orcamentofamiliar.despesa.adapter.web.in;

import alura.orcamentofamiliar.databuilder.DespesaCreator;
import alura.orcamentofamiliar.despesa.adapter.web.in.response.DespesaResponse;
import alura.orcamentofamiliar.despesa.application.port.out.ListarDespesasPorMesPort;
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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ListarDespesasPorMesController.class)
@AutoConfigureMockMvc(addFilters = false)
class ListarDespesasPorMesControllerTest {

    @Autowired private MockMvc mockMvc;

    @MockBean private ListarDespesasPorMesPort listarDespesasPorMesPort;

    @Autowired private ObjectMapper mapper;

    @Test
    void deveListarDespesasPorMes() throws Exception {

        int ano = 2022;
        int mes = 1;
        String url = "/orcamento-familiar/listar-despesas/{ano}/{mes}";

        List<Despesa> despesas = DespesaCreator.variasDespesas(3);
        given(listarDespesasPorMesPort.listarDespesasPorMes(ano, mes)).willReturn(despesas);

        List<DespesaResponse> responses = DespesaResponse.from(despesas);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(url, ano, mes)
                                                                 .header("Content-Type", "application/json")
                                                                 .accept("application/json"))
                                  .andExpect(status().isOk())
                                  .andReturn();

        then(listarDespesasPorMesPort).should()
                                      .listarDespesasPorMes(ano, mes);

        assertThat(result.getResponse()
                         .getContentAsString()).isEqualTo(mapper.writeValueAsString(responses));

    }
}