package alura.orcamentofamiliar.receita.domain;

import alura.orcamentofamiliar.usuario.domain.Usuario;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Entity
public class Receita {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    private String descricao;
    private BigDecimal valor;
    private LocalDate data;

    @ManyToOne @JoinColumn(name = "usuario_id") private Usuario usuario;

    public Receita(String descricao, BigDecimal valor, LocalDate data) {

        this.descricao = descricao;
        this.valor = valor;
        this.data = data;
    }

    public Receita(Long id, String descricao, BigDecimal valor, LocalDate data) {

        this.id = id;
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
