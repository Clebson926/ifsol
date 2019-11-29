package com.example.ifsol.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.example.ifsol.models.Produto;
import com.example.ifsol.repository.ProdutoRepository;

@Controller
public class ProdutoController {

	@Autowired
	private ProdutoRepository pr;
	
	@RequestMapping(value="/cadastrarProduto", method=RequestMethod.GET)
	public String formulario() {
		return "produto/formularioCadastroProdutos";
	}
	
	@RequestMapping(value="/cadastrarProduto", method=RequestMethod.POST)
	public String formulario(Produto produto) {
		pr.save(produto);
		return "redirect:/cadastrarProduto";
	}
	
	@RequestMapping(value = "/produtos")
	public ModelAndView listaProdutos() {
		ModelAndView mv = new ModelAndView("produto/produtosCadastrados");
		Iterable<Produto> produtos = pr.findAll();
		mv.addObject("produtos", produtos);
		return mv;
	}
	
	@RequestMapping(value = "detalhesProduto/{codigo}")
	public ModelAndView detalhesProduto(@PathVariable("codigo") int codigo) {
		ModelAndView mv = new ModelAndView("produto/detalhesProduto");
		Produto prod = pr.findByCodigo(codigo);
		mv.addObject("produto", prod);
		return mv;
	}
	
	@RequestMapping("/excluirProduto")
	public String excluirProduto(int codigo) {
		Produto produto = pr.findByCodigo(codigo);
		pr.delete(produto);
		return "redirect:/produtos";
	}
	
	@RequestMapping(value = "/{codigo}", method = RequestMethod.GET)
	public ModelAndView editarProduto(@PathVariable("codigo") int codigo) {
		Produto produto = pr.findByCodigo(codigo);
		ModelAndView mv = new ModelAndView("produto/formularioEditarProduto");
		mv.addObject("produto", produto);
		return mv;
	}
	
	 @PostMapping("atualizar/{codigo}")
	    public String atualizarProduto(@PathVariable("codigo") int codigo, Produto produto) {
	        pr.save(produto);
	        return "redirect:/produtos";
	    }
}
