-- =============================================
-- REMOÇÃO DE OBJETOS DO BANCO DE DADOS
-- =============================================

SET SERVEROUTPUT ON;
BEGIN
    DBMS_OUTPUT.PUT_LINE('Iniciando remoção dos objetos do banco de dados...');
END;
/

-- Remover triggers
BEGIN
    EXECUTE IMMEDIATE 'DROP TRIGGER trg_area_risco';
    DBMS_OUTPUT.PUT_LINE('Trigger trg_area_risco removida com sucesso.');
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Trigger trg_area_risco não encontrada ou já removida.');
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP TRIGGER trg_audit_alerta';
    DBMS_OUTPUT.PUT_LINE('Trigger trg_audit_alerta removida com sucesso.');
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Trigger trg_audit_alerta não encontrada ou já removida.');
END;
/

-- Remover sequências
BEGIN
    EXECUTE IMMEDIATE 'DROP SEQUENCE seq_area_risco';
    DBMS_OUTPUT.PUT_LINE('Sequência seq_area_risco removida com sucesso.');
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Sequência seq_area_risco não encontrada ou já removida.');
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP SEQUENCE seq_alerta';
    DBMS_OUTPUT.PUT_LINE('Sequência seq_alerta removida com sucesso.');
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Sequência seq_alerta não encontrada ou já removida.');
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP SEQUENCE seq_usuario';
    DBMS_OUTPUT.PUT_LINE('Sequência seq_usuario removida com sucesso.');
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Sequência seq_usuario não encontrada ou já removida.');
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP SEQUENCE seq_tipo_evento';
    DBMS_OUTPUT.PUT_LINE('Sequência seq_tipo_evento removida com sucesso.');
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Sequência seq_tipo_evento não encontrada ou já removida.');
END;
/

-- Remover índices
BEGIN
    EXECUTE IMMEDIATE 'DROP INDEX idx_alerta_area';
    DBMS_OUTPUT.PUT_LINE('Índice idx_alerta_area removido com sucesso.');
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Índice idx_alerta_area não encontrado ou já removido.');
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP INDEX idx_alerta_tipo';
    DBMS_OUTPUT.PUT_LINE('Índice idx_alerta_tipo removido com sucesso.');
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Índice idx_alerta_tipo não encontrado ou já removido.');
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP INDEX idx_alerta_data';
    DBMS_OUTPUT.PUT_LINE('Índice idx_alerta_data removido com sucesso.');
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Índice idx_alerta_data não encontrado ou já removido.');
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP INDEX idx_usuario_area';
    DBMS_OUTPUT.PUT_LINE('Índice idx_usuario_area removido com sucesso.');
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Índice idx_usuario_area não encontrado ou já removido.');
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP INDEX idx_usuario_geo';
    DBMS_OUTPUT.PUT_LINE('Índice idx_usuario_geo removido com sucesso.');
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Índice idx_usuario_geo não encontrado ou já removido.');
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP INDEX idx_area_geo';
    DBMS_OUTPUT.PUT_LINE('Índice idx_area_geo removido com sucesso.');
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Índice idx_area_geo não encontrado ou já removido.');
END;
/

-- Remover tabelas (na ordem correta devido às dependências)
BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE USUARIO_AREA';
    DBMS_OUTPUT.PUT_LINE('Tabela USUARIO_AREA removida com sucesso.');
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Tabela USUARIO_AREA não encontrada ou já removida.');
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE USUARIO';
    DBMS_OUTPUT.PUT_LINE('Tabela USUARIO removida com sucesso.');
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Tabela USUARIO não encontrada ou já removida.');
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE ALERTA';
    DBMS_OUTPUT.PUT_LINE('Tabela ALERTA removida com sucesso.');
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Tabela ALERTA não encontrada ou já removida.');
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE AREA_TIPO_EVENTO';
    DBMS_OUTPUT.PUT_LINE('Tabela AREA_TIPO_EVENTO removida com sucesso.');
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Tabela AREA_TIPO_EVENTO não encontrada ou já removida.');
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE AREA_RISCO';
    DBMS_OUTPUT.PUT_LINE('Tabela AREA_RISCO removida com sucesso.');
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Tabela AREA_RISCO não encontrada ou já removida.');
END;
/

BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE TIPO_EVENTO';
    DBMS_OUTPUT.PUT_LINE('Tabela TIPO_EVENTO removida com sucesso.');
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Tabela TIPO_EVENTO não encontrada ou já removida.');
END;
/

-- Remover tabela de auditoria (se existir)
BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE AUDITORIA_ALERTA';
    DBMS_OUTPUT.PUT_LINE('Tabela AUDITORIA_ALERTA removida com sucesso.');
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Tabela AUDITORIA_ALERTA não encontrada ou já removida.');
END;
/

COMMIT;

BEGIN
    DBMS_OUTPUT.PUT_LINE('Processo de remoção concluído com sucesso!');
END;
/ 