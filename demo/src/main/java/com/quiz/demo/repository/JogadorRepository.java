package com.quiz.demo.repository;

import com.quiz.demo.model.Jogador;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JogadorRepository extends JpaRepository<Jogador, Long> {
    Jogador findJogadorById(long id);
}
