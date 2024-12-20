package com.unip.desafio.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unip.desafio.model.DB.TarefaRepository;

@Service
public class TarefaService {

	@Autowired
	private TarefaRepository tarefaRepository;
	
	public List<Tarefa> listarTarefas(){
		return tarefaRepository.findAll();
	}
	
	public Tarefa salvarTarefa(Tarefa tarefa) { 
		return tarefaRepository.save(tarefa);
	} 
	
	public Tarefa buscarTarefa(Long id) {
		return tarefaRepository.findById(id).orElse(null);
	}
	 
	public void deletarTarefa(Long id) {
		tarefaRepository.deleteById(id);
	}
}
