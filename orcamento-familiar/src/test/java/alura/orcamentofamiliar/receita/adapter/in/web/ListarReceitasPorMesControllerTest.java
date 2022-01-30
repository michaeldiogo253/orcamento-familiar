package alura.orcamentofamiliar.receita.adapter.in.web;

import alura.orcamentofamiliar.databuilder.ReceitaCreator;
import alura.orcamentofamiliar.receita.application.port.out.ListarReceitasPorMesPort;
import alura.orcamentofamiliar.receita.domain.Receita;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ListarReceitasPorMesController.class)
@AutoConfigureMockMvc(addFilters = false)
class ListarReceitasPorMesControllerTest {

    @MockBean ListarReceitasPorMesPort listarReceitasPorMesPort;

    @Autowired ObjectMapper mapper;
    @Autowired MockMvc mockMvc;

    @Test
    void deveriaListarTodasAsReceitasDeUmDeterminadoMes() throws Exception {

        int ano = 2022;
        int mes = 1;
        String url = "/orcamento-familiar/listar-receitas/{ano}/{mes}";

        List<Receita> receitas = geraReceitas();

        given(listarReceitasPorMesPort.listarReceitasByYearAndMonth(ano, mes)).willReturn(receitas);

        MvcResult result = mockMvc.perform(get(url, ano, mes).header("Content-Type", "application/json")
                                                             .accept("application/json"))
                                  .andExpect(status().isOk())
                                  .andReturn();

        then(listarReceitasPorMesPort).should()
                                      .listarReceitasByYearAndMonth(ano, mes);

        assertThat(result.getResponse()
                         .getContentAsString()).isEqualTo("[{\"descricao\":\"any_descriacao\",\"valor\":10," +
                                                          "\"data\":\"2022-01-20\"}," +
                                                          "{\"descricao\":\"any_descriacao\",\"valor\":10," +
                                                          "\"data\":\"2022-01-22\"}," +
                                                          "{\"descricao\":\"any_descriacao\",\"valor\":10," +
                                                          "\"data\":\"2022-01-28\"}]");

    }

    private List<Receita> geraReceitas() {

        Receita receita1 = ReceitaCreator.umaReceitaComData(LocalDate.of(2022, 1, 20));
        Receita receita2 = ReceitaCreator.umaReceitaComData(LocalDate.of(2022, 1, 22));
        Receita receita3 = ReceitaCreator.umaReceitaComData(LocalDate.of(2022, 1, 28));

        return List.of(receita1, receita2, receita3);
    }

}