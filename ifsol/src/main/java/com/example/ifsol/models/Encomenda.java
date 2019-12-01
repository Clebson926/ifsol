package com.example.ifsol.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Encomenda implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int codigoEncomenda;

	@OneToOne
	private Usuario usuario;
	
	@OneToMany
	List<ItemEncomendaProduto> itens_encomenda = new ArrayList<ItemEncomendaProduto>();
	
	public int getCodigoEncomenda() {
		return codigoEncomenda;
	}
	public void setCodigoEncomenda(int codigoEncomenda) {
		this.codigoEncomenda = codigoEncomenda;
	}
	
	public List<ItemEncomendaProduto> getItens_produtos() {
		return itens_encomenda;
	}
	public void setItens_produtos(ItemEncomendaProduto item) {
		itens_encomenda.add(item);
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
