package alura.orcamentofamiliar.despesa.application.port.out;

import alura.orcamentofamiliar.despesa.domain.Despesa;

import java.util.List;

public interface ListarTodasAsDespesasPorDescricaoPort {

    List<Despesa> listarDespesasPorDescricao(String descricao);
}
