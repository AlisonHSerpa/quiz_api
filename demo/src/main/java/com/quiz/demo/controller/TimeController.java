package com.quiz.demo.controller;

import com.quiz.demo.repository.TimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/time")
public class TimeController {

    @Autowired
    private TimeRepository timeRepository;

    // criarTime() { cria um novo time }
    @PostMapping("/create")
    public String createTime() {
        return "index";
    }

    // removeTime() { encontra e remove um time }
    @DeleteMapping("/delete/{id}")
    public String deleteTime(@PathVariable long id) {
        return "index";
    }

    @PutMapping("/edit/{id}")
    public String editTime(@PathVariable long id) {
        return "index";
    }

    @GetMapping("/{id}")
    public String getTime(@PathVariable long id, Model model) {
        model.addAttribute("time", timeRepository.findById(id));
        return "placeholder";
    }

}
