package com.example.ifsol.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.ifsol.models.Encomenda;

public interface EncomendaRepository extends CrudRepository<Encomenda, String> {

	Encomenda findByCodigoEncomenda(int codigo);
	
}
