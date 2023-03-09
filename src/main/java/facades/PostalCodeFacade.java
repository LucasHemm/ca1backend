package facades;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

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



}
