package alura.orcamentofamiliar.receita.adapter.out.persistence;

import alura.orcamentofamiliar.receita.application.port.out.*;
import alura.orcamentofamiliar.receita.domain.Receita;
import alura.orcamentofamiliar.util.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Transactional
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ReceitaPersistenceAdapter implements SalvarReceitaPort,
                                                  ExisteReceitaNoPeriodoPort,
                                                  FindReceitaByIdPort,
                                                  DeletarReceitaByIdPort,
                                                  FindTodasAsReceitasPelaDescricaoPort,
                                                  ListarReceitasPorMesPort,
                                                  FindSomaDasReceitasNoMesPort,
                                                  FindAllReceitasPort {

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
    public Page<Receita> findAllReceitas(Pageable pageable) {

        return receitaRepository.findAll(pageable);
    }

    @Override
    public void deletarReceitaPorId(Long id) {

        receitaRepository.deleteById(id);
    }

    @Override
    public Page<Receita> listaTodasAsReceitasPorDescricao(String descricao, Pageable pageable) {

        return receitaRepository.findByDescricao(descricao, pageable);
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
