package com.quiz.demo.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "perguntas")
public class Pergunta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String area_conhecimento;
    private String pergunta;

    @ElementCollection
    @CollectionTable(name = "alternativas", joinColumns = @JoinColumn(name = "pergunta_id"))
    @Column(name = "alternativa")
    private List<String> alternativas;

    private int resposta;
    private String nivel;
}
