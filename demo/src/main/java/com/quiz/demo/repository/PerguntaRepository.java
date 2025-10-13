package com.quiz.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.quiz.demo.model.Pergunta;

public interface PerguntaRepository extends JpaRepository<Pergunta, Long> {
    Pergunta findPerguntaById(long id);
}
