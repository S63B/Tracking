package com.s63b.dao;

import com.s63b.domain.Pol;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by bramd on 7-3-2017.
 */
public class PolDao {
    private EntityManager em;

    public PolDao() {
        em = Persistence.createEntityManagerFactory("HibernatePersistenceUnit").createEntityManager();
    }

    public boolean addPol(Pol pol){
        try{
            em.getTransaction().begin();
            em.persist(pol);
            em.getTransaction().commit();
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
