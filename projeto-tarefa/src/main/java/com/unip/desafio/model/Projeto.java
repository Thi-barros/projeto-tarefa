package com.unip.desafio.model;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="projetos")
public class Projeto {
@Id
@GeneratedValue(strategy= GenerationType.IDENTITY)
private Long id;
@NotEmpty(message ="O nome do projeto é obrigatório")
@Size(min = 2, max=30, message = "O nome do projeto deve conter entre 2 a 30 caracteres")
private String nome;
@NotEmpty(message ="A descrição do projeto é obrigatório")
private String descricao;
private boolean status;
private LocalDateTime dataCriacao; 
private LocalDateTime dataEntrega;
private LocalDateTime prazo;


@OneToMany(mappedBy= "projeto", cascade = CascadeType.ALL)
private List<Tarefa> tarefas;

public LocalDateTime getPrazo() {
	return prazo;
}
public void setPrazo(LocalDateTime prazo) {
	this.prazo = prazo;
}
public LocalDateTime getDataEntrega() {
	return dataEntrega;
}
public void setDataEntrega(LocalDateTime dataEntrega) {
	this.dataEntrega = dataEntrega;
}

public List<Tarefa> getTarefas() {
	return tarefas;
}
public void setTarefas(List<Tarefa> tarefas) {
	this.tarefas = tarefas;
}
public Long getId() {
	return id;
}
public void setId(Long id) {
	this.id = id;
}
public String getNome() {
	return nome;
}
public void setNome(String nome) {
	this.nome = nome;
}
public String getDescricao() {
	return descricao;
}
public void setDescricao(String descricao) {
	this.descricao = descricao;
}
public boolean isStatus() {
	return status;
}
public void setStatus(boolean status) {
	this.status = status;
}
public LocalDateTime getDataCriacao() {
	return dataCriacao;
}
public void setDataCriacao(LocalDateTime dataCriacao) {
	this.dataCriacao = dataCriacao;
}


}
