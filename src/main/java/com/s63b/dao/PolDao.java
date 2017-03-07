package com.s63b.dao;

import com.s63b.domain.Pol;

import javax.persistence.EntityManager;
import javax.persistence.NamedQuery;
import javax.persistence.Persistence;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

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

    public List<Pol> getPols(String licensePlate){
        try{
            return em.createNamedQuery("Pol.getPolls", Pol.class).setParameter("licensePlate", licensePlate).getResultList();
        }catch (Exception e){
            return null;
        }
    }

    public List<Pol> getPolsBetween(String licensePlate, long startDate, long endDate){
        try{
            return em.createNamedQuery("Pol.getPollsBetween", Pol.class).setParameter("licensePlate", licensePlate).setParameter("startDate", startDate).setParameter("endDate", endDate).getResultList();
        }catch (Exception e){
            return null;
        }
     }
}
