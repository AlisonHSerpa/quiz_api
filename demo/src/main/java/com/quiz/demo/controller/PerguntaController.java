package com.quiz.demo.controller;

import com.quiz.demo.model.Pergunta;
import com.quiz.demo.repository.PerguntaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping("/pergunta")
public class PerguntaController {

    @Autowired
    private PerguntaRepository perguntaRepository;

    // Criar pergunta
    @PostMapping("/create")
    public ResponseEntity<Pergunta> createPergunta(@RequestBody Pergunta pergunta) {
        Pergunta perguntaSaved = perguntaRepository.save(pergunta);
        return ResponseEntity.ok(perguntaSaved);
    }

    // Buscar uma pergunta por ID
    @GetMapping("/{id}")
    public ResponseEntity<Pergunta> showPergunta(@PathVariable long id) {
        Optional<Pergunta> pergunta = perguntaRepository.findById(id);
        return pergunta.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Editar uma pergunta existente
    @PutMapping("/edit/{id}")
    public ResponseEntity<Pergunta> editPergunta(@PathVariable long id, @RequestBody Pergunta updatedPergunta) {
        Optional<Pergunta> existingPergunta = perguntaRepository.findById(id);

        if (existingPergunta.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Pergunta pergunta = existingPergunta.get();
        pergunta.setArea_conhecimento(updatedPergunta.getArea_conhecimento());
        pergunta.setPergunta(updatedPergunta.getPergunta());
        pergunta.setAlternativas(updatedPergunta.getAlternativas());
        pergunta.setResposta(updatedPergunta.getResposta());
        pergunta.setNivel(updatedPergunta.getNivel());

        Pergunta saved = perguntaRepository.save(pergunta);
        return ResponseEntity.ok(saved);
    }

    // Remover pergunta por ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePergunta(@PathVariable long id) {
        if (!perguntaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        perguntaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
