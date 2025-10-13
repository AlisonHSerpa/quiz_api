package com.quiz.demo.controller;

import com.quiz.demo.repository.PerguntaRepository;
import com.quiz.demo.repository.TimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class indexController {
    @Autowired
    private TimeRepository timeRepository;

    @Autowired
    private PerguntaRepository perguntaRepository;

    @Autowired
    private PerguntaRepository perguntaRepository2;

    @GetMapping("/")
    public String index() {
        return "index";
    }
}
