package alura.orcamentofamiliar.receita.adapter.in.web.response;

import alura.orcamentofamiliar.receita.domain.Receita;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReceitaResponse {

    private String descricao;
    private BigDecimal valor;
    private LocalDate data;

    public static ReceitaResponse from(Receita receita) {

        return new ReceitaResponse(receita.getDescricao(), receita.getValor(), receita.getData());
    }

    public static List<ReceitaResponse> from(List<Receita> receitas) {

        return receitas.stream()
                       .map(ReceitaResponse::from)
                       .collect(Collectors.toList());
    }

    public static Page<ReceitaResponse> fromPage(Page<Receita> receitasPaginadas) {

        return receitasPaginadas.map(ReceitaResponse::from);

    }
}
