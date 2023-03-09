/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;

/**
 *
 * @author tha
 */
public class Populator {
    public static void populate(){
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        PersonFacade pf = PersonFacade.getPersonFacade(emf);
//        fe.create(new RenameMeDTO(new RenameMe("First 1", "Last 1")));

    }
    
    public static void main(String[] args) {
        populate();
    }
}
