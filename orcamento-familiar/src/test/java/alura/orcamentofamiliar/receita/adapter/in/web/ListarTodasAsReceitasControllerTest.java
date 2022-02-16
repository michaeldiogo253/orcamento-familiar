package alura.orcamentofamiliar.receita.adapter.in.web;

import alura.orcamentofamiliar.databuilder.ReceitaCreator;
import alura.orcamentofamiliar.receita.adapter.in.web.response.ReceitaResponse;
import alura.orcamentofamiliar.receita.application.port.out.FindAllReceitasPort;
import alura.orcamentofamiliar.receita.application.port.out.FindTodasAsReceitasPelaDescricaoPort;
import alura.orcamentofamiliar.receita.domain.Receita;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ListarTodasAsReceitasController.class)
@AutoConfigureMockMvc(addFilters = false)
class ListarTodasAsReceitasControllerTest {

    @MockBean private FindAllReceitasPort findAllReceitasPort;
    @MockBean private FindTodasAsReceitasPelaDescricaoPort findTodasAsReceitasPelaDescricaoPort;

    @Autowired ObjectMapper mapper;
    @Autowired MockMvc mockMvc;

    @Test
    void deveriaListarTodasAsReceitasComDescricaoInternet() throws Exception {

        String url = "/orcamento-familiar/receitas/listar-todas";
        String descricao = "Internet";
        List<Receita> receitas = ReceitaCreator.variasReceitasComDescricaoInternet(3);
        given(findTodasAsReceitasPelaDescricaoPort.listaTodasAsReceitasPorDescricao("Internet")).willReturn(receitas);

        MvcResult result = mockMvc.perform(get(url).header("Content-Type", "application/json")
                                                   .accept("application/json")
                                                   .param("descricao", "Internet"))
                                  .andExpect(status().isOk())
                                  .andReturn();

        then(findAllReceitasPort).shouldHaveNoInteractions();
        then(findTodasAsReceitasPelaDescricaoPort).should()
                                                  .listaTodasAsReceitasPorDescricao(descricao);

        List<ReceitaResponse> responses = ReceitaResponse.from(receitas);
        assertThat(result.getResponse()
                         .getContentAsString()).isEqualTo(mapper.writeValueAsString(responses));
    }

    @Test
    void deveriaChamarAPortDeListarTodasAsReceitasQuandoNaoReceberNenhumaDescricao() throws Exception {

        String url = "/orcamento-familiar/receitas/listar-todas";

        List<Receita> receitas = ReceitaCreator.variasReceitas(3);
        given(findAllReceitasPort.findAllReceitas()).willReturn(receitas);

        MvcResult result = mockMvc.perform(get(url).header("Content-Type", "application/json")
                                                   .accept("application/json"))
                                  .andExpect(status().isOk())
                                  .andReturn();

        then(findTodasAsReceitasPelaDescricaoPort).shouldHaveNoInteractions();
        then(findAllReceitasPort).should()
                                 .findAllReceitas();

       /* assertThat(result.getResponse()
                         .getContentAsString()).isEqualTo("[{\"descricao\":\"any_descriacao\",\"valor\":10," +
                                                          "\"data\":\"2022-02-08\"},{\"descricao\":\"any_descriacao\",\"valor\":10,\"data\":\"2022-02-08\"},{\"descricao\":\"any_descriacao\",\"valor\":10,\"data\":\"2022-02-08\"}]");*/
    }

}