package com.s63b.controllers;

import com.s63b.domain.Car;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import java.util.List;

@RestController
public class JPATest {

    private static EntityManagerFactory factory;
    private static EntityManager em;

    public JPATest() {
        factory = Persistence.createEntityManagerFactory("HibernatePersistenceUnit");
        em = factory.createEntityManager();

        em.getTransaction().begin();
        Car car = new Car();
        car.setLicensePlate("ASDF");

        em.persist(car);
        em.getTransaction().commit();
    }

    @RequestMapping("/jpa")
    public String init() {
        Query q = em.createQuery("SELECT c FROM Car c");
        List<Car> carList = q.getResultList();

        String s = "";

        for (Car car : carList) {
            s += " [" + car.getLicensePlate() + "] ";
        }

        s += " -- Size: " + carList.size();

        return s;
    }
}