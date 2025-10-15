package com.quiz.demo.controller;

import com.quiz.demo.repository.TimeRepository;
import com.quiz.demo.model.Time;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/time")
public class TimeController {

    @Autowired
    private TimeRepository timeRepository;

    // Criar um novo time
    @PostMapping("/create")
    public ResponseEntity<Time> createTime(@RequestBody Time time) {
        if (time.getId() != null && timeRepository.existsById(time.getId())) {
            return ResponseEntity.badRequest().build();
        }
        Time savedTime = timeRepository.save(time);
        return ResponseEntity.ok(savedTime);
    }

    // Buscar um time por ID
    @GetMapping("/{id}")
    public ResponseEntity<Time> getTime(@PathVariable long id) {
        Optional<Time> time = Optional.ofNullable(timeRepository.findById(id));
        return time.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Editar um time existente
    @PutMapping("/edit/{id}")
    public ResponseEntity<Time> editTime(@PathVariable long id, @RequestBody Time updatedTime) {
        Optional<Time> existingTime = Optional.ofNullable(timeRepository.findById(id));

        if (existingTime.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Time time = existingTime.get();
        time.setNome(updatedTime.getNome());
        time.setPontos(updatedTime.getPontos());
        time.setJogadores(updatedTime.getJogadores());

        Time saved = timeRepository.save(time);
        return ResponseEntity.ok(saved);
    }

    // Remover um time
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTime(@PathVariable long id) {
        if (!timeRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        timeRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // mostrarVencedor() { vai enviar o time com maior pontuacao }
    @GetMapping("/winner")
    public ResponseEntity<Time> winner() {
        List<Time> times = timeRepository.findAll();

        int maiorPontuacao = -1;
        Time winner = null;
        for (Time time : times) {
            if (time.getPontos() > maiorPontuacao) {
                winner = time;
            }
            maiorPontuacao = time.getPontos();
        }

        if (winner == null) {
            return ResponseEntity.notFound().build();
        }
        else {
            return ResponseEntity.ok(winner);
        }
    }

    // adicionarPontuacao() { vai adicionar pontos ao time }
    @PostMapping("/{id}")
    public ResponseEntity<Time> pontosParaOTime(@PathVariable int id, @RequestBody int points) {
        Optional<Time> optionalTime = Optional.ofNullable(timeRepository.findById(id));

        if (optionalTime.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Time time = optionalTime.get();
        time.setPontos(time.getPontos() + points);
        timeRepository.save(time);

        return ResponseEntity.ok(time);
    }
}
