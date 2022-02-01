package alura.orcamentofamiliar.despesa.adapter.web.in;

import alura.orcamentofamiliar.despesa.application.DeletarDespesaPorIdUseCase;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(DeletarDespesaController.class)
@AutoConfigureMockMvc(addFilters = false)
class DeletarDespesaControllerTest {

    private DeletarDespesaPorIdUseCase useCase = Mockito.mock(DeletarDespesaPorIdUseCase.class);
    DeletarDespesaController deletarDespesaController = new DeletarDespesaController(useCase);

    @Autowired MockMvc mockMvc;

    @Test
    void deveDeletarUmaDespesaComIdValido(){



    }

}