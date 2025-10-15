package com.quiz.demo.model;

public enum Nivel {
    DIFICIL("dificil"),
    FACIL("facil");

    private String nivel;
    private Nivel(String nivel) {
        this.nivel = nivel;
    }

    public String getNivel() {
        return nivel;
    }
}
