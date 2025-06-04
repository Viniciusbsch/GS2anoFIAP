package com.gs.dao;

import com.gs.model.Usuario;
import com.gs.util.HibernateUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class UsuarioDao {

    public Usuario findById(Long id) {
        EntityManager em = HibernateUtil.getSessionFactory().createEntityManager();
        try {
            TypedQuery<Usuario> query = em.createQuery("SELECT u FROM Usuario u LEFT JOIN FETCH u.areas WHERE u.id = :id", Usuario.class);
            query.setParameter("id", id);
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }
} 