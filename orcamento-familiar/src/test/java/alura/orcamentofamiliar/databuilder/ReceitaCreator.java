package alura.orcamentofamiliar.databuilder;

import alura.orcamentofamiliar.receita.domain.Receita;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ReceitaCreator {

    public static Receita umaReceitaAleatoria() {

        return new Receita("any_descriacao", BigDecimal.TEN, LocalDate.now());
    }

    public static Receita umaReceitaComDescricao(String descricao) {

        return new Receita(descricao, BigDecimal.TEN, LocalDate.now());
    }

    public static Receita umaReceitaComData(LocalDate data) {

        return new Receita("any_descriacao", BigDecimal.TEN, data);
    }

    public static Receita umaReceita(Long id, String descricao, BigDecimal valor, LocalDate data) {

        return new Receita(id, descricao, valor, data);
    }

    public static List<Receita> variasReceitas(int quantidade) {

        return quantidade > 0 ? IntStream.range(0, quantidade)
                                         .mapToObj(value -> umaReceitaAleatoria())
                                         .collect(Collectors.toList()) : Collections.emptyList();
    }

    public static List<Receita> variasReceitasComDescricaoInternet(int quantidade) {

        return quantidade > 0 ? IntStream.range(0, quantidade)
                                         .mapToObj(value -> umaReceitaComDescricao("Internet"))
                                         .collect(Collectors.toList()) : Collections.emptyList();
    }
}
