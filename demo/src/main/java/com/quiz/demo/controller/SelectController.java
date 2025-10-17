package com.quiz.demo.controller;

import com.quiz.demo.model.Nivel;
import com.quiz.demo.model.Pergunta;
import com.quiz.demo.repository.PerguntaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/select")
public class SelectController {

    @Autowired
    private PerguntaRepository perguntaRepository;

    private final Random random = new Random();

    // Puxar pergunta aleatória e remover do BD
    @GetMapping("/random")
    public ResponseEntity<Pergunta> puxarPerguntaAleatoria() {
        List<Pergunta> perguntas = perguntaRepository.findAll();
        if (perguntas.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Pergunta pergunta = perguntas.get(random.nextInt(perguntas.size()));
        perguntaRepository.delete(pergunta);
        return ResponseEntity.ok(pergunta);
    }

    // Puxar pergunta difícil e remover do BD
    @GetMapping("/dificil")
    public ResponseEntity<Pergunta> puxarPerguntaDificil() {
        List<Pergunta> perguntas = perguntaRepository.findAll()
                .stream()
                .filter(p -> p.getNivel() == Nivel.DIFICIL)
                .toList();

        if (perguntas.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Pergunta pergunta = perguntas.get(random.nextInt(perguntas.size()));
        perguntaRepository.delete(pergunta);
        return ResponseEntity.ok(pergunta);
    }

    // Puxar pergunta fácil e remover do BD
    @GetMapping("/facil")
    public ResponseEntity<Pergunta> puxarPerguntaFacil() {
        List<Pergunta> perguntas = perguntaRepository.findAll()
                .stream()
                .filter(p -> p.getNivel() == Nivel.FACIL)
                .toList();

        if (perguntas.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Pergunta pergunta = perguntas.get(random.nextInt(perguntas.size()));
        perguntaRepository.delete(pergunta);
        return ResponseEntity.ok(pergunta);
    }
}
