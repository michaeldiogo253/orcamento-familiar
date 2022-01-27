package alura.orcamentofamiliar.despesa.application.port.out;

import alura.orcamentofamiliar.despesa.domain.Despesa;

import java.util.List;

public interface ListarDespesasPorMesPort {

    List<Despesa> listarDespesasPorMes(int ano, int mes);
}
