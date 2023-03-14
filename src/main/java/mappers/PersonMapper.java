package mappers;

import dtos.HobbyDTO;
import dtos.PersonDTO;
import dtos.PhoneDTO;
import dtos.PostalCodeDTO;
import entities.*;
import errorhandling.ExceptionDTO;
import errorhandling.PersonNotFoundException;
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

    public static PersonDTO createPerson(PersonDTO personDTO, EntityManagerFactory emf) throws PersonNotFoundException {
        Person personToReturn = new Person();
        EntityManager em = emf.createEntityManager();
        Query query = em.createNamedQuery("CityInfo.findCity");
        query.setParameter("zipCode", personDTO.getAddressDTO().getZipCode());
        CityInfo cityInfo = (CityInfo) query.getSingleResult();

        Address address = new Address(personDTO.getAddressDTO().getStreet(), personDTO.getAddressDTO().getAdditionalInfo(), cityInfo);
        Set<Phone> phones = personDTO.getPhones();
        Person person = new Person(personDTO.getEmail(), personDTO.getFirstName(), personDTO.getLastName(), address, personDTO.getHobbies());

        try {
            TypedQuery<Address> tq = em.createQuery("select a from Address a where a.street = :street AND a.cityInfo = :cityInfo", Address.class);
            tq.setParameter("street", address.getStreet());
            tq.setParameter("cityInfo", address.getCityInfo());
            List<Address> addresses = tq.getResultList();
            if (addresses.size() > 0) {
                address = tq.getSingleResult();
            }
        } catch (Exception e) {
            throw new PersonNotFoundException("Address not found");
        } finally {
        }
        if (address.getId() == null) {
            try {
                em.getTransaction().begin();
                em.persist(address);
                em.getTransaction().commit();
                System.out.println("ADRESSEN ER NU LAVET");
            } catch (Exception e) {
                throw new PersonNotFoundException("Could not inset address");
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
        } catch (Exception e) {
            throw new PersonNotFoundException("Could not insert person");
        } finally {
            em.close();
        }
        System.out.println(person.getFirstName() + "NAME " + person.getId());
        PersonDTO personDTO1 = new PersonDTO(person);
        personDTO1.setId(person.getId());


        return personDTO1;
    }


    public static Long getPersonCount(HobbyDTO hobbyDTO, EntityManagerFactory emf) throws PersonNotFoundException {
        EntityManager em = emf.createEntityManager();
        Hobby hobby = new Hobby();
        try {
            Query query = em.createNamedQuery("Hobby.findHobbyByName");
            query.setParameter("name", hobbyDTO.getName());
            hobby = (Hobby) query.getSingleResult();
        } catch (Exception e) {
            throw new PersonNotFoundException("Hobby not found");
        }

//        Hobby hobby = em.find(Hobby.class, hobbyDTO.getId());
        try {
            Query query1 = em.createQuery("SELECT COUNT(p) FROM Person p JOIN p.hobbies h " +
                    "WHERE h = :hobby");
            query1.setParameter("hobby", hobby);
            Long res = (Long) query1.getSingleResult();
            System.out.println(res + "res");
            return res;
        } catch (Exception e) {
            throw new PersonNotFoundException("Could not get count");
        } finally {
            em.close();
        }
    }


    public static List<PersonDTO> getPersonByHobby(HobbyDTO hobbyDTO, EntityManagerFactory emf) throws PersonNotFoundException {
        EntityManager em = emf.createEntityManager();
        List<PersonDTO> resultDTOList = new ArrayList<>();
        try {
            String queryString = "SELECT p FROM Person p JOIN p.hobbies h " +
                    "WHERE h.name = :hobbyName";
            TypedQuery<Person> query = em.createQuery(queryString, Person.class);
            query.setParameter("hobbyName", hobbyDTO.getName());
            List<Person> resultList = query.getResultList();

            for (Person person : resultList) {
                PersonDTO personDTO = new PersonDTO(person);
                personDTO.setId(person.getId());
                resultDTOList.add(personDTO);
            }
        } catch (Exception e) {
            throw new PersonNotFoundException("Could not get person by hobby");
        } finally {
            em.close();
        }

        return resultDTOList;
    }

    public static PersonDTO editPerson(PersonDTO personDTO, EntityManagerFactory emf) throws PersonNotFoundException {
        EntityManager em = emf.createEntityManager();
        Query query = em.createNamedQuery("Person.findById");
        query.setParameter("id", personDTO.getId());
        Person person = (Person) query.getSingleResult();

        try {
            em.getTransaction().begin();
            person.setFirstName(personDTO.getFirstName());
            person.setLastName(personDTO.getLastName());
            person.setEmail(personDTO.getEmail());
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new PersonNotFoundException("Could not edit person");

        } finally {
            em.close();
        }
        return new PersonDTO(person);
    }

    public static PersonDTO getPersonByNumber(PhoneDTO phoneDTO, EntityManagerFactory emf) throws PersonNotFoundException {
        Person p;
        PersonDTO pDTO;
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Person> query = em.createQuery("SELECT ph.person FROM Phone ph WHERE ph.number = :number", Person.class);
            query.setParameter("number", phoneDTO.getNumber());
            p = query.getSingleResult();
            System.out.println(p.getFirstName() + "this is PERSON P");
            pDTO = new PersonDTO(p);
            pDTO.setId(p.getId());
        } catch (Exception e) {
            throw new PersonNotFoundException("Could not get person by number");
        } finally {
            em.close();
        }
        return pDTO;


    }

    public static List<PersonDTO> getAllPersons(EntityManagerFactory emf) throws PersonNotFoundException {
        EntityManager em = emf.createEntityManager();
        List<PersonDTO> personDTOList = new ArrayList<>();

        try {
            List<Person> personList = em.createNamedQuery("Person.findAll").getResultList();
            for (Person person : personList) {
                PersonDTO personDTO = new PersonDTO(person);
                personDTO.setId(person.getId());
                personDTOList.add(personDTO);
            }
        } catch (Exception e) {
            throw new PersonNotFoundException("Could not get all persons");
        } finally {
            em.close();
        }
        return personDTOList;
    }


    public static List<PersonDTO> getPeopleByPostalCode(PostalCodeDTO postalCode, EntityManagerFactory emf) throws PersonNotFoundException {
        EntityManager em = emf.createEntityManager();
        ArrayList<PersonDTO> personDTOS = new ArrayList<>();

        try {
            String queryString = "SELECT p FROM Person p JOIN p.address a JOIN a.cityInfo c WHERE c.zipCode = :postalCode";
            TypedQuery query = em.createQuery(queryString, Person.class);
            query.setParameter("postalCode", postalCode.getZipCode());
            List<Person> resultList = query.getResultList();
            for (Person person : resultList) {
                PersonDTO personDTO = new PersonDTO(person);
                personDTO.setId(person.getId());
                personDTOS.add(personDTO);
            }
        } catch (Exception e) {
            throw new PersonNotFoundException("Could not get people by postal code");
        } finally {
            em.close();
        }
        return personDTOS;
    }

    public static List<HobbyDTO> getAllHobbies(EntityManagerFactory emf) throws PersonNotFoundException {
        EntityManager em = emf.createEntityManager();
        ArrayList<HobbyDTO> hobbyDTOS = new ArrayList();
        try {
            Query query = em.createNamedQuery("Hobby.findAll");
            List<Hobby> rs = query.getResultList();
            for (Hobby h : rs) {
                HobbyDTO hDTO = new HobbyDTO(h);
                hobbyDTOS.add(hDTO);
            }
        } catch (Exception e) {
            throw new PersonNotFoundException("Could not get postal codes");
        } finally {
            em.close();
        }
        return hobbyDTOS;
    }

}



