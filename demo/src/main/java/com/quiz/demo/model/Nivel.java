package com.quiz.demo.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Nivel {
    DIFICIL("dificil"),
    MEDIO("medio"),
    FACIL("facil");

    private String nivel;
    private Nivel(String nivel) {
        this.nivel = nivel;
    }
@JsonValue
    public String getNivel() {
        return nivel;
    }

    // --- NOVO MÉTODO PARA DESSERIALIZAÇÃO (JSON -> Java) ---
    @JsonCreator
    public static Nivel fromNivel(String nivel) {
        for (Nivel n : Nivel.values()) {
            if (n.nivel.equalsIgnoreCase(nivel)) {
                return n;
            }
        }
        throw new IllegalArgumentException("Nível '" + nivel + "' inválido.");
    }
}
