package com.example.ifsol.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.ifsol.models.Encomenda;
import com.example.ifsol.models.ItemEncomendaProduto;

public interface ItemEncomendaProdutoRepository extends CrudRepository<ItemEncomendaProduto, String>{
	
	Iterable<ItemEncomendaProduto> findByEncomenda(Encomenda encomenda);

}
