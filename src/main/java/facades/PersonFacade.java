package facades;

import dtos.HobbyDTO;
import dtos.PersonDTO;
import dtos.PhoneDTO;
import dtos.PostalCodeDTO;
import entities.CityInfo;
import entities.Person;
import errorhandling.PersonNotFoundException;
import mappers.PersonMapper;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class PersonFacade {

    private static PersonFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private PersonFacade() {}


    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static PersonFacade getPersonFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new PersonFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() throws PersonNotFoundException {
        return emf.createEntityManager();
    }

    public PersonDTO create(PersonDTO personDTO) throws PersonNotFoundException{
        return PersonMapper.createPerson(personDTO,emf);
    }

    public static Long getPersonCount(HobbyDTO hobbyDTO) throws PersonNotFoundException {
        return PersonMapper.getPersonCount(hobbyDTO,emf);
    }

    public static List<PersonDTO> getPersonByHobby(HobbyDTO hobbyDTO) throws PersonNotFoundException{
        return PersonMapper.getPersonByHobby(hobbyDTO, emf);
    }

    public static PersonDTO editPerson(PersonDTO personDTO) throws PersonNotFoundException {
        return PersonMapper.editPerson(personDTO, emf);
    }

    public PersonDTO getPersonByNumber(PhoneDTO phoneDTO) throws PersonNotFoundException{
        return PersonMapper.getPersonByNumber(phoneDTO, emf);
    }

    public List<PersonDTO> getAllPersons() throws PersonNotFoundException {
        return PersonMapper.getAllPersons(emf);
    }

    public static List<PersonDTO> getPeopleByPostalCode(PostalCodeDTO postalCode) throws PersonNotFoundException{
        return PersonMapper.getPeopleByPostalCode(postalCode, emf);
    }

}
