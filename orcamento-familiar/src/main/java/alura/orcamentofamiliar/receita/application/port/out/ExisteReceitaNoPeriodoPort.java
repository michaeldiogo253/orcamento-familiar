package alura.orcamentofamiliar.receita.application.port.out;

import java.time.LocalDate;
import java.util.List;

public interface ExisteReceitaNoPeriodoPort {

    boolean existeNoPeriodo(String descricao, List<LocalDate> periodos);
}
