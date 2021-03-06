package com.example.ifsol.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private ImplementsUserDetailsService userDetailsService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.csrf().disable().authorizeRequests()
		.antMatchers(HttpMethod.GET, "/cadastrarProduto").hasRole("PRODUTOR")
		.antMatchers(HttpMethod.POST, "/cadastrarProduto").hasRole("PRODUTOR")
		.antMatchers(HttpMethod.GET, "/cadastrarUsuario").hasRole("ADMIN")
		.antMatchers(HttpMethod.POST, "/cadastrarUsuario").hasRole("ADMIN")
		.antMatchers(HttpMethod.POST, "atualizar/{codigo}").hasRole("ADMIN")
		.antMatchers(HttpMethod.GET, "/produtos").hasAnyRole("ADMIN, COMPRADOR")
		.antMatchers(HttpMethod.GET, "/usuarios").hasRole("ADMIN")
		.antMatchers(HttpMethod.GET, "/registrarEncomenda").hasRole("COMPRADOR")
		.antMatchers(HttpMethod.GET, "/carrinho").hasRole("COMPRADOR")
		.antMatchers(HttpMethod.GET, "/registrarVenda").hasRole("PRODUTOR")
		.antMatchers(HttpMethod.GET, "/encomendas").hasRole("PRODUTOR")
		.antMatchers(HttpMethod.GET, "/carrinhoVenda").hasRole("PRODUTOR")
		.antMatchers(HttpMethod.GET, "/vendas").hasRole("ADMIN")
		.antMatchers(HttpMethod.GET, "/{codigo}").hasRole("ADMIN")
		.antMatchers(HttpMethod.GET, "/excluirProduto").hasRole("ADMIN")
		.anyRequest().authenticated()
		.and().formLogin().permitAll()
		.and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(userDetailsService)
		.passwordEncoder(new BCryptPasswordEncoder());
	}

	@Override
	public void configure(WebSecurity web) throws Exception{
		web.ignoring().antMatchers("/materialize/**", "/style/**");
	}
}
