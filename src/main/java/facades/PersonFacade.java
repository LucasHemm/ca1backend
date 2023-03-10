package facades;

import dtos.PersonDTO;
import mappers.PersonMapper;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

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

    public static int getPersonCount() {
        return PersonMapper.getPersonCount(emf);
    }

    public static List<PersonDTO> findUsersByHobby(String hobby) {
        return PersonMapper.findUsersByHobby(hobby, emf);
    }

    public static PersonDTO editPerson(PersonDTO personDTO) {
        return PersonMapper.editPerson(personDTO, emf);
    }

}
