package com.example.ifsol.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.springframework.security.core.GrantedAuthority;

@Entity
public class Role implements GrantedAuthority{

	@Id
	private String nome;

	@ManyToMany(mappedBy = "roles")
    private List<Usuario> usuarios;
	
	public String getNome() {
		return nome;
	}

	public void setNomeRole(String nome) {
		this.nome = nome;
	}
	
	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	@Override
	public String getAuthority() {
		// TODO Auto-generated method stub
		return this.nome;
	}
}
