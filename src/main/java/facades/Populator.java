/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dtos.HobbyDTO;
import dtos.PersonDTO;
import entities.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author tha
 */
public class Populator {
    public static void populate(){
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        EntityManager em = emf.createEntityManager();
        PersonFacade pf = PersonFacade.getPersonFacade(emf);
        Query query = em.createNamedQuery("CityInfo.findCity");
        query.setParameter("zipCode", "3751");
        CityInfo cityInfo = (CityInfo) query.getSingleResult();
        Address address = new Address("Lærkevej 4", "no additional info", cityInfo);
        Phone phone = new Phone("1212121212", "no additional info");
        Set<Phone> phones = new HashSet<>();
        phones.add(phone);
        Hobby hobby1 = em.find(Hobby.class, 14);
        Hobby hobby2 = em.find(Hobby.class, 42);
        System.out.println(hobby1.getId() + " HERE IS the ID");
        Set<Hobby> hobbies = new HashSet<>();
        hobbies.add(hobby1);
        hobbies.add(hobby2);


        pf.create(new PersonDTO(new Person("Mace@mail","mace","Windu",phones,address,hobbies)));

    }
    
    public static void main(String[] args) {
        populate();
    }
}
