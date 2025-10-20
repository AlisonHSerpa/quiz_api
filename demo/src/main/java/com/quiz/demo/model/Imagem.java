package com.quiz.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "imagens")
public class Imagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url; 
    
    @Transient 
    private String base64Data; 

    @OneToOne(mappedBy = "imagem")
    private Pergunta pergunta; 
    

    public Imagem() {}

 
    public Long getId() { 
        return id;
     }
    public void setId(Long id) { 
        this.id = id;
    }

    public String getUrl() { 
        return url; 
    }
    public void setUrl(String url) { 
        this.url = url; 
    }

    public String getBase64Data() { 
        return base64Data; 
    }
    public void setBase64Data(String base64Data) { 
        this.base64Data = base64Data; 
    }

}