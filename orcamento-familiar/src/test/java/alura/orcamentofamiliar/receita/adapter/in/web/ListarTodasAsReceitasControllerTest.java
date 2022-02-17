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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    Pageable paginacao = PageRequest.of(0, 5);

    @Test
    void deveriaListarTodasAsReceitasComDescricaoInternet() throws Exception {


        String url = "/orcamento-familiar/receitas/listar-todas?page=0&size=5";
        String descricao = "Internet";
        List<Receita> receitas = ReceitaCreator.variasReceitasComDescricaoInternet(3);
        Page<Receita> paginas = converteListPage(receitas, paginacao);
        given(findTodasAsReceitasPelaDescricaoPort.listaTodasAsReceitasPorDescricao("Internet",
                                                                                    paginacao)).willReturn(paginas);

        MvcResult result = mockMvc.perform(get(url).header("Content-Type", "application/json")
                                                   .accept("application/json")
                                                   .param("descricao", "Internet"))
                                  .andExpect(status().isOk())
                                  .andReturn();

        then(findAllReceitasPort).shouldHaveNoInteractions();
        then(findTodasAsReceitasPelaDescricaoPort).should()
                                                  .listaTodasAsReceitasPorDescricao(descricao, paginacao);

        Page<ReceitaResponse> responses = ReceitaResponse.fromPage(paginas);
        assertThat(result.getResponse()
                         .getContentAsString()).isEqualTo(mapper.writeValueAsString(responses));
    }

    @Test
    void deveriaChamarAPortDeListarTodasAsReceitasQuandoNaoReceberNenhumaDescricao() throws Exception {

        String url = "/orcamento-familiar/receitas/listar-todas?page=0&size=5";

        List<Receita> receitas = ReceitaCreator.variasReceitas(3);
        Page<Receita> paginas = converteListPage(receitas, paginacao);


        given(findAllReceitasPort.findAllReceitas(paginacao)).willReturn(paginas);


        MvcResult result = mockMvc.perform(get(url).header("Content-Type", "application/json")
                                                   .accept("application/json"))
                                  .andExpect(status().isOk())
                                  .andReturn();

        then(findTodasAsReceitasPelaDescricaoPort).shouldHaveNoInteractions();
        then(findAllReceitasPort).should()
                                 .findAllReceitas(paginacao);

       /* assertThat(result.getResponse()
                         .getContentAsString()).isEqualTo("[{\"descricao\":\"any_descriacao\",\"valor\":10," +
                                                          "\"data\":\"2022-02-08\"},{\"descricao\":\"any_descriacao\",\"valor\":10,\"data\":\"2022-02-08\"},{\"descricao\":\"any_descriacao\",\"valor\":10,\"data\":\"2022-02-08\"}]");*/
    }

    private Page<Receita> converteListPage(List<Receita> listReceitas, Pageable pageable){

        Page<Receita> pages = new PageImpl<Receita>(listReceitas, pageable, listReceitas.size());
        return pages;
    }
}