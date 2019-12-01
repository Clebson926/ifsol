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

import com.example.ifsol.models.ItemVenda;
import com.example.ifsol.models.Produto;
import com.example.ifsol.models.Usuario;
import com.example.ifsol.models.Venda;
import com.example.ifsol.repository.ItemVendaRepository;
import com.example.ifsol.repository.ProdutoRepository;
import com.example.ifsol.repository.UsuarioRepository;
import com.example.ifsol.repository.VendaRepository;

@Controller
public class VendaController {
	
	ArrayList<ItemVenda> produtos = new ArrayList<ItemVenda>();
	
	@Autowired
	private UsuarioRepository ur;
	
	@Autowired
	private ItemVendaRepository ivr;
	
	@Autowired
	private VendaRepository vr;
	
	@Autowired
	private ProdutoRepository pr;
	
	@RequestMapping(value="/registrarVenda", method=RequestMethod.GET)
	public ModelAndView formRegistrarVenda() {
		ModelAndView mv = new ModelAndView("venda/formularioRegistrarVendas");
		Iterable<Produto> produtos = pr.findAll();
		mv.addObject("produtos", produtos);
		return mv;
	}
	
	@RequestMapping(value = "detalhesProdutoVenda/{codigo}")
	public ModelAndView detalhesProduto(@PathVariable("codigo") int codigo) {
		ModelAndView mv = new ModelAndView("venda/detalhesProdutoVenda");
		Produto prod = pr.findByCodigo(codigo);
		mv.addObject("produto", prod);
		return mv;
	}
	
	@RequestMapping(value="/addItemVenda", method=RequestMethod.POST)
	public String addItemVenda(@RequestParam("quantidade") String quantidade, @RequestParam("codigo") String codigo) {
		Produto produto = pr.findByCodigo(Integer.parseInt(codigo));
		
		ItemVenda item = new ItemVenda();
		item.setProduto(produto);
		item.setQuantidade(Integer.parseInt(quantidade));
		produtos.add(item);
		
		return "redirect:/registrarVenda";
	}
	
	@RequestMapping(value="/carrinhoVenda", method=RequestMethod.GET)
	public ModelAndView carrinhoVenda() {
		ModelAndView mv = new ModelAndView("venda/carrinhoVenda");
		mv.addObject("produtos", produtos);
		return mv;
	}

	@RequestMapping(value="/finalizarVenda", method=RequestMethod.POST)
	public String finalizarVenda() {
		
        Authentication authentication = (Authentication) SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.getName());

        Venda venda = new Venda();
        Usuario usuario = ur.findByEmail(authentication.getName());
        venda.setUsuario(usuario);
        
        for(int i=0; i<produtos.size(); i++) {
        	ItemVenda item = produtos.get(i);
        	venda.setItens(item);
        	ivr.save(produtos.get(i));
        	produtos.get(i).setVenda(venda);;
        	vr.save(venda);
        }
        produtos.clear();
		return "redirect:/registrarVenda";
	}
	
	@RequestMapping(value="/vendas", method=RequestMethod.GET)
	public ModelAndView listaVendas() {
		ModelAndView mv = new ModelAndView("venda/listaVendas");
		
		Iterable<Venda> vendas = vr.findAll();
		
		Iterable<ItemVenda> itens = ivr.findAll();
		mv.addObject("itens", itens);
		mv.addObject("vendas", vendas);
		
		return mv;
	}
	
}
