package alura.orcamentofamiliar.despesa.application.port.out;

import alura.orcamentofamiliar.despesa.domain.Despesa;

public interface FindDespesaByIdPort {
    Despesa findDespesaPorId(Long id);
}
