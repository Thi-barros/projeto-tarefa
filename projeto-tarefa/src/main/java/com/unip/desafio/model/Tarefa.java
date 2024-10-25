package com.unip.desafio.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Tarefa {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	//Falta a lógica de tarefa(parecida com a de projeto), relacionamentos e validação!
	private String nome;
	private String descricao;
}
