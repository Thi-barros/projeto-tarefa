package com.unip.desafio.model.DB;
import org.springframework.data.jpa.repository.JpaRepository;
import com.unip.desafio.model.Tarefa;

public interface TarefaRepository extends JpaRepository<Tarefa, Long>{
}