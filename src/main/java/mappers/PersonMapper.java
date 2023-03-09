package mappers;

import dtos.PersonDTO;
import entities.Address;
import entities.CityInfo;
import entities.Person;
import entities.Phone;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Set;

public class PersonMapper {

    private static EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();

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

}
