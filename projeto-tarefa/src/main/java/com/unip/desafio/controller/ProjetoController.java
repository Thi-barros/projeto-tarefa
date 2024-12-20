package com.unip.desafio.controller;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.unip.desafio.model.Projeto;
import com.unip.desafio.model.ProjetoService;
import com.unip.desafio.model.Tarefa;
import com.unip.desafio.model.TarefaService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;


@Controller
@RequestMapping("/projetos")
public class ProjetoController {

	@Autowired
	private ProjetoService projetoService; 
	@Autowired
	private TarefaService tarefaService;
	
	@GetMapping("/index")
	public String mostrarIndex(Model model) {
		model.addAttribute("projeto", new Projeto());
		model.addAttribute("projetos", projetoService.listarProjetos());
		model.addAttribute("tarefa", new Tarefa());
		model.addAttribute("tarefas", tarefaService.listarTarefas());		
		return "index"; 
	} 
	
	@PostMapping("/salvar")
	@Transactional
	String salvarProjeto(@Valid @ModelAttribute("projeto") Projeto projeto, BindingResult result, Model model) throws ParseException {
		if(result.hasErrors()) {
			model.addAttribute("projetos", projetoService.listarProjetos());
	        model.addAttribute("tarefa", new Tarefa());
	        model.addAttribute("listaTarefas", tarefaService.listarTarefas());
	        return "index"; 
		} 
		projetoService.salvarProjeto(projeto);
		projeto.setPrazo(projeto.getDataCriacao().plusDays(15));
		return "redirect:/projetos/listar";  
	}
	
	@PostMapping("/atualizar")
	public String atualizarProjeto(@RequestParam("projetoId") Long id,
			@RequestParam("nome") String nome, @RequestParam("descricao") String descricao){
		Projeto projetoExistente = (Projeto) projetoService.buscarProjeto(id);
		if(projetoExistente != null) {
			projetoExistente.setNome(nome);
			projetoExistente.setDescricao(descricao);
			projetoService.salvarProjeto(projetoExistente);
		} 
		return "redirect:/projetos/index";
	} 
	@PostMapping("/concluir/{id}")
	public String concluirProjeto (@PathVariable Long id){
		Projeto projeto = projetoService.buscarProjeto(id);
		if(projeto != null) {
			projeto.setStatus(true);
			projeto.setDataEntrega(LocalDateTime.now());
			projetoService.salvarProjeto(projeto);
			return"redirect:/projetos/index";
		}
			return"redirect:/error"; 
		}
	 
	@GetMapping("/listar")
	public ModelAndView listarProjetos(){
		ModelAndView mav = new ModelAndView("index");
		mav.addObject("projetos", projetoService.listarProjetos());
		mav.addObject("projeto", new Projeto());
		mav.addObject("tarefa", new Tarefa());
		mav.addObject("listaTarefas", tarefaService.listarTarefas());
		return mav; 
	}
	
	@GetMapping("/todos")
	public String listarTodosProjetos(Model model) {
		model.addAttribute("projetos", projetoService.listarProjetos()); 
		model.addAttribute("listaTarefas", tarefaService.listarTarefas());
		return"listarProjetos";
		}
	
	@GetMapping("/buscar/{id}")
	public String buscarProjeto(@PathVariable Long id, Model model){
		Projeto projeto = projetoService.buscarProjeto(id);
		if(projeto!=null) {
			model.addAttribute("projeto", projeto);
			return "projetoDetalhe";
		}else {
			model.addAttribute("erro", "Projeto não encontrado");
			return"index";
		}
	}
	
	@PostMapping("/deletar")
	@Transactional
	public String deletarProjeto(@RequestParam("projetoId") Long id, Model model){
		try {
            projetoService.deletarProjeto(id);
            return "redirect:/projetos/listar";
        } catch (Exception e) {
            model.addAttribute("erro", "Erro ao deletar projeto: " + e.getMessage());
            e.printStackTrace();
            return "error";
        }
    }
	}
