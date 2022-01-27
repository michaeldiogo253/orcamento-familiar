package alura.orcamentofamiliar.receita.application.port.out;

import alura.orcamentofamiliar.receita.domain.Receita;

import java.util.List;

public interface ListarReceitasPorMesPort {

    List<Receita> listarReceitasByYearAndMonth(int year, int month);
}
