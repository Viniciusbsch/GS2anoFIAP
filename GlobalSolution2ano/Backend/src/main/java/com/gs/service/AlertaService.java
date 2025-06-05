package com.gs.service;

import com.gs.exception.BusinessException;
import com.gs.model.Alerta;
import com.gs.model.AreaRisco;
import com.gs.model.TipoEvento;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import com.gs.util.HibernateUtil;

import java.time.LocalDateTime;
import java.util.List;

public class AlertaService {
    
    public Alerta criarAlerta(Long idArea, String tipoEvento, String descricao, int nivelSeveridade) {
        EntityManager em = HibernateUtil.getSessionFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        
        try {
            // Buscar área e tipo de evento
            AreaRisco area = em.find(AreaRisco.class, idArea);
            if (area == null) {
                throw new BusinessException("Área não encontrada");
            }

            TypedQuery<TipoEvento> query = em.createQuery(
                "SELECT t FROM TipoEvento t WHERE t.descricao = :descricao", TipoEvento.class);
            query.setParameter("descricao", tipoEvento);
            TipoEvento tipo = query.getSingleResult();

            // Converter nível de severidade para enum
            Alerta.Severidade severidade;
            switch (nivelSeveridade) {
                case 1 -> severidade = Alerta.Severidade.BAIXO;
                case 2 -> severidade = Alerta.Severidade.MÉDIO;
                case 3 -> severidade = Alerta.Severidade.ALTO;
                case 4 -> severidade = Alerta.Severidade.ALTÍSSIMO;
                case 5 -> severidade = Alerta.Severidade.CALAMIDADE;
                default -> throw new BusinessException("Nível de severidade inválido");
            }

            // Criar orientação padrão baseada na severidade
            String orientacao = "Orientações para " + tipoEvento + " de nível " + nivelSeveridade;

            Alerta alerta = new Alerta(area, tipo, LocalDateTime.now(), severidade, descricao, orientacao);
            
            tx.begin();
            em.persist(alerta);
            tx.commit();
            
            return alerta;
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw new BusinessException("Erro ao criar alerta", e);
        } finally {
            em.close();
        }
    }
    
    public Alerta criarAlerta(AreaRisco area, TipoEvento tipo, Alerta.Severidade severidade, 
                            String descricao, String orientacao) {
        EntityManager em = HibernateUtil.getSessionFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        
        try {
            Alerta alerta = new Alerta(area, tipo, LocalDateTime.now(), severidade, descricao, orientacao);
            
            tx.begin();
            em.persist(alerta);
            tx.commit();
            
            return alerta;
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw new BusinessException("Erro ao criar alerta", e);
        } finally {
            em.close();
        }
    }
    
    public List<Alerta> listarAlertas() {
        EntityManager em = HibernateUtil.getSessionFactory().createEntityManager();
        try {
            TypedQuery<Alerta> query = em.createQuery("SELECT a FROM Alerta a", Alerta.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    public List<Alerta> listarAlertasAtivos() {
        EntityManager em = HibernateUtil.getSessionFactory().createEntityManager();
        try {
            TypedQuery<Alerta> query = em.createQuery(
                "SELECT a FROM Alerta a WHERE a.status = true ORDER BY a.dataHora DESC", 
                Alerta.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    public Alerta atualizarAlerta(Long id, Alerta.Severidade severidade, 
                                String descricao, String orientacao) {
        EntityManager em = HibernateUtil.getSessionFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        
        try {
            Alerta alerta = em.find(Alerta.class, id);
            if (alerta == null) {
                throw new BusinessException("Alerta não encontrado");
            }
            
            alerta.setSeveridade(severidade);
            alerta.setDescricao(descricao);
            alerta.setOrientacaoPopulacao(orientacao);
            
            tx.begin();
            em.merge(alerta);
            tx.commit();
            
            return alerta;
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw new BusinessException("Erro ao atualizar alerta", e);
        } finally {
            em.close();
        }
    }
    
    public void desativarAlerta(Long id) {
        EntityManager em = HibernateUtil.getSessionFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        
        try {
            Alerta alerta = em.find(Alerta.class, id);
            if (alerta == null) {
                throw new BusinessException("Alerta não encontrado");
            }
            
            alerta.setStatus(false);
            
            tx.begin();
            em.merge(alerta);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw new BusinessException("Erro ao desativar alerta", e);
        } finally {
            em.close();
        }
    }
} 