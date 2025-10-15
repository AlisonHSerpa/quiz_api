package com.quiz.demo.controller;

import com.quiz.demo.model.Jogador;
import com.quiz.demo.repository.JogadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/jogador")
public class JogadorController {

    @Autowired
    private JogadorRepository jogadorRepository;

    // Criar um novo jogador
    @PostMapping("/create")
    public ResponseEntity<Jogador> criarJogador(@RequestBody Jogador jogador) {
        if (jogadorRepository.existsById(jogador.getId())) {
            return ResponseEntity.badRequest().build(); // evita sobrescrever
        }
        Jogador novoJogador = jogadorRepository.save(jogador);
        return ResponseEntity.ok(novoJogador);
    }

    // Buscar jogador por ID
    @GetMapping("/{id}")
    public ResponseEntity<Jogador> findJogadorById(@PathVariable long id) {
        Optional<Jogador> jogador = jogadorRepository.findById(id);
        return jogador.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Editar jogador (sem mudar o time aqui)
    @PutMapping("/edit/{id}")
    public ResponseEntity<Jogador> editJogador(@PathVariable long id, @RequestBody Jogador updatedJogador) {
        Optional<Jogador> optionalJogador = jogadorRepository.findById(id);

        if (optionalJogador.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Jogador jogador = optionalJogador.get();
        jogador.setNome(updatedJogador.getNome());
        jogador.setEspecialidade(updatedJogador.getEspecialidade());
        // jogador.setTime(...) ← intencionalmente não alterado aqui

        Jogador saved = jogadorRepository.save(jogador);
        return ResponseEntity.ok(saved);
    }

    // Remover jogador
    @DeleteMapping("/remove/{id}")
    public ResponseEntity<Void> removeJogador(@PathVariable long id) {
        if (!jogadorRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        jogadorRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
