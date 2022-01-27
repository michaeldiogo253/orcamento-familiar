package alura.orcamentofamiliar.despesa.application.port.out;

import java.time.LocalDate;
import java.util.List;

public interface ExisteDespesaNoPeriodoPort {
 boolean existeNoPeriodo(String descricao, List<LocalDate> periodos);
}
