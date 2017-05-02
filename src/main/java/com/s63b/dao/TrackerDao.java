package com.s63b.dao;

import com.S63B.domain.Entities.Pol;
import com.S63B.domain.Entities.Tracker;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.List;

public class TrackerDao {
    private EntityManager em;

    public TrackerDao() {
        em = Persistence.createEntityManagerFactory("HibernatePersistenceUnit").createEntityManager();
    }

    public boolean createTracker(Tracker tracker){
        try{
            em.getTransaction().begin();;
            em.persist(tracker);
            em.getTransaction().commit();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public Tracker getTracker(int trackerID){
        try{
            return em.createNamedQuery("Tracker.getTracker", Tracker.class).setParameter("trackerID", trackerID).getSingleResult();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
