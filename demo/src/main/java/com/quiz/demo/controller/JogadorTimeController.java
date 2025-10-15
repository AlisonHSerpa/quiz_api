package com.quiz.demo.controller;

import com.quiz.demo.model.Jogador;
import com.quiz.demo.model.Time;
import com.quiz.demo.repository.JogadorRepository;
import com.quiz.demo.repository.TimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/jogador/time")
public class JogadorTimeController {

    @Autowired
    private JogadorRepository jogadorRepository;

    @Autowired
    private TimeRepository timeRepository;

    // Adiciona um jogador a um time
    @PostMapping("/add")
    public ResponseEntity<Jogador> adicionaJogador(
            @RequestParam Long jogadorId,
            @RequestParam Long timeId) {

        Optional<Jogador> jogadorOpt = jogadorRepository.findById(jogadorId);
        Optional<Time> timeOpt = timeRepository.findById(timeId);

        if (jogadorOpt.isEmpty() || timeOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Jogador jogador = jogadorOpt.get();
        Time time = timeOpt.get();

        jogador.setTime(time);
        jogadorRepository.save(jogador);

        return ResponseEntity.ok(jogador);
    }

    // Remove um jogador de um time
    @DeleteMapping("/remove")
    public ResponseEntity<Jogador> removeJogador(
            @RequestParam Long jogadorId) {

        Optional<Jogador> jogadorOpt = jogadorRepository.findById(jogadorId);

        if (jogadorOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Jogador jogador = jogadorOpt.get();
        jogador.setTime(null);
        jogadorRepository.save(jogador);

        return ResponseEntity.ok(jogador);
    }
}