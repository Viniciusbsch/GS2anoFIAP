-- =============================================
-- DADOS INICIAIS (TESTE)
-- =============================================

-- Tipos de eventos climáticos
INSERT INTO TIPO_EVENTO (cod_tipo, descricao) VALUES ('TEMPESTADE', 'Tempestade com ventos acima de 80km/h');
INSERT INTO TIPO_EVENTO (cod_tipo, descricao) VALUES ('INUNDACAO', 'Alagamento por chuvas intensas');
INSERT INTO TIPO_EVENTO (cod_tipo, descricao) VALUES ('CALOR_EXTREMO', 'Temperatura acima de 40°C por 3+ horas');
INSERT INTO TIPO_EVENTO (cod_tipo, descricao) VALUES ('GEADA', 'Temperatura abaixo de 0°C com formação de gelo');

-- Áreas de risco
INSERT INTO AREA_RISCO (id_area, nome, nivel_risco, latitude, longitude) 
VALUES (seq_area_risco.NEXTVAL, 'Vila São José', 3, -23.5506507, -46.6333824);

INSERT INTO AREA_RISCO (id_area, nome, nivel_risco, latitude, longitude) 
VALUES (seq_area_risco.NEXTVAL, 'Morro do Alemão', 4, -22.8862471, -43.2730483);

-- Relacionamento área-tipo evento
INSERT INTO AREA_TIPO_EVENTO VALUES (1, 'TEMPESTADE');
INSERT INTO AREA_TIPO_EVENTO VALUES (1, 'INUNDACAO');
INSERT INTO AREA_TIPO_EVENTO VALUES (2, 'INUNDACAO');

-- Usuários
INSERT INTO USUARIO (id_usuario, nome, email, telefone, latitude, longitude, notif_email, notif_sms)
VALUES (seq_usuario.NEXTVAL, 'João Silva', 'joao@email.com', '11999998888', -23.5505199, -46.6333094, 1, 1);

INSERT INTO USUARIO (id_usuario, nome, email, telefone, latitude, longitude, notif_email, notif_sms)
VALUES (seq_usuario.NEXTVAL, 'Maria Souza', 'maria@email.com', '21988887777', -22.8861234, -43.2729876, 1, 0);

-- Relacionamento usuário-área
INSERT INTO USUARIO_AREA VALUES (1, 1);
INSERT INTO USUARIO_AREA VALUES (2, 2);

-- Alertas de teste
INSERT INTO ALERTA (id_alerta, id_area, cod_tipo, data_hora, severidade, descricao, status)
VALUES (seq_alerta.NEXTVAL, 1, 'TEMPESTADE', SYSTIMESTAMP, 'ALTO', 'Tempestade com ventos de 90km/h prevista para 1h', 1);

INSERT INTO ALERTA (id_alerta, id_area, cod_tipo, data_hora, severidade, descricao, status)
VALUES (seq_alerta.NEXTVAL, 2, 'INUNDACAO', SYSTIMESTAMP, 'ALTÍSSIMO', 'Nível do rio subiu 2m acima do normal', 1);

COMMIT;

-- =============================================
-- CONSULTAS ÚTEIS
-- =============================================

-- 1. Consultar todas as áreas de risco e seus tipos de eventos
SELECT 
    ar.nome as area_risco,
    ar.nivel_risco,
    LISTAGG(te.descricao, ', ') WITHIN GROUP (ORDER BY te.descricao) as tipos_evento
FROM AREA_RISCO ar
LEFT JOIN AREA_TIPO_EVENTO ate ON ar.id_area = ate.id_area
LEFT JOIN TIPO_EVENTO te ON ate.cod_tipo = te.cod_tipo
GROUP BY ar.nome, ar.nivel_risco;

-- 2. Consultar usuários e suas áreas monitoradas
SELECT 
    u.nome as usuario,
    u.email,
    u.telefone,
    LISTAGG(ar.nome, ', ') WITHIN GROUP (ORDER BY ar.nome) as areas_monitoradas
FROM USUARIO u
LEFT JOIN USUARIO_AREA ua ON u.id_usuario = ua.id_usuario
LEFT JOIN AREA_RISCO ar ON ua.id_area = ar.id_area
GROUP BY u.nome, u.email, u.telefone;

-- 3. Consultar alertas ativos por área
SELECT 
    ar.nome as area_risco,
    te.descricao as tipo_evento,
    a.severidade,
    a.data_hora,
    a.descricao
FROM ALERTA a
JOIN AREA_RISCO ar ON a.id_area = ar.id_area
JOIN TIPO_EVENTO te ON a.cod_tipo = te.cod_tipo
WHERE a.status = 1
ORDER BY a.data_hora DESC;

-- 4. Consultar histórico de auditoria de alertas
SELECT 
    a.id_alerta,
    ar.nome as area_risco,
    aa.acao,
    aa.data_modificacao,
    aa.usuario
FROM AUDITORIA_ALERTA aa
JOIN ALERTA a ON aa.id_alerta = a.id_alerta
JOIN AREA_RISCO ar ON a.id_area = ar.id_area
ORDER BY aa.data_modificacao DESC;

-- =============================================
-- ATUALIZAÇÕES ÚTEIS
-- =============================================

-- 1. Atualizar nível de risco de uma área
UPDATE AREA_RISCO 
SET nivel_risco = 5,
    historico_alteracoes = historico_alteracoes || CHR(10) || 
                          'Nível de risco atualizado para 5 em ' || SYSTIMESTAMP
WHERE nome = 'Morro do Alemão';

-- 2. Atualizar preferências de notificação de um usuário
UPDATE USUARIO 
SET notif_sms = 1,
    telefone = '21999998888'
WHERE email = 'maria@email.com';

-- 3. Desativar alerta antigo
UPDATE ALERTA 
SET status = 0
WHERE data_hora < SYSTIMESTAMP - INTERVAL '24' HOUR
AND status = 1;

-- 4. Adicionar orientação à população em alerta
UPDATE ALERTA 
SET orientacao_populacao = 'Evitar sair de casa. Manter-se em local seguro e elevado.'
WHERE id_alerta = 2;

COMMIT; 