package com.unip.desafio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.unip.desafio.model.Projeto;
import com.unip.desafio.model.ProjetoService;
import com.unip.desafio.model.Tarefa;
import com.unip.desafio.model.TarefaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import java.text.ParseException;
import java.util.List;

@Controller
@RequestMapping("/tarefas")
public class TarefaController {

	private static final Logger logger = LoggerFactory.getLogger(TarefaController.class);
	
	@Autowired
	private ProjetoService projetoService;
    @Autowired
    private TarefaService tarefaService;

    @GetMapping("/listar")
    public List<Tarefa> listarTarefas() {
        return tarefaService.listarTarefas();
    }

    @PostMapping("/salvar")
    @Transactional
    public String criarTarefa(@RequestParam("projetoId") Long projetoId, 
    		@ModelAttribute("tarefa") @Valid Tarefa tarefa,
    		BindingResult result, Model model)throws ParseException {
    	if (result.hasErrors()) {
    		model.addAttribute("projeto", new Projeto());
            model.addAttribute("projetos", projetoService.listarProjetos());
    		model.addAttribute("tarefa", tarefa);
    		model.addAttribute("tarefas", tarefaService.listarTarefas());   
    		return "index"; // Retorna a mesma página com erros 
        }
    	Projeto projeto = projetoService.buscarProjeto(projetoId);
    	if(projeto != null) {
	    	tarefa.setProjeto(projeto);
	    	tarefaService.salvarTarefa(tarefa);
	    	return"redirect:/projetos/index";
    	}else {
    		System.out.println("Projeto não encontrado para o ID: " + projetoId);
    	}
    	return"redirect:/projetos/index";
    }

    @GetMapping("/buscar/{id}")
    public String buscarTarefaPorId(@PathVariable Long id, Model model) {
         Tarefa tarefa = tarefaService.buscarTarefa(id);
         if(tarefa != null) {
        	 model.addAttribute("tarefa", tarefa);
        	 return"projetoDetalhe";
         }else {
        	 model.addAttribute("erro", "Tarefa nao encontrada");
        	 return"index";
         }
         
    }

    @PostMapping("/atualizar")
    public String atualizarTarefa(@RequestParam("tarefaId") Long id, 
    		@RequestParam("nome") String nome, 
    		@RequestParam("descricao") String descricao) {
        Tarefa tarefa = tarefaService.buscarTarefa(id);
        if (tarefa != null) {
            tarefa.setDescricao(descricao);
            tarefa.setNome(nome);
            tarefaService.salvarTarefa(tarefa);
        }
        return "redirect:/projetos/index";
    }

    @PostMapping("/deletar")
    @Transactional
    public String deletarTarefa(@RequestParam("tarefaId") Long id, Model model) {
        try {
    	tarefaService.deletarTarefa(id);
    	return"redirect:/projetos/index";
    }catch(Exception e){
    	model.addAttribute("erro", "Erro ao deletar tarefa" + e.getMessage());
    	e.printStackTrace();
    	return"error";
    	}
        
    }
}
