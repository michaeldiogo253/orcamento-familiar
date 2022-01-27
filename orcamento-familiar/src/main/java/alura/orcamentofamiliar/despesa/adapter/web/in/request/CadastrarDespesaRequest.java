package alura.orcamentofamiliar.despesa.adapter.web.in.request;

import lombok.Value;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Value
public class CadastrarDespesaRequest {

    @NotNull(message = "Descrição não pode ser nula") String descricao;
    @NotNull(message = "Valor não pode ser nulo") BigDecimal valor;
    @NotNull(message = "Data não pode ser nula") LocalDate data;
}
