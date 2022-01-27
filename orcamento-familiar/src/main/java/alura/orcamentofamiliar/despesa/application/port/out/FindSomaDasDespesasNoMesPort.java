package alura.orcamentofamiliar.despesa.application.port.out;

import java.math.BigDecimal;

public interface FindSomaDasDespesasNoMesPort {
    BigDecimal somaDasDespesasNoMes(int ano, int mes);
}
