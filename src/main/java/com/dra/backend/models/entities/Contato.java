package com.dra.backend.models.entities;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;

import lombok.*;

@NoArgsConstructor
@Data
@Entity
@Table(name = "users")
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

	public Contato(String nome, String email, String senha, String telefone, String endereco, String bairro,
			String cidade, String estado) {
		this.setNome(nome);
		this.setEmail(email);
		this.setSenha(senha);
		this.setTelefone(telefone);
		this.setEndereco(endereco);
		this.setBairro(bairro);
		this.setCidade(cidade);
		this.setEstado(estado);
		this.role = ContatoRole.USER;
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
