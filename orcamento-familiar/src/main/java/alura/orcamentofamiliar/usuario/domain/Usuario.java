package alura.orcamentofamiliar.usuario.domain;

import alura.orcamentofamiliar.despesa.domain.Despesa;
import alura.orcamentofamiliar.receita.domain.Receita;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "usuarios")
public class Usuario {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    private String nome;
    private String login;
    private String senha;

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY,
               cascade = {CascadeType.PERSIST, CascadeType.MERGE}) List<Despesa> despesas;

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY,
               cascade = {CascadeType.PERSIST, CascadeType.MERGE}) List<Receita> receitas;

    public Usuario(String nome, String login, String senha) {

        this.nome = nome;
        this.login = login;
        this.senha = senha;
    }
}
