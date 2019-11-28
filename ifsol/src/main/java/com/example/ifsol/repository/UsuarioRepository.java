package com.example.ifsol.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.ifsol.models.Usuario;

public interface UsuarioRepository extends CrudRepository<Usuario, String>{

	Usuario findByEmail(String email);
}
