package com.example.ifsol.controllers;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.ifsol.models.Encomenda;
import com.example.ifsol.models.ItemEncomendaProduto;
import com.example.ifsol.models.Produto;
import com.example.ifsol.models.Usuario;
import com.example.ifsol.repository.EncomendaRepository;
import com.example.ifsol.repository.ItemEncomendaProdutoRepository;
import com.example.ifsol.repository.ProdutoRepository;
import com.example.ifsol.repository.UsuarioRepository;

@Controller
public class EncomendaController {
	
	ArrayList<ItemEncomendaProduto> produtos = new ArrayList<ItemEncomendaProduto>();
	
	@Autowired
	private ProdutoRepository pr;
	
	@Autowired
	private UsuarioRepository ur;
	
	@Autowired
	private EncomendaRepository er;
	
	@Autowired
	private ItemEncomendaProdutoRepository iepr;
	
	@RequestMapping(value="/registrarEncomenda", method=RequestMethod.GET)
	public ModelAndView formRegistrarEncomenda() {
		ModelAndView mv = new ModelAndView("encomenda/formularioEncomendarProdutos");
		Iterable<Produto> produtos = pr.findAll();
		mv.addObject("produtos", produtos);
		return mv;
	}
	
	@RequestMapping(value="/addItemEncomenda", method=RequestMethod.POST)
	public String addItemEncomenda(@RequestParam("quantidade") String quantidade, @RequestParam("codigo") String codigo) {
		//ModelAndView mv = new ModelAndView("encomenda/formularioEncomendarProdutos");
		//Iterable<Produto> produtos = pr.findAll();
		//mv.addObject("produtos", produtos);
	
		Produto produto = pr.findByCodigo(Integer.parseInt(codigo));
		
		ItemEncomendaProduto item = new ItemEncomendaProduto();
		item.setProduto(produto);
		item.setQuantidade(Integer.parseInt(quantidade));
		produtos.add(item);
		
		return "redirect:/registrarEncomenda";
	}
	
	@RequestMapping(value="/carrinho", method=RequestMethod.GET)
	public ModelAndView carrinho() {
		ModelAndView mv = new ModelAndView("encomenda/carrinho");
		mv.addObject("produtos", produtos);
		return mv;
	}
	
	@RequestMapping(value="/finalizarEncomenda", method=RequestMethod.POST)
	public String finalizarEncomenda() {
		
        Authentication authentication = (Authentication) SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.getName());

        Encomenda encomenda = new Encomenda();
        Usuario usuario = ur.findByEmail(authentication.getName());
        encomenda.setUsuario(usuario);
        
        for(int i=0; i<produtos.size(); i++) {
        	ItemEncomendaProduto item = produtos.get(i);
        	encomenda.setItens_produtos(item);
        	iepr.save(produtos.get(i));
        	produtos.get(i).setEncomenda(encomenda);
        	er.save(encomenda);
        }
        produtos.clear();
		return "redirect:/registrarEncomenda";
	}
	
	@RequestMapping(value="/encomendas", method=RequestMethod.GET)
	public ModelAndView listaEncomendas() {
		ModelAndView mv = new ModelAndView("encomenda/listaEncomendas");
		
		Iterable<Encomenda> encomendas = er.findAll();
		
		Iterable<ItemEncomendaProduto> itens = iepr.findAll();
		mv.addObject("itens", itens);
		mv.addObject("encomendas", encomendas);
		
		return mv;
	}
	
	@RequestMapping(value = "detalhesEncomenda/{codigoEncomenda}")
	public ModelAndView detalhesEncomenda(@PathVariable("codigoEncomenda") int codigoEncomenda) {
		ModelAndView mv = new ModelAndView("encomenda/detalhesEncomenda");
		Encomenda encomenda = er.findByCodigoEncomenda(codigoEncomenda);
		mv.addObject("encomenda", encomenda);
		return mv;
	}
	
}
