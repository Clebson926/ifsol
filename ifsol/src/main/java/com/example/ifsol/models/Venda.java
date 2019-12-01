package com.example.ifsol.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Venda {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int codigoVenda;
	@OneToOne
	private Usuario usuario;
	
	@OneToMany
	List<ItemVenda> itens = new ArrayList<ItemVenda>();
	
	public int getCodigoVenda() {
		return codigoVenda;
	}

	public void setCodigoVenda(int codigoVenda) {
		this.codigoVenda = codigoVenda;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<ItemVenda> getItens() {
		return itens;
	}

	public void setItens(ItemVenda item) {
		itens.add(item);
	}
}
