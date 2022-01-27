package alura.orcamentofamiliar.receita.adapter.out.persistence;

import alura.orcamentofamiliar.despesa.application.port.out.FindDespesasNomesPorCategoriaPort;
import alura.orcamentofamiliar.despesa.domain.Categoria;
import alura.orcamentofamiliar.receita.application.port.out.FindTodasAsReceitasPelaDescricaoPort;
import alura.orcamentofamiliar.receita.application.port.out.ListarReceitasPorMesPort;
import alura.orcamentofamiliar.receita.application.port.out.*;
import alura.orcamentofamiliar.receita.domain.Receita;
import alura.orcamentofamiliar.util.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Transactional
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ReceitaPersistenceAdapter implements SalvarReceitaPort,
                                                  ExisteReceitaNoPeriodoPort,
                                                  FindReceitaByIdPort,
                                                  FindAllReceitasPort,
                                                  DeletarReceitaByIdPort,
                                                  FindTodasAsReceitasPelaDescricaoPort,
                                                  ListarReceitasPorMesPort,
                                                  FindSomaDasReceitasNoMesPort {

    private final ReceitaRepository receitaRepository;

    @Override
    public void salvarReceita(Receita receita) {

        receitaRepository.save(receita);
    }

    @Override
    public boolean existeNoPeriodo(String descricao, List<LocalDate> periodos) {

        return receitaRepository.existeNoPeriodo(descricao, periodos);
    }

    @Override
    public Receita findReceitaById(Long idReceita) {

        return receitaRepository.findById(idReceita)
                                .orElseThrow(() -> new ResourceNotFoundException("Receita não encontrada"));
    }

    @Override
    public List<Receita> findAllReceitas() {

        return receitaRepository.findAll();
    }

    @Override
    public void deletarReceitaPorId(Long id) {

        receitaRepository.deleteById(id);
    }

    @Override
    public List<Receita> listaTodasAsReceitasPorDescricao(String descricao) {

        return receitaRepository.findByDescricao(descricao);
    }

    @Override
    public List<Receita> listarReceitasByYearAndMonth(int year, int month) {

        return receitaRepository.listarReceitasByYearAndMonth(year, month);
    }

    @Override
    public BigDecimal somaDasReceitasNoMes(int ano, int mes) {

        return receitaRepository.findSomaReceitasMensais(ano, mes);
    }


}
