package com.dra.backend.models.entities;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.dra.backend.dto.auth.CriarContatoDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import lombok.*;

@Data
@Schema(hidden = true)
@Entity
@Table(name = "users")
@NoArgsConstructor
public class Contato implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String senha;
    @Column(nullable = true)
    private String telefone;
    @Column(nullable = true)
    private String endereco;
    @Column(nullable = true)
    private String bairro;
    @Column(nullable = true)
    private String cidade;
    @Column(nullable = true)
    private String estado;
    @Column(nullable = false)
    private ContatoRole role;

    public Contato(CriarContatoDTO contatoDTO) {
        this.nome = contatoDTO.getNome();
        this.email = contatoDTO.getEmail();
        this.senha = contatoDTO.getSenha();
        this.telefone = contatoDTO.getTelefone();
        this.endereco = contatoDTO.getEndereco();
        this.bairro = contatoDTO.getBairro();
        this.cidade = contatoDTO.getCidade();
        this.estado = contatoDTO.getEstado();
        this.role = ContatoRole.USER;
    }

    public static Contato from(CriarContatoDTO contatoDTO) {
        return new Contato(contatoDTO);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role == ContatoRole.ADMIN) {
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        }
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return senha;
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