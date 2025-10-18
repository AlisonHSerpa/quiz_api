package com.quiz.demo.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "perguntas")
public class Pergunta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String area_conhecimento;
    private String pergunta;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "imagem_id", referencedColumnName = "id")
    private Imagem imagem;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "alternativas", joinColumns = @JoinColumn(name = "pergunta_id"))
    @Column(name = "alternativa")
    private List<String> alternativas;

    private int resposta;
    private Nivel nivel;

    public Imagem getImagem() {
        return imagem;
    }

    public void setImagem(Imagem imagem) {
        this.imagem = imagem;
    }

    public List<String> getAlternativas() {
        return alternativas;
    }


    public void setAlternativas(List<String> alternativas) {
        this.alternativas = alternativas;
    }

    public String getArea_conhecimento() {
        return area_conhecimento;
    }

    public void setArea_conhecimento(String area_conhecimento) {
        this.area_conhecimento = area_conhecimento;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getResposta() {
        return resposta;
    }

    public void setResposta(int resposta) {
        this.resposta = resposta;
    }

    public String getPergunta() {
        return pergunta;
    }

    public void setPergunta(String pergunta) {
        this.pergunta = pergunta;
    }

    public Nivel getNivel() {
        return nivel;
    }

    public void setNivel(Nivel nivel) {
        this.nivel = nivel;
    }
}
