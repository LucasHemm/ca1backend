package facades;

import dtos.PostalCodeDTO;
import mappers.PostalCodeMapper;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class PostalCodeFacade {


    private static PostalCodeFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private PostalCodeFacade() {}


    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static PostalCodeFacade getPostalCodeFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new PostalCodeFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public static List<PostalCodeDTO> getPostalCodes(){
        return PostalCodeMapper.getPostalCodes(emf);
    }


}
