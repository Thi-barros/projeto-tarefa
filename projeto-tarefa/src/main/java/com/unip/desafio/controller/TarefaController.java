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
    public String criarTarefa(@ModelAttribute("tarefa") @Valid Tarefa tarefa, BindingResult result, 
    		Model model, @RequestParam("projetoId") Long projetoId) {
    	if (result.hasErrors()) {
            model.addAttribute("projetos", projetoService.listarProjetos());
    		model.addAttribute("tarefa", tarefa);
            return "index"; // Retorna a mesma página com erros
        }
    	Projeto projeto = projetoService.buscarProjeto(projetoId);
        tarefa.setProjeto(projeto);
    	tarefaService.salvarTarefa(tarefa);
        return "redirect:/projetos/index";
    }

    @GetMapping("/{id}")
    public String buscarTarefaPorId(@PathVariable Long id, Model model) {
         Tarefa tarefa = tarefaService.buscarTarefa(id);
         if(tarefa != null) {
        	 model.addAttribute("tarefa", tarefa);
        	 return"projetoDetalhe";
         }else {
        	 model.addAttribute("erro", "tarefa nao encontrada");
        	 return"index";
         }
         
    }

    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<?> atualizarTarefa(@PathVariable Long id, @RequestBody Tarefa tarefaAtualizada) {
        Tarefa tarefa = tarefaService.buscarTarefa(id);
        if (tarefa != null) {
            tarefa.setDescricao(tarefaAtualizada.getDescricao());
            tarefa.setNome(tarefaAtualizada.getNome());
            tarefaService.salvarTarefa(tarefa);
        return ResponseEntity.ok(tarefa);
        }
        return ResponseEntity.status(404).body("tarefa nao encontrada");
    }

    @DeleteMapping("/{id}")
    @Transactional
    public String deletarTarefa(@PathVariable Long id, Model model) {
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
