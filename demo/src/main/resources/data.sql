-- Inserindo perguntas
INSERT INTO perguntas (id, area_conhecimento, pergunta, resposta, nivel)
VALUES (1, 'Matemática', 'Quanto é 2 + 2?', 0, 'FACIL');

INSERT INTO perguntas (id, area_conhecimento, pergunta, resposta, nivel)
VALUES (2, 'Português', 'Qual é o plural de "pão"?', 1, 'FACIL');

INSERT INTO perguntas (id, area_conhecimento, pergunta, resposta, nivel)
VALUES (3, 'História', 'Quem foi o primeiro presidente do Brasil?', 2, 'MEDIO');

-- Inserindo alternativas (relacionadas pelas foreign keys pergunta_id)
INSERT INTO alternativas (pergunta_id, alternativa)
VALUES (1, '4'),
       (1, '5'),
       (1, '22'),
       (1, '2 + 2');

INSERT INTO alternativas (pergunta_id, alternativa)
VALUES (2, 'Pões'),
       (2, 'Pães'),
       (2, 'Pãos'),
       (2, 'Pãeses');

INSERT INTO alternativas (pergunta_id, alternativa)
VALUES (3, 'Getúlio Vargas'),
       (3, 'Juscelino Kubitschek'),
       (3, 'Marechal Deodoro da Fonseca'),
       (3, 'Dom Pedro II');
