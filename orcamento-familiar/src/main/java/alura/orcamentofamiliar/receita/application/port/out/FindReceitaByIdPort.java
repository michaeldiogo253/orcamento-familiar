package alura.orcamentofamiliar.receita.application.port.out;

import alura.orcamentofamiliar.receita.domain.Receita;

public interface FindReceitaByIdPort {

    Receita findReceitaById(Long idReceita);
}
