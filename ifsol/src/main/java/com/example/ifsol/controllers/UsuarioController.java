package com.example.ifsol.controllers;

import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.example.ifsol.models.Usuario;
import com.example.ifsol.repository.UsuarioRepository;

@Controller
public class UsuarioController {

	@Autowired
	private UsuarioRepository ur;

	@RequestMapping(value = "/cadastrarUsuario", method = RequestMethod.GET)
	public String formCadastrarNovoUsuario() {
		return "usuario/formCadastrarNovoUsuario";
	}

	@RequestMapping(value = "/cadastrarUsuario", method = RequestMethod.POST)
	public String formCadastrarNovoUsuario(Usuario usuario) {
		String senhaEncriptada = new BCryptPasswordEncoder().encode(usuario.getSenha());
		usuario.setSenha(senhaEncriptada);
		ur.save(usuario);
		return "redirect:/cadastrarUsuario";
	}

	@RequestMapping(value = "/usuarios")
	public ModelAndView listarUsuarios() {
		ModelAndView mv = new ModelAndView("usuario/usuariosCadastrados");
		BCryptPasswordEncoder enc = new BCryptPasswordEncoder();
		Iterable<Usuario> usuarios = ur.findAll();
		Iterator<Usuario> it = usuarios.iterator();
		while (it.hasNext()) {
			System.out.println(it.next().getSenha());
		}
		mv.addObject("usuarios", usuarios);
		return mv;
	}

}
