package com.s63b;

import org.springframework.web.bind.annotation.RequestMapping;
<<<<<<< HEAD
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

    public JPATest(){
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
=======
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by bramd on 21-2-2017.
 */
@RestController
public class PolController {

    @RequestMapping("/pol")
    public Result pol(@RequestParam(value="id", defaultValue="id") String id,
                         @RequestParam(value="lat", defaultValue="lat") String lat,
                         @RequestParam(value="lng", defaultValue="lng") String lng) {

        return new Result(true, id + lat + lng, 0, "Hastikke mooi");
>>>>>>> ae2a361478416de017a67c2bc3c736e2efc2a219
    }
}
