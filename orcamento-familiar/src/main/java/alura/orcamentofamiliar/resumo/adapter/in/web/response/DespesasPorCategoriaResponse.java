package alura.orcamentofamiliar.resumo.adapter.in.web.response;

import alura.orcamentofamiliar.despesa.domain.Categoria;
import lombok.Value;

import java.math.BigDecimal;

@Value
public class DespesasPorCategoriaResponse {

    Categoria categoria;
    BigDecimal valor;

}
