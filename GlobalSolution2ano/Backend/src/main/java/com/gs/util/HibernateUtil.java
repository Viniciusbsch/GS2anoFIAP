package com.gs.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HibernateUtil {
    private static final Logger logger = LoggerFactory.getLogger(HibernateUtil.class);
    private static SessionFactory sessionFactory;

    private static SessionFactory buildSessionFactory() {
        try {
            logger.info("Carregando sistema de alertas climaticos...");
            
            // Cria a configuração a partir do arquivo hibernate.cfg.xml
            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml");
            
            // Adiciona as classes de entidade
            configuration.addAnnotatedClass(com.gs.model.Usuario.class);
            configuration.addAnnotatedClass(com.gs.model.AreaRisco.class);
            configuration.addAnnotatedClass(com.gs.model.TipoEvento.class);
            configuration.addAnnotatedClass(com.gs.model.Alerta.class);
            configuration.addAnnotatedClass(com.gs.model.AuditoriaAlerta.class);
            configuration.addAnnotatedClass(com.gs.model.UsuarioArea.class);
            configuration.addAnnotatedClass(com.gs.model.AreaTipoEvento.class);
            
            // Cria e retorna a SessionFactory
            sessionFactory = configuration.buildSessionFactory();
            logger.info("Sistema carregado com sucesso!");
            
            return sessionFactory;
        } catch (Exception ex) {
            logger.error("Erro ao inicializar o sistema. Verifique se o banco de dados está funcionando corretamente.");
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            sessionFactory = buildSessionFactory();
        }
        return sessionFactory;
    }

    public static void shutdown() {
        try {
            if (sessionFactory != null && !sessionFactory.isClosed()) {
                logger.info("Encerrando sistema...");
                sessionFactory.close();
                logger.info("Sistema encerrado com sucesso!");
            }
        } catch (Exception ex) {
            logger.error("Erro ao encerrar sistema.");
            throw new RuntimeException("Erro ao encerrar sistema", ex);
        }
    }
} 