-- Cria a tabela se ela ainda não existir.
CREATE TABLE IF NOT EXISTS PERGUNTA (
                                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                        texto_pergunta VARCHAR(500) NOT NULL,
    categoria VARCHAR(100)
    );

-- Limpa a tabela antes de inserir novos dados.sere algumas perguntas de teste para validar o processo.
DELETE FROM PERGUNTA;

-- In
INSERT INTO PERGUNTA (texto_pergunta, categoria) VALUES ('Qual é a capital da França?', 'Geografia');
INSERT INTO PERGUNTA (texto_pergunta, categoria) VALUES ('O que significa a sigla "SQL"?', 'Tecnologia');
INSERT INTO PERGUNTA (texto_pergunta, categoria) VALUES ('Este é um teste para o carregamento automático do H2?', 'Teste');

