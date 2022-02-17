package alura.orcamentofamiliar.receita.adapter.out.persistence;

import alura.orcamentofamiliar.receita.domain.Receita;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface ReceitaRepository extends JpaRepository<Receita, Long> {

    @Query("SELECT COUNT(receita) > 0 " +
           "FROM Receita receita " +
           "WHERE receita.descricao = :descricao AND receita.data IN :periodos")
    boolean existeNoPeriodo(String descricao, List<LocalDate> periodos);

    Page<Receita> findByDescricao(String descricao, Pageable paginacao);

    @Query("select r from Receita r where year(r.data) = :ano and month(r.data) = :mes")
    List<Receita> listarReceitasByYearAndMonth(int ano, int mes);

    @Query("Select SUM(r.valor) from Receita r where year(r.data) = :ano and month(r.data) = :mes")
    BigDecimal findSomaReceitasMensais(int ano, int mes);
}
