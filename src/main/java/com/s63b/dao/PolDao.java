package com.s63b.dao;

import com.S63B.domain.Entities.Pol;

import java.util.List;

public class PolDao extends BaseDao<Pol> {

    public boolean deletePols(String licensePlate){
        try{
            em.createNamedQuery("Pol.deletePolls", Pol.class).setParameter("licensePlate", licensePlate);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public List<Pol> getPols(String licensePlate){
        try{
            return em.createNamedQuery("Pol.getPolls", Pol.class).setParameter("licensePlate", licensePlate).getResultList();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<Pol> getPolsBetween(String licensePlate, long startDate, long endDate){
        try{
            return em.createNamedQuery("Pol.getPollsBetween", Pol.class).setParameter("licensePlate", licensePlate).setParameter("startDate", startDate).setParameter("endDate", endDate).getResultList();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
     }
}
