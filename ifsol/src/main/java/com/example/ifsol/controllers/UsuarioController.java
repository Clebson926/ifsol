package com.example.ifsol.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.ifsol.models.Role;
import com.example.ifsol.models.Usuario;
import com.example.ifsol.repository.RoleRepository;
import com.example.ifsol.repository.UsuarioRepository;

@Controller
public class UsuarioController {

	@Autowired
	private UsuarioRepository ur;
	
	@Autowired
	private RoleRepository rr;

	@RequestMapping(value = "/cadastrarUsuario", method = RequestMethod.GET)
	public String formCadastrarNovoUsuario() {
		return "usuario/formCadastrarNovoUsuario";
	}

	@RequestMapping(value = "/cadastrarUsuario", method = RequestMethod.POST)
	public String formCadastrarNovoUsuario(@RequestParam("perfil") String perfil, Usuario usuario) {
		
		//Ecriptando a senha
		String senhaEncriptada = new BCryptPasswordEncoder().encode(usuario.getSenha());
		usuario.setSenha(senhaEncriptada);
		
		//Setando o perfil
		Role role = new Role();
		role.setNomeRole("ROLE_"+perfil);
		rr.save(role);
		
		role.setUsuarios(usuario);
		usuario.setRoles(role);
				
		ur.save(usuario);
		
		return "redirect:/cadastrarUsuario";
	}

	@RequestMapping(value = "/usuarios")
	public ModelAndView listarUsuarios() {
		ModelAndView mv = new ModelAndView("usuario/usuariosCadastrados");
		Iterable<Usuario> usuarios = ur.findAll();
		mv.addObject("usuarios", usuarios);
		return mv;
	}

}
