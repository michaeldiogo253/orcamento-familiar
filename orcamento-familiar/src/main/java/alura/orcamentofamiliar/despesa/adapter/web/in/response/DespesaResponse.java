package alura.orcamentofamiliar.despesa.adapter.web.in.response;

import alura.orcamentofamiliar.despesa.domain.Categoria;
import alura.orcamentofamiliar.despesa.domain.Despesa;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Value
public class DespesaResponse {

    String descricao;
    BigDecimal valor;
    LocalDate data;
    Categoria categoria;

    public static DespesaResponse from(Despesa despesa) {

        return new DespesaResponse(despesa.getDescricao(),
                                   despesa.getValor(),
                                   despesa.getData(),
                                   despesa.getCategoria());
    }

    public static List<DespesaResponse> from(List<Despesa> despesas) {

        return despesas.stream()
                       .map(DespesaResponse::from)
                       .collect(Collectors.toList());
    }
}
