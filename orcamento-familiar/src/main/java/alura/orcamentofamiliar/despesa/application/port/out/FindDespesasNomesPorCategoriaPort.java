package alura.orcamentofamiliar.despesa.application.port.out;

import alura.orcamentofamiliar.resumo.adapter.in.web.response.DespesasPorCategoriaResponse;

import java.util.List;

public interface FindDespesasNomesPorCategoriaPort {

    List<DespesasPorCategoriaResponse> findDespesasByAnoAndMesGroupByCategoria(int ano, int mes);

}
