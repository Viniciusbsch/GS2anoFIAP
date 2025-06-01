-- =============================================
-- TABELAS PRINCIPAIS
-- =============================================

-- Tabela de áreas de risco
CREATE TABLE AREA_RISCO (
    id_area NUMBER PRIMARY KEY,
    nome VARCHAR2(100) NOT NULL,
    nivel_risco NUMBER(1) CHECK (nivel_risco BETWEEN 1 AND 5),
    historico_alteracoes CLOB,
    latitude NUMBER(12,8),
    longitude NUMBER(12,8)
);

-- Tabela de tipos de evento climático
CREATE TABLE TIPO_EVENTO (
    cod_tipo VARCHAR2(30) PRIMARY KEY,
    descricao VARCHAR2(100) NOT NULL
);

-- Tabela associativa entre áreas e tipos de evento
CREATE TABLE AREA_TIPO_EVENTO (
    id_area NUMBER REFERENCES AREA_RISCO(id_area),
    cod_tipo VARCHAR2(30) REFERENCES TIPO_EVENTO(cod_tipo),
    PRIMARY KEY (id_area, cod_tipo)
);

-- Tabela de alertas
CREATE TABLE ALERTA (
    id_alerta NUMBER PRIMARY KEY,
    id_area NUMBER REFERENCES AREA_RISCO(id_area),
    cod_tipo VARCHAR2(30) REFERENCES TIPO_EVENTO(cod_tipo),
    data_hora TIMESTAMP DEFAULT SYSTIMESTAMP NOT NULL,
    severidade VARCHAR2(20) CHECK (severidade IN ('BAIXO','MÉDIO','ALTO','ALTÍSSIMO','CALAMIDADE')),
    descricao CLOB,
    status NUMBER(1) DEFAULT 1,
    orientacao_populacao CLOB,
    CONSTRAINT chk_status CHECK (status IN (0,1))
);

-- Tabela de usuários
CREATE TABLE USUARIO (
    id_usuario NUMBER PRIMARY KEY,
    nome VARCHAR2(100) NOT NULL,
    email VARCHAR2(100),
    telefone VARCHAR2(20),
    latitude NUMBER(12,8),
    longitude NUMBER(12,8),
    notif_email NUMBER(1) DEFAULT 1,
    notif_sms NUMBER(1) DEFAULT 0,
    CONSTRAINT chk_notif_email CHECK (notif_email IN (0,1)),
    CONSTRAINT chk_notif_sms CHECK (notif_sms IN (0,1))
);

-- Tabela associativa usuário-área
CREATE TABLE USUARIO_AREA (
    id_usuario NUMBER REFERENCES USUARIO(id_usuario),
    id_area NUMBER REFERENCES AREA_RISCO(id_area),
    PRIMARY KEY (id_usuario, id_area)
);

-- Tabela de auditoria de alertas
CREATE TABLE AUDITORIA_ALERTA (
    id_auditoria NUMBER PRIMARY KEY,
    id_alerta NUMBER REFERENCES ALERTA(id_alerta),
    acao VARCHAR2(10) NOT NULL,
    data_modificacao TIMESTAMP NOT NULL,
    usuario VARCHAR2(30) NOT NULL
);

-- =============================================
-- OBJETOS DE BANCO (SEQUÊNCIAS, TRIGGERS)
-- =============================================

-- Sequências para IDs automáticos
CREATE SEQUENCE seq_area_risco START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_alerta START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_usuario START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_tipo_evento START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE seq_auditoria START WITH 1 INCREMENT BY 1;

-- Trigger para IDs automáticos
CREATE OR REPLACE TRIGGER trg_area_risco
BEFORE INSERT ON AREA_RISCO
FOR EACH ROW
BEGIN
    SELECT seq_area_risco.NEXTVAL INTO :new.id_area FROM dual;
END;
/

-- Trigger para auditoria de alertas
CREATE OR REPLACE TRIGGER trg_audit_alerta
AFTER INSERT OR UPDATE ON ALERTA
FOR EACH ROW
BEGIN
    INSERT INTO AUDITORIA_ALERTA (
        id_auditoria, 
        id_alerta, 
        acao, 
        data_modificacao, 
        usuario
    ) VALUES (
        seq_auditoria.NEXTVAL,
        :new.id_alerta,
        CASE WHEN INSERTING THEN 'INSERT' ELSE 'UPDATE' END,
        SYSTIMESTAMP,
        USER
    );
END;
/

-- =============================================
-- ÍNDICES PARA PERFORMANCE
-- =============================================

-- Índices para consultas frequentes
CREATE INDEX idx_alerta_area ON ALERTA(id_area);
CREATE INDEX idx_alerta_tipo ON ALERTA(cod_tipo);
CREATE INDEX idx_alerta_data ON ALERTA(data_hora);
CREATE INDEX idx_usuario_area ON USUARIO_AREA(id_usuario);
CREATE INDEX idx_usuario_geo ON USUARIO(latitude, longitude);
CREATE INDEX idx_area_geo ON AREA_RISCO(latitude, longitude);
CREATE INDEX idx_auditoria_alerta ON AUDITORIA_ALERTA(id_alerta); 