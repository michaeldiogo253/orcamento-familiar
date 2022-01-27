package alura.orcamentofamiliar.receita.adapter.in.web.response;

import alura.orcamentofamiliar.receita.domain.Receita;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Value
public class ReceitaResponse {

    String descricao;
    BigDecimal valor;
    LocalDate data;

    public static ReceitaResponse from(Receita receita) {

        return new ReceitaResponse(receita.getDescricao(), receita.getValor(), receita.getData());
    }

    public static List<ReceitaResponse> from(List<Receita> receitas) {

        return receitas.stream()
                       .map(ReceitaResponse::from)
                       .collect(Collectors.toList());
    }
}
