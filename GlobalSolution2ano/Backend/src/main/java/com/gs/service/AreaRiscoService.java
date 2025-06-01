package com.gs.service;

import com.gs.exception.BusinessException;
import com.gs.model.AreaRisco;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import com.gs.util.HibernateUtil;

import java.util.List;

public class AreaRiscoService {
    
    public AreaRisco cadastrarArea(String nome, int nivelRisco, double latitude, double longitude) {
        EntityManager em = HibernateUtil.getSessionFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        
        try {
            AreaRisco area = new AreaRisco();
            area.setNome(nome);
            area.setNivelRisco(nivelRisco);
            area.setLatitude(latitude);
            area.setLongitude(longitude);
            
            tx.begin();
            em.persist(area);
            tx.commit();
            
            return area;
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw new BusinessException("Erro ao cadastrar área", e);
        } finally {
            em.close();
        }
    }
    
    public List<AreaRisco> listarAreas() {
        EntityManager em = HibernateUtil.getSessionFactory().createEntityManager();
        try {
            TypedQuery<AreaRisco> query = em.createQuery("SELECT a FROM AreaRisco a", AreaRisco.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    public List<AreaRisco> listarAreasPorRisco(int nivelRisco) {
        EntityManager em = HibernateUtil.getSessionFactory().createEntityManager();
        try {
            TypedQuery<AreaRisco> query = em.createQuery(
                "SELECT a FROM AreaRisco a WHERE a.nivelRisco = :nivel", AreaRisco.class);
            query.setParameter("nivel", nivelRisco);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    public AreaRisco atualizarArea(Long id, String nome, int nivelRisco, 
                                 double latitude, double longitude) {
        EntityManager em = HibernateUtil.getSessionFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        
        try {
            AreaRisco area = em.find(AreaRisco.class, id);
            if (area == null) {
                throw new BusinessException("Área não encontrada");
            }
            
            area.setNome(nome);
            area.setNivelRisco(nivelRisco);
            area.setLatitude(latitude);
            area.setLongitude(longitude);
            
            tx.begin();
            em.merge(area);
            tx.commit();
            
            return area;
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw new BusinessException("Erro ao atualizar área", e);
        } finally {
            em.close();
        }
    }
    
    public void removerArea(Long id) {
        EntityManager em = HibernateUtil.getSessionFactory().createEntityManager();
        EntityTransaction tx = em.getTransaction();
        
        try {
            AreaRisco area = em.find(AreaRisco.class, id);
            if (area == null) {
                throw new BusinessException("Área não encontrada");
            }
            
            tx.begin();
            em.remove(area);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw new BusinessException("Erro ao remover área", e);
        } finally {
            em.close();
        }
    }
} 