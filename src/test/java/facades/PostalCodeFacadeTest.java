package facades;

import entities.CityInfo;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;


public class PostalCodeFacadeTest {

    private static EntityManagerFactory emf;
    private static PostalCodeFacade facade;

    public PostalCodeFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = PostalCodeFacade.getPostalCodeFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Phone.deleteAll").executeUpdate();
            em.createNamedQuery("Person.deleteAllPersons").executeUpdate();
            em.createNamedQuery("Hobby.deleteAll").executeUpdate();
            em.createNamedQuery("Address.deleteAll").executeUpdate();
            em.createNamedQuery("CityInfo.deleteAllRows").executeUpdate();
            em.persist(new CityInfo("3751", "Østermarie"));
            em.persist(new CityInfo("3730", "Nexø"));
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
//        Remove any data after each test was run
    }

    @Test
    public void testGetPostalCodes() {

        Assertions.assertEquals(2, facade.getPostalCodes().size(), "Expects two rows in the database");
    }


}
