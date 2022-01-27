package alura.orcamentofamiliar.despesa.adapter.out.persistence;

import alura.orcamentofamiliar.despesa.domain.Despesa;
import alura.orcamentofamiliar.resumo.adapter.in.web.response.DespesasPorCategoriaResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface DespesaRepository extends JpaRepository<Despesa, Long> {

    @Query("SELECT COUNT(despesa) > 0 " +
           "FROM Despesa despesa " +
           "WHERE despesa.descricao = :descricao AND despesa.data IN :periodos")
    boolean existeNoPeriodo(String descricao, List<LocalDate> periodos);

    List<Despesa> findByDescricao(String descricao);

    @Query("Select d from Despesa d where year(d.data) = :ano and month(d.data) = :mes ")
    List<Despesa> findByAnoAndMes(int ano, int mes);

    @Query("Select SUM(d.valor) from Despesa d where year(d.data) = :ano and month(d.data) = :mes")
    BigDecimal somaDasDespesasNoMes(int ano, int mes);

    @Query("Select new alura.orcamentofamiliar.resumo.adapter.in.web.response.DespesasPorCategoriaResponse (d.categoria , SUM(d.valor)) from Despesa d where year(d.data) = :ano and month(d.data) = :mes GROUP BY d.categoria")
    List<DespesasPorCategoriaResponse> findDespesasByAnoAndMesGroupByCategoria(int ano, int mes);
}
