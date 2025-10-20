package com.quiz.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Time")
public class Time {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @Column(nullable = false, unique = true)
    private String nome;
    private float pontos;
    private int dicas;

    @OneToMany(mappedBy = "time", fetch = FetchType.EAGER)
    private Set<Jogador> jogadores = new HashSet<>();

    public Long getId() {
        return id;
    }

    public Set<Jogador> getJogadores() {
        return jogadores;
    }

    public void setJogadores(Set<Jogador> jogadores) {
        this.jogadores = jogadores;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public float getPontos() {
        return pontos;
    }

    public void setPontos(float pontos) {
        this.pontos = pontos;
    }

    public int getDicas() {
        return dicas;
    }

    public void setDicas(int dicas) {
        this.dicas = dicas;
    }
}
