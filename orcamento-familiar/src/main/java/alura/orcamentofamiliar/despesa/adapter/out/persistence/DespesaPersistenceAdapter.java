package alura.orcamentofamiliar.despesa.adapter.out.persistence;

import alura.orcamentofamiliar.despesa.application.port.out.*;
import alura.orcamentofamiliar.despesa.domain.Categoria;
import alura.orcamentofamiliar.despesa.domain.Despesa;
import alura.orcamentofamiliar.resumo.adapter.in.web.response.DespesasPorCategoriaResponse;
import alura.orcamentofamiliar.util.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DespesaPersistenceAdapter implements SalvarDespesaPort,
                                                  FindDespesaByIdPort,
                                                  DeletarDespesaPorIdPort,
                                                  ListarTodasAsDespesasPorDescricaoPort,
                                                  ListarTodasAsDespesasPort,
                                                  ExisteDespesaNoPeriodoPort,
                                                  ListarDespesasPorMesPort,
                                                  FindSomaDasDespesasNoMesPort,
                                                  FindDespesasNomesPorCategoriaPort {

    private final DespesaRepository despesaRepository;

    @Override
    @Transactional
    public void salvarDespesa(Despesa despesa) {

        despesaRepository.save(despesa);
    }

    @Override
    public boolean existeNoPeriodo(String descricao, List<LocalDate> periodos) {

        return despesaRepository.existeNoPeriodo(descricao, periodos);
    }

    @Override
    @Transactional
    public void deletarDespesaPorId(Long id) {

        despesaRepository.deleteById(id);
    }

    @Override
    public Despesa findDespesaPorId(Long id) {

        return despesaRepository.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException("Despesa não encontrada"));
    }

    @Override
    public List<Despesa> listarDespesasPorDescricao(String descricao) {

        return despesaRepository.findByDescricao(descricao);
    }

    @Override
    public List<Despesa> listarTodasAsDespesas() {

        return despesaRepository.findAll();
    }

    @Override
    public List<Despesa> listarDespesasPorMes(int ano, int mes) {

        return despesaRepository.findByAnoAndMes(ano, mes);
    }

    @Override
    public BigDecimal somaDasDespesasNoMes(int ano, int mes) {

        return despesaRepository.somaDasDespesasNoMes(ano, mes);
    }

    @Override
    public List<DespesasPorCategoriaResponse> findDespesasByAnoAndMesGroupByCategoria(int ano, int mes) {

        return despesaRepository.findDespesasByAnoAndMesGroupByCategoria(ano, mes);
    }
}
