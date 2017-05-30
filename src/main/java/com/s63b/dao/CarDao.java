package com.s63b.dao;

import com.S63B.domain.Entities.Car;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<Car> getStolenCars(){
        List<Car> cars = new ArrayList<>();
        try {
            Query query = em.createQuery("SELECT c FROM Car c");
            cars =  query.getResultList();
            return cars.stream().filter(car -> car.isStolen() == true)
                    .collect(Collectors.toList());
        }catch(NoResultException e){
            // No result is acceptable, object is null.
            return cars;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
