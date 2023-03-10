package mappers;

import dtos.PersonDTO;
import entities.Address;
import entities.CityInfo;
import entities.Person;
import entities.Phone;
import facades.PersonFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Set;

public class PersonMapper {
    //Hallo

    private static EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();

    public static PersonDTO createPerson(PersonDTO personDTO,EntityManagerFactory emf){
        EntityManager em = emf.createEntityManager();
        Query query = em.createNamedQuery("CityInfo.findCityInfo");
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

}
