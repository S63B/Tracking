package com.s63b.dao;

import com.S63B.domain.Entities.Pol;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
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
            e.printStackTrace();
            return false;
        }
    }

    public boolean deletePols(int trackerID){
        try{
            em.createNamedQuery("Pol.deletePolls", Pol.class).setParameter("trackerID", trackerID);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public List<Pol> getPols(int trackerID){
        try{
            return em.createNamedQuery("Pol.getPolls", Pol.class).setParameter("trackerID", trackerID).getResultList();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<Pol> getPolsBetween(int trackerID, long startDate, long endDate){
        try{
            return em.createNamedQuery("Pol.getPollsBetween", Pol.class).setParameter("trackerID", trackerID).setParameter("startDate", startDate).setParameter("endDate", endDate).getResultList();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
     }
}
