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

	//Todos metodos vão retonar uma lista, para retornarem de forma ordenada o resultado.
	
	public List<Projeto> listarProjetos(){
		//Para a ordenação da lista
		
		return projetoRepository.findAll(Sort.by("nome").ascending());
	}
	public Projeto salvarProjeto(Projeto projeto) {
		if(projeto.getId() == null) {
		projeto.setDataCriacao(LocalDateTime.now());
		projetoRepository.save(projeto);
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
