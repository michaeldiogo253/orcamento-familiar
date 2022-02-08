package alura.orcamentofamiliar.usuario.domain;

import alura.orcamentofamiliar.despesa.domain.Despesa;
import alura.orcamentofamiliar.receita.domain.Receita;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "usuarios")
public class Usuario implements UserDetails {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    private String nome;
    private String login;
    private String senha;

    @ManyToMany(fetch = FetchType.EAGER) private List<Perfil> perfis = new ArrayList<>();

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY,
               cascade = {CascadeType.PERSIST, CascadeType.MERGE}) List<Despesa> despesas;

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY,
               cascade = {CascadeType.PERSIST, CascadeType.MERGE}) List<Receita> receitas;

    public Usuario(String nome, String login, String senha) {

        this.nome = nome;
        this.login = login;
        this.senha = senha;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return perfis;
    }

    @Override
    public String getPassword() {

        return this.senha;
    }

    @Override
    public String getUsername() {

        return this.login;
    }

    @Override
    public boolean isAccountNonExpired() {

        return true;
    }

    @Override
    public boolean isAccountNonLocked() {

        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {

        return true;
    }

    @Override
    public boolean isEnabled() {

        return true;
    }
}
