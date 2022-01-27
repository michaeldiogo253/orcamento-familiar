package alura.orcamentofamiliar.receita.application.port.out;

import alura.orcamentofamiliar.receita.domain.Receita;

public interface SalvarReceitaPort {
    void salvarReceita(Receita receita);
}
