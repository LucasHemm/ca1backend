package facades;

import dtos.HobbyDTO;
import dtos.PersonDTO;
import dtos.PhoneDTO;
import entities.Person;
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

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public PersonDTO create(PersonDTO personDTO){
        return PersonMapper.createPerson(personDTO,emf);
    }

    public static Long getPersonCount(HobbyDTO hobbyDTO) {
        return PersonMapper.getPersonCount(hobbyDTO,emf);
    }

    public static List<PersonDTO> getPersonByHobby(HobbyDTO hobbyDTO) {
        return PersonMapper.getPersonByHobby(hobbyDTO, emf);
    }

    public static PersonDTO editPerson(PersonDTO personDTO) {
        return PersonMapper.editPerson(personDTO, emf);
    }
    public PersonDTO getPersonByNumber(PhoneDTO phoneDTO){
        return PersonMapper.getPersonByNumber(phoneDTO, emf);
    }

    public Set<Person> getAllPersons() {
        return PersonMapper.getAllPersons(emf);


    }
}
