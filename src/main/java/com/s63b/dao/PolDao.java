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

    public List getPols(String licensePlate){
        ArrayList<Pol> pols = new ArrayList<Pol>();
        pols.add(new Pol("AAA", 50, 50, System.currentTimeMillis()));
        pols.addAll(em.createNamedQuery("Pol.getPolls").setParameter("licensePlate", licensePlate).getResultList());

//        new JSONObject();

//        pols.add();
        return pols;
    }
}
