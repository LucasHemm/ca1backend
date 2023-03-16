//package facades;
//
//import dtos.HobbyDTO;
//import dtos.PersonDTO;
//import dtos.PhoneDTO;
//import entities.*;
//import errorhandling.PersonNotFoundException;
//import org.junit.jupiter.api.*;
//import utils.EMF_Creator;
//
//import javax.persistence.EntityManager;
//import javax.persistence.EntityManagerFactory;
//import javax.persistence.Query;
//import java.util.HashSet;
//import java.util.Set;
//
//public class PersonFacadeTest {
//
//    private static EntityManagerFactory emf;
//    private static PersonFacade facade;
//    Hobby hobby1;
//    Phone phone1;
//    Person person;
//    PersonDTO persistedPersonDTO;
//
//    @BeforeAll
//    public static void setUpClass() {
//        emf = EMF_Creator.createEntityManagerFactoryForTest();
//        facade = PersonFacade.getPersonFacade(emf);
//    }
//
//    @AfterAll
//    public static void tearDownClass() {
////        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
//    }
//
//    @BeforeEach
//    public void setUp() throws PersonNotFoundException {
//        EntityManager em = emf.createEntityManager();
//        try {
//            em.getTransaction().begin();
//            em.createNamedQuery("Phone.deleteAll").executeUpdate();
//            em.createNamedQuery("Person.deleteAllPersons").executeUpdate();
//            em.createNamedQuery("Hobby.deleteAll").executeUpdate();
//            em.createNamedQuery("Address.deleteAll").executeUpdate();
//            em.createNamedQuery("CityInfo.deleteAllRows").executeUpdate();
//
//
//            CityInfo cityInfo1 = new CityInfo("3751", "Østermarie");
//            CityInfo cityInfo2 = new CityInfo("3730", "Nexø");
//            em.persist(cityInfo1);
//            em.persist(cityInfo2);
//            em.getTransaction().commit();
//            em.getTransaction().begin();
//
//            Address address1 = new Address("Lærkevej 4", "no additional info", cityInfo1);
//            Address address2 = new Address("Lyngby hovedgade 27", "no additional info", cityInfo2);
//
//            em.persist(address1);
//            em.persist(address2);
//            em.getTransaction().commit();
//            em.getTransaction().begin();
//            hobby1 = new Hobby("Programming", "LINKYLINK", "Generel", "Indendørs");
//            Hobby hobby2 = new Hobby("Håndbold", "LINKYLINK", "Generel", "Indendørs");
//
//            em.persist(hobby1);
//            em.persist(hobby2);
//            em.getTransaction().commit();
//
//
//            Set<Hobby> hobbySet1 = new HashSet<>();
//            Set<Hobby> hobbySet2 = new HashSet<>();
//            hobbySet1.add(hobby1);
//            hobbySet2.add(hobby2);
//
//
//            phone1 = new Phone("1212121212", "no additional info");
//            Set<Phone> phoneSet1 = new HashSet<>();
//            phoneSet1.add(phone1);
//            Phone phone2 = new Phone("8989898989", "no additional info");
//            Set<Phone> phoneSet2 = new HashSet<>();
//            phoneSet2.add(phone2);
//
//
//            person = new Person("Mace@mail", "mace", "Windu", phoneSet1, address1, hobbySet1);
//            persistedPersonDTO = facade.create(new PersonDTO(person));
//            System.out.println(persistedPersonDTO.getId() + " THIS IS THE ID");
//            facade.create(new PersonDTO(new Person("Darth@mail", "Sheev", "Palpatine", phoneSet2, address2, hobbySet2)));
//
//
//        } finally {
//            em.close();
//        }
//    }
//
//    @AfterEach
//    public void tearDown() {
////        Remove any data after each test was run
//    }
//
//    @Test
//    public void testGetCount() throws PersonNotFoundException {
//        HobbyDTO hobbyDTO = new HobbyDTO(hobby1);
//        Assertions.assertEquals(1L, PersonFacade.getPersonCount(hobbyDTO), "Expects one person with this hobby");
//    }
//
//    @Test
//    public void testGetPersonByHobby()  throws PersonNotFoundException{
//        HobbyDTO hobbyDTO = new HobbyDTO(hobby1);
//        Assertions.assertEquals(1, facade.getPersonByHobby(hobbyDTO).size(), "Expects one person with this hobby");
//    }
//
//    @Test
//    public void testGetPersonByPhone()  throws PersonNotFoundException{
//        PhoneDTO phoneDTO = new PhoneDTO(phone1);
//        Assertions.assertEquals("mace", facade.getPersonByNumber(phoneDTO).getFirstName(), "Expects one person with this phone number");
//    }
//
//    @Test
//    public void testEditPerson() throws PersonNotFoundException {
//        PersonDTO personDTO = persistedPersonDTO;
//        personDTO.setFirstName("Blaze");
//        PersonDTO personDTO1 = PersonFacade.editPerson(personDTO);
//        Assertions.assertEquals("Blaze", personDTO1.getFirstName(), "Expects one person with this phone number");
//    }
//
//    @Test
//    public void testCreatePerson()  throws PersonNotFoundException{
//        EntityManager em = emf.createEntityManager();
//        em.getTransaction().begin();
//        CityInfo cityInfo3 = new CityInfo("3500", "Værløse");
//        em.persist(cityInfo3);
//        em.getTransaction().commit();
//        em.getTransaction().begin();
//        Address address3 = new Address("Rolighedsvej 98", "no additional info", cityInfo3);
//        Hobby hobby3 = new Hobby("Skak", "LINKYLINK", "Generel", "Indendørs");
//        em.persist(hobby3);
//        em.getTransaction().commit();
//        Phone phone3 = new Phone("70707070", "no additional info");
//        Set<Phone> phoneSet3 = new HashSet<>();
//        phoneSet3.add(phone3);
//
//        Set<Hobby> hobbySet3 = new HashSet<>();
//        hobbySet3.add(hobby3);
//
//        facade.create(new PersonDTO(new Person("Han@mail", "Han", "Solo", phoneSet3, address3, hobbySet3)));
//        em.close();
//        Assertions.assertEquals(3, facade.getAllPersons().size(), "Expects three persons in the database");
//    }
//
//}
