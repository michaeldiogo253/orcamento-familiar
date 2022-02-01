package alura.orcamentofamiliar.despesa.adapter.web.in;

import alura.orcamentofamiliar.databuilder.DespesaCreator;
import alura.orcamentofamiliar.despesa.adapter.web.in.response.DespesaResponse;
import alura.orcamentofamiliar.despesa.application.port.out.ListarTodasAsDespesasPorDescricaoPort;
import alura.orcamentofamiliar.despesa.application.port.out.ListarTodasAsDespesasPort;
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

@WebMvcTest(ListarTodasAsDespesasController.class)
@AutoConfigureMockMvc(addFilters = false)
class ListarTodasAsDespesasControllerTest {

    @MockBean private ListarTodasAsDespesasPorDescricaoPort listarTodasAsDespesasPorDescricaoPort;
    @MockBean private ListarTodasAsDespesasPort listarTodasAsDespesasPort;

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper mapper;

    @Test
    void deveListarTodasAsDespesasComDescricaoInternet() throws Exception {

        List<Despesa> despesas = DespesaCreator.variasDespesasComDescricaoInternet(3);
        String descricao = "Internet";
        String url = "/orcamento-familiar/despesas/listar-todas";
        given(listarTodasAsDespesasPorDescricaoPort.listarDespesasPorDescricao(descricao)).willReturn(despesas);

        List<DespesaResponse> responses = DespesaResponse.from(despesas);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(url)
                                                                 .header("Content-Type", "application/json")
                                                                 .accept("application/json")
                                                                 .param("descricao", "Internet"))
                                  .andExpect(status().isOk())
                                  .andReturn();

        then(listarTodasAsDespesasPorDescricaoPort).should()
                                                   .listarDespesasPorDescricao(descricao);
        then(listarTodasAsDespesasPort).shouldHaveNoInteractions();

        assertThat(result.getResponse()
                         .getContentAsString()).isEqualTo(mapper.writeValueAsString(responses));

    }

    @Test
    void deveListarTodasAsDespesasQuandoNenhumaDescricaoForPassadaComRequestParam() throws Exception {

        List<Despesa> despesas = DespesaCreator.variasDespesas(3);

        String url = "/orcamento-familiar/despesas/listar-todas";
        given(listarTodasAsDespesasPort.listarTodasAsDespesas()).willReturn(despesas);

        List<DespesaResponse> responses = DespesaResponse.from(despesas);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(url)
                                                                 .header("Content-Type", "application/json")
                                                                 .accept("application/json"))
                                  .andExpect(status().isOk())
                                  .andReturn();

        then(listarTodasAsDespesasPort).should().listarTodasAsDespesas();

        then(listarTodasAsDespesasPorDescricaoPort).shouldHaveNoInteractions();

        assertThat(result.getResponse()
                         .getContentAsString()).isEqualTo(mapper.writeValueAsString(responses));

    }

}