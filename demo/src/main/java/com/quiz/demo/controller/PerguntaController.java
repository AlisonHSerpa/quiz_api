package com.quiz.demo.controller;

import com.quiz.demo.model.Nivel;
import com.quiz.demo.model.Pergunta;
import com.quiz.demo.repository.PerguntaRepository;
import com.quiz.demo.model.ImagemService; // NOVO IMPORT
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pergunta")
public class PerguntaController {

    @Autowired
    private PerguntaRepository perguntaRepository;

    @Autowired
    private ImagemService imagemService; // INJEÇÃO DO SERVIÇO DE IMAGEM

    private final Random random = new Random();

    // ==========================================================
    // MÉTODOS AUXILIARES PARA PROCESSAMENTO DO BASE64
    // ==========================================================
    
    // Processa o Base64 para uma única pergunta
    private Pergunta processarBase64(Pergunta pergunta) {
        if (pergunta != null && pergunta.getImagem() != null) {
            // O serviço baixa a imagem do URL e preenche o campo Base64Data
            imagemService.processarBase64(pergunta.getImagem());
        }
        return pergunta;
    }
    
    // Processa o Base64 para uma lista de perguntas
    private List<Pergunta> processarListaBase64(List<Pergunta> perguntas) {
        return perguntas.stream()
                .map(this::processarBase64)
                .collect(Collectors.toList());
    }
    
    // ==========================================================
    // ENDPOINTS
    // ==========================================================

    // Criar pergunta
    @PostMapping("/create")
    public ResponseEntity<Pergunta> createPergunta(@RequestBody Pergunta pergunta) {
        // O Spring/JPA salva o objeto Pergunta e o objeto Imagem aninhado.
        Pergunta perguntaSaved = perguntaRepository.save(pergunta);
        
        // Retorna a pergunta processada com Base64 para confirmação
        return ResponseEntity.ok(processarBase64(perguntaSaved));
    }

    // Buscar uma pergunta por ID
    @GetMapping("/{id}")
    public ResponseEntity<Pergunta> showPergunta(@PathVariable long id) {
        Optional<Pergunta> pergunta = perguntaRepository.findById(id);
        
        return pergunta.map(p -> {
            processarBase64(p); // Processa Base64 antes de retornar
            return ResponseEntity.ok(p);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Editar uma pergunta existente
    @PutMapping("/edit/{id}")
    public ResponseEntity<Pergunta> editPergunta(@PathVariable long id, @RequestBody Pergunta updatedPergunta) {
        Optional<Pergunta> existingPergunta = perguntaRepository.findById(id);

        if (existingPergunta.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Pergunta pergunta = existingPergunta.get();
        
        // 1. Atualiza os campos básicos
        pergunta.setArea_conhecimento(updatedPergunta.getArea_conhecimento());
        pergunta.setPergunta(updatedPergunta.getPergunta());
        pergunta.setAlternativas(updatedPergunta.getAlternativas());
        pergunta.setResposta(updatedPergunta.getResposta());
        pergunta.setNivel(updatedPergunta.getNivel());
        
        // 2. Atualiza o objeto Imagem (a lógica do @OneToOne fará a mágica)
        // Se 'updatedPergunta' tiver um objeto Imagem, ele substituirá o antigo.
        if (updatedPergunta.getImagem() != null) {
            pergunta.setImagem(updatedPergunta.getImagem());
        }

        Pergunta saved = perguntaRepository.save(pergunta);
        
        // Retorna a pergunta atualizada e processada
        processarBase64(saved);
        return ResponseEntity.ok(saved);
    }

    // Remover pergunta por ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePergunta(@PathVariable long id) {
        if (!perguntaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        // O CascadeType.ALL em Pergunta.java deletará a Imagem associada
        perguntaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Puxar pergunta aleatória e remover do BD
    @GetMapping("/random")
    public ResponseEntity<Pergunta> puxarPerguntaAleatoria() {
        List<Pergunta> perguntas = perguntaRepository.findAll();
        if (perguntas.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Pergunta pergunta = perguntas.get(random.nextInt(perguntas.size()));
        perguntaRepository.delete(pergunta);
        
        processarBase64(pergunta); // Processa Base64 antes de retornar
        return ResponseEntity.ok(pergunta);
    }

    // Puxar pergunta difícil e remover do BD
    @GetMapping("/dificil")
    public ResponseEntity<Pergunta> puxarPerguntaDificil() {
        List<Pergunta> perguntas = perguntaRepository.findAll().stream()
                .filter(p -> p.getNivel() == Nivel.DIFICIL)
                .toList();

        if (perguntas.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Pergunta pergunta = perguntas.get(random.nextInt(perguntas.size()));
        perguntaRepository.delete(pergunta);
        
        processarBase64(pergunta); // Processa Base64 antes de retornar
        return ResponseEntity.ok(pergunta);
    }

    // Puxar pergunta fácil e remover do BD
    @GetMapping("/facil")
    public ResponseEntity<Pergunta> puxarPerguntaFacil() {
        List<Pergunta> perguntas = perguntaRepository.findAll().stream()
                .filter(p -> p.getNivel() == Nivel.FACIL)
                .toList();

        if (perguntas.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Pergunta pergunta = perguntas.get(random.nextInt(perguntas.size()));
        perguntaRepository.delete(pergunta);
        
        processarBase64(pergunta); // Processa Base64 antes de retornar
        return ResponseEntity.ok(pergunta);
    }
}