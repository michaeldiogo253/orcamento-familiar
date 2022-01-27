package alura.orcamentofamiliar.util.date;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

public class DateUtil {

    public static List<LocalDate> periodos(LocalDate dataReferencia) {

        YearMonth anoMes = YearMonth.of(dataReferencia.getYear(), dataReferencia.getMonth());
        int diasNoMes = anoMes.lengthOfMonth();
        LocalDate dataInicio = LocalDate.of(dataReferencia.getYear(), dataReferencia.getMonth(), 1);
        return dataInicio.datesUntil(dataInicio.plusDays(diasNoMes - 1L))
                         .collect(Collectors.toList());
    }

}
