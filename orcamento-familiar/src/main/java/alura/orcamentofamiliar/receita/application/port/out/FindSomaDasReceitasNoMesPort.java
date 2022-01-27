package alura.orcamentofamiliar.receita.application.port.out;

import java.math.BigDecimal;

public interface FindSomaDasReceitasNoMesPort {
    BigDecimal somaDasReceitasNoMes(int ano, int mes);
}
