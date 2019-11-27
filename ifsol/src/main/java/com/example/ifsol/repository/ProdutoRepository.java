package com.example.ifsol.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.ifsol.models.Produto;

public interface ProdutoRepository extends CrudRepository<Produto, String>{

	Produto findByCodigo(int codigo);
	
}
