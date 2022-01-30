package alura.orcamentofamiliar.receita.adapter.in.web;

import alura.orcamentofamiliar.receita.application.service.DeletarReceitaPorIdUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = DeletarReceitaPorIdController.class)
@AutoConfigureMockMvc(addFilters = false)
class DeletarReceitaPorIdControllerTest {

    @MockBean DeletarReceitaPorIdUseCase deletarReceitaPorIdUseCase;

    @Autowired ObjectMapper mapper;
    @Autowired MockMvc mockMvc;

    @Test
    void deveDeletarReceitaComIdValido() throws Exception {

        Long idReceita = 1L;
        String url = "/orcamento-familiar/receitas/deletar-receita/{idReceita}";

        MvcResult result = mockMvc.perform(delete(url, idReceita).header("Content-Type", "application/json")
                                                                 .accept("application/json"))
                                  .andExpect(status().isOk())
                                  .andReturn();

        then(deletarReceitaPorIdUseCase).should()
                                        .execute(new DeletarReceitaPorIdUseCase.InputValues(idReceita));

        assertThat(result.getResponse()
                         .getContentAsString()).isEmpty();
    }

}