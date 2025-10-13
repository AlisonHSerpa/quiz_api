package com.quiz.demo.repository;

import com.quiz.demo.model.Nivel;
import org.springframework.data.jpa.repository.JpaRepository;
import com.quiz.demo.model.Pergunta;

import java.util.List;

public interface PerguntaRepository extends JpaRepository<Pergunta, Long> {
    Pergunta findPerguntaById(long id);

    List<Pergunta> findPerguntaByNivel(Nivel nivel);
}
