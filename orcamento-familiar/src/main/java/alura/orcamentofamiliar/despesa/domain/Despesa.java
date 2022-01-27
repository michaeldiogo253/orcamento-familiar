package alura.orcamentofamiliar.despesa.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Despesa {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    private String descricao;
    private BigDecimal valor;
    private LocalDate data;
    @Enumerated(EnumType.STRING) Categoria categoria;

    public Despesa(String descricao, BigDecimal valor, LocalDate data, Categoria categoria) {

        this.descricao = descricao;
        this.valor = valor;
        this.data = data;
        this.categoria = categoria;
    }

    public void atualizaDadosDespesa(String descricao, BigDecimal valor, LocalDate data) {

        this.descricao = descricao;
        this.valor = valor;
        this.data = data;
    }
}
