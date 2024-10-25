package com.unip.desafio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unip.desafio.model.Projeto;
import com.unip.desafio.model.ProjetoService;

@RestController
@RequestMapping("/projetos")
public class ProjetoController {

	@Autowired
	private ProjetoService projetoService; 
	
	@PostMapping
	List<Projeto> salvarProjetoList(@RequestBody Projeto projeto){
		return projetoService.salvarProjeto(projeto);
	}
	
	
	@GetMapping
	List<Projeto> listarProjetos(){
		return projetoService.listarProjetos();
	}
	
	
	List<Projeto> buscarProjeto(Long id){
		return projetoService.buscarProjeto(id);
	}
	
	@DeleteMapping("{id}")
	List<Projeto> deletarProjeto(@PathVariable Long id){
		return projetoService.deletarProjeto(id);
	}
}
