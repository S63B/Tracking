package com.s63b.dao;

import com.S63B.domain.Entities.Car;
import com.S63B.domain.Entities.Tracker;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;

public class CarDao extends BaseDao<Car> {

    public Car getCar(String licensePlate){
        try {
            return em.createNamedQuery("Car.getCar", Car.class).setParameter("licensePlate", licensePlate).getSingleResult();
        }catch(NoResultException e){
            // No result is acceptable, object is null.
            return null;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
