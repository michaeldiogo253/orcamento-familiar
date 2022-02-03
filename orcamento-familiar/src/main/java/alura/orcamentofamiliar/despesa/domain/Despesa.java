package alura.orcamentofamiliar.despesa.domain;

import alura.orcamentofamiliar.usuario.domain.Usuario;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Entity
public class Despesa {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    private String descricao;
    private BigDecimal valor;
    private LocalDate data;
    @Enumerated(EnumType.STRING) Categoria categoria;

    @Setter @ManyToOne @JoinColumn(name = "usuario_id") private Usuario usuario;

    public Despesa(String descricao, BigDecimal valor, LocalDate data, Categoria categoria) {

        this.descricao = descricao;
        this.valor = valor;
        this.data = data;
        this.categoria = categoria;
    }

    public Despesa(Long id, String descricao, BigDecimal valor, LocalDate data, Categoria categoria) {

        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
        this.data = data;
        this.categoria = categoria;
    }

    public Despesa(String descricao, BigDecimal valor, LocalDate data, Categoria categoria, Usuario usuario) {

        this.descricao = descricao;
        this.valor = valor;
        this.data = data;
        this.categoria = categoria;
        this.usuario = usuario;
    }

    public void atualizaDadosDespesa(String descricao, BigDecimal valor, LocalDate data) {

        this.descricao = descricao;
        this.valor = valor;
        this.data = data;
    }
}
