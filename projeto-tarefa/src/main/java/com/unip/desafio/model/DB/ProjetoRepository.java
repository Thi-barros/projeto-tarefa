package com.unip.desafio.model.DB;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unip.desafio.model.Projeto;

public interface ProjetoRepository extends JpaRepository<Projeto, Long> {

}
