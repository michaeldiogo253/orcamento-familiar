package alura.orcamentofamiliar.receita.application.port.out;

import alura.orcamentofamiliar.receita.domain.Receita;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FindTodasAsReceitasPelaDescricaoPort {
    Page<Receita> listaTodasAsReceitasPorDescricao(String descricao, Pageable paginacao);
}
