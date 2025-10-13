package com.quiz.demo.controller;

import com.quiz.demo.model.Jogador;
import com.quiz.demo.model.Pergunta;
import com.quiz.demo.repository.JogadorRepository;
import com.quiz.demo.repository.PerguntaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/pergunta")
public class PerguntaController {

    @Autowired
    private PerguntaRepository perguntaRepository;

    // criarPergunta() { cria uma nova pergunta e adiciona no bd }
    @PostMapping("/create")
    public String createPergunta() {
        return "index";
    }

    // removePergunta() { acha e retira uma pergunta do bd }
    @DeleteMapping("/delete/{id}")
    public String deletePergunta(@PathVariable long id) {
        return "index";
    }

    @PutMapping("/edit/{id}")
    public String editPergunta(@PathVariable long id) {
        return "index";
    }

    @GetMapping("/{id}")
    public String showPergunta(@PathVariable long id, Model model) {
        Pergunta pergunta = perguntaRepository.findPerguntaById(id);
        model.addAttribute("pergunta", pergunta);
        return "placeholder";
    }
}
