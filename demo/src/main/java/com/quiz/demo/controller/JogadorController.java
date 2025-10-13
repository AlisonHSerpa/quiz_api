package com.quiz.demo.controller;

import com.quiz.demo.model.Jogador;
import com.quiz.demo.repository.JogadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/jogador")
public class JogadorController {

    @Autowired
    private JogadorRepository jogadorRepository;

    // cadastrarJogador() { cira um novo jogador }
    @PostMapping("/create")
    public String criarJogador() {
        return "index";
    }

    // removerJogador() { encontra e remove um jogador }
    @DeleteMapping("/remove/{id}")
    public String removeJogador(@PathVariable long id) {
        return "index";
    }

    @PutMapping("/edit/{id}")
    public String editJogador(@PathVariable long id) {
        return "index";
    }

    @GetMapping("/{id}")
    public String findJogadorById(@PathVariable long id, Model model) {
        Jogador jogador = jogadorRepository.findJogadorById(id);
        model.addAttribute("jogador", jogador);
        return "placeholder";
    }
}
