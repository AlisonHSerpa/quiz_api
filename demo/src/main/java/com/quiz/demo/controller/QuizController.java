package com.quiz.demo.controller;

import com.quiz.demo.model.Nivel;
import com.quiz.demo.model.Pergunta;
import com.quiz.demo.model.Time;
import com.quiz.demo.model.TimeDTO;
import com.quiz.demo.repository.PerguntaRepository;
import com.quiz.demo.repository.TimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping("/quiz")
public class QuizController {
    @Autowired
    private TimeRepository timeRepository;

    // adicionarPontuacao() { vai adicionar pontos ao time }
    @PostMapping("/acertou/{id}")
    public ResponseEntity<Time> pontosParaOTime(@PathVariable int id, @RequestBody TimeDTO data) {
        Optional<Time> optionalTime = Optional.ofNullable(timeRepository.findById(id));

        if (optionalTime.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Time time = optionalTime.get();
        time.setPontos(time.getPontos() + data.pontos());
        time.setDicas(time.getDicas() - data.dicasUsadas());
        timeRepository.save(time);

        return ResponseEntity.ok(time);
    }

    // mostrarVencedor() { vai enviar o time com maior pontuacao }
    @GetMapping("/winner")
    public ResponseEntity<?> winner() {
        List<Time> times = timeRepository.findAll();

        float maiorPontuacao = -1;
        Time winner = null;
        for (Time time : times) {
            if (time.getPontos() > maiorPontuacao) {
                winner = time;
            }
            else if (time.getPontos() == maiorPontuacao) {
                winner = null;
            }
            maiorPontuacao = time.getPontos();
        }

        if (winner == null) {
            return ResponseEntity.ok("empate");
        }
        else {
            return ResponseEntity.ok(winner);
        }
    }

}
