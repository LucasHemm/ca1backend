package mappers;

import dtos.PersonDTO;
import entities.Address;
import entities.CityInfo;
import entities.Person;
import entities.Phone;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PersonMapper {


    public static PersonDTO createPerson(PersonDTO personDTO){
        CityInfo cityInfo = new CityInfo(personDTO.getAddressDTO().getZipCode(), personDTO.getAddressDTO().getCity());
        Address address = new Address(personDTO.getAddressDTO().getStreet(), personDTO.getAddressDTO().getAdditionalInfo(), cityInfo);
        Set<Phone> phones = personDTO.getPhones();
        Person person = new Person(personDTO.getEmail(), personDTO.getFirstName(), personDTO.getLastName(), phones, address);
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(person);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new PersonDTO();
    }


    public static int getPersonCount(EntityManagerFactory emf){
        EntityManager em = emf.createEntityManager();
        try{
            return (int)em.createQuery("SELECT COUNT(p) FROM Person p JOIN p.hobbies h " +
                    "WHERE h.name = :hobby").getSingleResult();
        }finally{
            em.close();
        }
    }


    public static List<PersonDTO> findUsersByHobby(String hobbyName, EntityManagerFactory emf) {
        EntityManager em = emf.createEntityManager();
        String queryString = "SELECT p FROM Person p JOIN p.hobbies h " +
                "WHERE h.name = :hobbyName";
        TypedQuery<Person> query = em.createQuery(queryString, Person.class);
        query.setParameter("hobbyName", hobbyName);
        List<Person> resultList = query.getResultList();
        List<PersonDTO> resultDTOList = new ArrayList<>();
        for (Person person : resultList) {
            PersonDTO personDTO = new PersonDTO(person);
            resultDTOList.add(personDTO);
        }
        return resultDTOList;
    }

    public static PersonDTO editPerson(PersonDTO personDTO, EntityManagerFactory emf ){
        EntityManager em = emf.createEntityManager();
        Person person = em.find(Person.class, personDTO.getId());
        try {
            em.getTransaction().begin();
            person.setFirstName(personDTO.getFirstName());
            person.setLastName(personDTO.getLastName());
            person.setEmail(personDTO.getEmail());
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new PersonDTO(person);
    }

}
