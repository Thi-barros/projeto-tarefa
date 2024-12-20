package com.unip.desafio.model;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.unip.desafio.model.DB.ProjetoRepository;
 
@Service
public class ProjetoService {
	@Autowired
	private ProjetoRepository projetoRepository;
	private Projeto projeto;

	
	public List<Projeto> listarProjetos(){
		//Para a ordenação da lista
		
		return projetoRepository.findAll(Sort.by("nome").ascending());
	}
	public Projeto salvarProjeto(Projeto projeto) {
		if(projeto.getId() == null) {
		projeto.setDataCriacao(LocalDateTime.now());
		}
		return projetoRepository.save(projeto);
	}
	public Projeto buscarProjeto(Long id) {
		return projetoRepository.findById(id).orElse(null);
		}
	public void deletarProjeto(Long id) {
		projetoRepository.deleteById(id);
	}
}
