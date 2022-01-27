package alura.orcamentofamiliar.despesa.application.port.out;

import alura.orcamentofamiliar.despesa.domain.Despesa;

public interface SalvarDespesaPort {
    public void salvarDespesa(Despesa despesa);
}
