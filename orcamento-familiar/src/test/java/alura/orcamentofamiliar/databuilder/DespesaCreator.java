package alura.orcamentofamiliar.databuilder;

import alura.orcamentofamiliar.despesa.domain.Categoria;
import alura.orcamentofamiliar.despesa.domain.Despesa;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DespesaCreator {

    public static Despesa umaDespesaAleatoria() {

        return new Despesa("any_descriacao", BigDecimal.TEN, LocalDate.now(), Categoria.Moradia);
    }

    public static Despesa umaDespesa(Long id, String descricao, BigDecimal valor, LocalDate data, Categoria categoria) {

        return new Despesa(id, descricao, valor, data, categoria);
    }

    public static Despesa umaDespesaComDescricaoInternet(){
        return new Despesa("Internet", BigDecimal.TEN, LocalDate.now(), Categoria.Moradia);
    }
    public static List<Despesa> variasDespesas(int quantidade) {

        return quantidade > 0 ? IntStream.range(0, quantidade)
                                         .mapToObj(value -> umaDespesaAleatoria())
                                         .collect(Collectors.toList()) : Collections.emptyList();
    }

    public static List<Despesa> variasDespesasComDescricaoInternet(int quantidade) {

        return quantidade > 0 ? IntStream.range(0, quantidade)
                                         .mapToObj(value -> umaDespesaComDescricaoInternet())
                                         .collect(Collectors.toList()) : Collections.emptyList();
    }

}
