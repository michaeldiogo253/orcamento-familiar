package alura.orcamentofamiliar.despesa.adapter.web.in;

import alura.orcamentofamiliar.despesa.application.DeletarDespesaPorIdUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DeletarDespesaController.class)
@AutoConfigureMockMvc(addFilters = false)
class DeletarDespesaControllerTest {

    @MockBean private DeletarDespesaPorIdUseCase useCase;

    @Autowired MockMvc mockMvc;

    @Test
    void deveDeletarUmaDespesaComIdValido() throws Exception {

        Long idDespesa = 1L;
        String url = "/orcamento-familiar/despesas/deletar-despesa/{idDespesa}";

        mockMvc.perform(delete(url, idDespesa).header("Content-Type", "application/json")
                                              .accept("application/json"))
               .andExpect(status().isOk());

        then(useCase).should()
                     .execute(new DeletarDespesaPorIdUseCase.InputValues(idDespesa));

    }

}