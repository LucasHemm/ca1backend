package mappers;

import dtos.HobbyDTO;
import dtos.PersonDTO;
import dtos.PhoneDTO;
import entities.*;
import facades.PersonFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class PersonMapper {
    //Hallo

    private static EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();

    public static PersonDTO createPerson(PersonDTO personDTO,EntityManagerFactory emf){
        EntityManager em = emf.createEntityManager();
        Query query = em.createNamedQuery("CityInfo.findCity");
        query.setParameter("zipCode", personDTO.getAddressDTO().getZipCode());
        CityInfo cityInfo = (CityInfo) query.getSingleResult();

        Address address = new Address(personDTO.getAddressDTO().getStreet(), personDTO.getAddressDTO().getAdditionalInfo(), cityInfo);
        Set<Phone> phones = personDTO.getPhones();
        Person person = new Person(personDTO.getEmail(), personDTO.getFirstName(), personDTO.getLastName(), address,personDTO.getHobbies());

        try {
            TypedQuery<Address> tq = em.createQuery("select a from Address a where a.street = :street AND a.cityInfo = :cityInfo", Address.class);
            tq.setParameter("street", address.getStreet());
            tq.setParameter("cityInfo", address.getCityInfo());
            List<Address> addresses = tq.getResultList();
            if (addresses.size() > 0) {
                address = tq.getSingleResult();
            }
        } finally {
        }
        if (address.getId() == null) {
            try {
                em.getTransaction().begin();
                em.persist(address);
                em.getTransaction().commit();
                System.out.println("ADRESSEN ER NU LAVET");
            } finally {

            }
        }
        person.setAddress(address);
        try {
            em.getTransaction().begin();
            em.persist(person);
            for (Phone phone : phones) {
                phone.setPerson(person);
                em.persist(phone);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }

        return new PersonDTO();
    }


    public static Long getPersonCount(HobbyDTO hobbyDTO, EntityManagerFactory emf){
        EntityManager em = emf.createEntityManager();
        Hobby hobby = em.find(Hobby.class, hobbyDTO.getId());
        try{
            Query query =  em.createQuery("SELECT COUNT(p) FROM Person p JOIN p.hobbies h " +
                    "WHERE h = :hobby");
            query.setParameter("hobby", hobby);
            return (Long)query.getSingleResult();
        }finally{
            em.close();
        }
    }


    public static List<PersonDTO> getPersonByHobby(HobbyDTO hobbyDTO, EntityManagerFactory emf) {
        EntityManager em = emf.createEntityManager();
        String queryString = "SELECT p FROM Person p JOIN p.hobbies h " +
                "WHERE h.name = :hobbyName";
        TypedQuery<Person> query = em.createQuery(queryString, Person.class);
        query.setParameter("hobbyName", hobbyDTO.getName());
        List<Person> resultList = query.getResultList();
        List<PersonDTO> resultDTOList = new ArrayList<>();
        for (Person person : resultList) {
            PersonDTO personDTO = new PersonDTO(person);
            resultDTOList.add(personDTO);
        }
        em.close();
        return resultDTOList;
    }

    public static PersonDTO editPerson(PersonDTO personDTO, EntityManagerFactory emf ){
        EntityManager em = emf.createEntityManager();
        Person person = new Person(personDTO.getId(),personDTO.getEmail(), personDTO.getFirstName(), personDTO.getLastName(),personDTO.getPhones(), personDTO.getAddresses(emf), personDTO.getHobbies());
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
    public static PersonDTO getPersonByNumber(PhoneDTO phoneDTO, EntityManagerFactory emf){
        EntityManager em = emf.createEntityManager();
        TypedQuery<Person> query = em.createQuery("SELECT ph.person FROM Phone ph WHERE ph.number = :number", Person.class);
        query.setParameter("number", phoneDTO.getNumber());
        Person p = query.getSingleResult();
        PersonDTO pDTO = new PersonDTO(p);
        em.close();
        return pDTO;


    }

    public static Set<Person> getAllPersons(EntityManagerFactory emf) {
        EntityManager em = emf.createEntityManager();
        Set<Person> persons = (Set<Person>) em.createNamedQuery("Person.findAll").getResultList().stream().collect(Collectors.toSet());
        return persons;
    }
}
