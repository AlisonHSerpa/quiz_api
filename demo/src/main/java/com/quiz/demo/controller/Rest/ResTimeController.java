package com.quiz.demo.controller.Rest;

import com.quiz.demo.model.Time;
import com.quiz.demo.repository.TimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rest/time")
public class ResTimeController {

    @Autowired
    private TimeRepository timeRepository;

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
