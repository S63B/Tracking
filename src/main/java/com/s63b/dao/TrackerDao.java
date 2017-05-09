package com.s63b.dao;

import com.S63B.domain.Entities.Tracker;

public class TrackerDao extends BaseDao<Tracker> {
    public Tracker getTracker(int trackerID){
        try{
            return em.createNamedQuery("Tracker.getTracker", Tracker.class).setParameter("trackerID", trackerID).getSingleResult();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
