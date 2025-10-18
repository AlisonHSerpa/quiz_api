package com.quiz.demo.model;

import org.springframework.stereotype.Service;
import java.io.InputStream;
import java.net.URLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Base64;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

@Service
public class ImagemService {

    public Imagem processarBase64(Imagem imagem) {
        if (imagem == null || imagem.getUrl() == null || imagem.getUrl().isEmpty()) {
            return imagem;
        }

        try {
            URI uri = new URI(imagem.getUrl());
            URL url = uri.toURL();

            URLConnection connection = url.openConnection();
    
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)"); 
            connection.setConnectTimeout(10000); // 10 segundos para conectar
            connection.setReadTimeout(15000);
            
            
        try (InputStream is = connection.getInputStream()) {
            byte[] bytes = is.readAllBytes();
            String base64 = Base64.getEncoder().encodeToString(bytes);
            imagem.setBase64Data("data:image/jpeg;base64," + base64); 
            }
            
        } catch (URISyntaxException e) {
            System.err.println("Erro de Sintaxe da URI: " + imagem.getUrl() + " - " + e.getMessage());
            imagem.setBase64Data(null);
        } catch (MalformedURLException e) {
            System.err.println("Erro de Formato de URL: " + imagem.getUrl() + " - " + e.getMessage());
            imagem.setBase64Data(null);
        } catch (Exception e) {
            // Captura IOExceptions e outras falhas de download/conversão
            System.err.println("Erro ao processar Base64 para URL: " + imagem.getUrl() + " - " + e.getMessage());
            imagem.setBase64Data(null);
        }

        return imagem;
    }
}