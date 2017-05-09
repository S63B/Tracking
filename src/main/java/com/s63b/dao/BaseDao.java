package com.s63b.dao;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class BaseDao<T> {
    protected EntityManager em;

    public BaseDao() {
        em = Persistence.createEntityManagerFactory("HibernatePersistenceUnit").createEntityManager();
    }

    public boolean create(T object){
        try{
            em.getTransaction().begin();
            em.persist(object);
            em.getTransaction().commit();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean save(T object){
        try{
            em.getTransaction().begin();
            em.merge(object);
            em.getTransaction().commit();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
