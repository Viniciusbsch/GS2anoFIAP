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