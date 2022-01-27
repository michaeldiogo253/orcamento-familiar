package alura.orcamentofamiliar.receita.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Receita {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    private String descricao;
    private BigDecimal valor;
    private LocalDate data;

    public Receita(String descricao, BigDecimal valor, LocalDate data) {

        this.descricao = descricao;
        this.valor = valor;
        this.data = data;
    }

    public void atualizaDadosReceita(String descricao, BigDecimal valor, LocalDate data) {

        this.descricao = descricao;
        this.valor = valor;
        this.data = data;
    }
}
