//package rest;
//
//import com.google.gson.Gson;
//import dtos.HobbyDTO;
//import dtos.PersonDTO;
//import dtos.PhoneDTO;
//import entities.*;
//import io.restassured.RestAssured;
//import io.restassured.http.ContentType;
//import io.restassured.parsing.Parser;
//import org.glassfish.grizzly.http.server.HttpServer;
//import org.glassfish.grizzly.http.util.HttpStatus;
//import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
//import org.glassfish.jersey.server.ResourceConfig;
//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import utils.EMF_Creator;
//
//import javax.persistence.EntityManager;
//import javax.persistence.EntityManagerFactory;
//import javax.ws.rs.core.UriBuilder;
//import java.net.URI;
//import java.util.HashSet;
//import java.util.Set;
//
//import static io.restassured.RestAssured.given;
//import static org.hamcrest.Matchers.equalTo;
//
//public class PersonResourceTest {
//    private static final int SERVER_PORT = 7777;
//    private static final String SERVER_URL = "http://localhost/api";
//    private static Person p1, p2;
//    private static CityInfo c1, c2;
//    private static Hobby hobby1, hobby2;
//    private static HobbyDTO hobbyDTO1;
//    private static Address address1, address2;
//    private static Phone phone1, phone2;
//    private static Set<Hobby> hobbySet1, hobbySet2, hobbySet3;
//
//    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
//    private static HttpServer httpServer;
//    private static EntityManagerFactory emf;
//    private static Gson gson = new Gson();
//
//    static HttpServer startServer() {
//        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
//        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
//    }
//
//    @BeforeAll
//    public static void setUpClass() {
//        //This method must be called before you request the EntityManagerFactory
//        EMF_Creator.startREST_TestWithDB();
//        emf = EMF_Creator.createEntityManagerFactoryForTest();
//
//        httpServer = startServer();
//        //Setup RestAssured
//        RestAssured.baseURI = SERVER_URL;
//        RestAssured.port = SERVER_PORT;
//        RestAssured.defaultParser = Parser.JSON;
//    }
//
//    @AfterAll
//    public static void closeTestServer() {
//        //System.in.read();
//
//        //Don't forget this, if you called its counterpart in @BeforeAll
//        EMF_Creator.endREST_TestWithDB();
//        httpServer.shutdownNow();
//    }
//
//    // Setup the DataBase (used by the test-server and this test) in a known state BEFORE EACH TEST
//    //TODO -- Make sure to change the EntityClass used below to use YOUR OWN (renamed) Entity class
//    @BeforeEach
//    public void setUp() {
//        EntityManager em = emf.createEntityManager();
//
//        c1 = new CityInfo("3751", "Østermarie");
//        c2 = new CityInfo("3730", "Nexø");
//        hobby1 = new Hobby("Programming", "LINKYLINK", "Generel", "Indendørs");
//        hobby2 = new Hobby("Håndbold", "LINKYLINK", "Generel", "Indendørs");
//        address1 = new Address("Lærkevej 99", "no additional info", c1);
//        address2 = new Address("Lyngby hovedgade 99", "no additional info", c2);
//        hobbySet1 = new HashSet<>();
//        hobbySet2 = new HashSet<>();
//        hobbySet1.add(hobby1);
//        hobbySet2.add(hobby2);
//        phone1 = new Phone("1212121212", "no additional info");
//        Set<Phone> phoneSet1 = new HashSet<>();
//        phoneSet1.add(phone1);
//        phone2 = new Phone("8989898989", "no additional info");
//        Set<Phone> phoneSet2 = new HashSet<>();
//        phoneSet2.add(phone2);
//        p1 = new Person("Lando@mail", "Lando", "calRizz", phoneSet1, address1, hobbySet1);
//        p2 = new Person("Boba@mail", "Boba", "Fett", phoneSet2, address2, hobbySet2);
//
//
//        try {
//            em.getTransaction().begin();
//            em.createNamedQuery("Phone.deleteAll").executeUpdate();
//            em.createNamedQuery("Person.deleteAllPersons").executeUpdate();
//            em.createNamedQuery("Hobby.deleteAll").executeUpdate();
//            em.createNamedQuery("Address.deleteAll").executeUpdate();
//            em.createNamedQuery("CityInfo.deleteAllRows").executeUpdate();
//            em.persist(c1);
//            em.persist(c2);
//            em.persist(hobby1);
//            em.persist(hobby2);
//            em.persist(address1);
//            em.persist(address2);
//            em.persist(p1);
//            em.persist(p2);
//            em.getTransaction().commit();
//        } finally {
//            em.close();
//        }
//    }
//
//    @Test
//    public void getPostalcodes() {
//        given()
//                .contentType(ContentType.JSON)
//                .get("/postalcode")
//                .then()
//                .assertThat()
//                .statusCode(HttpStatus.OK_200.getStatusCode())
//                .body("size()", equalTo(2));
//
//    }
//
//    //Test
//    @Test
//    public void createPerson() {
//        Phone phone3 = new Phone("26262626", "no additional info");
//        Set<Phone> phoneSet3 = new HashSet<>();
//        phoneSet3.add(phone3);
//        Person person = new Person("Darth@mail", "Darth", "Maul", phoneSet3, address1, hobbySet1);
//        PersonDTO pdto = new PersonDTO(person);
//        String requestBody = gson.toJson(pdto);
//        given()
//                .header("Content-type", ContentType.JSON)
//                .body(requestBody)
//                .post("/person")
//                .then()
//                .assertThat()
//                .statusCode(HttpStatus.OK_200.getStatusCode())
//                .body("idPerson", equalTo(person.getId()))
//                .body("firstName", equalTo(person.getFirstName()));
//        System.out.println(gson.toJson(person));
//    }
//
//    @Test
//    public void getCount() {
////        String requestBody = gson.toJson(hobbyDTO1);
////        System.out.println(requestBody);
//        String hobbyName = hobby1.getName();
//        given()
//                .header("Content-type", ContentType.JSON)
//                .get("/person/count/{hobby}", hobbyName)
//                .then()
//                .assertThat()
//                .statusCode(HttpStatus.OK_200.getStatusCode())
//                .body("count", equalTo(1));
//    }
//
//
//    @Test
//    public void getPersonByPhone() {
//
//
//        Phone phone3 = new Phone("26262626", "no additional info");
//        Set<Phone> phoneSet3 = new HashSet<>();
//        phoneSet3.add(phone3);
//        Person person = new Person("Darth@mail", "Darth", "Maul", phoneSet3, address1, hobbySet1);
//        PersonDTO pdto = new PersonDTO(person);
//        String requestBody = gson.toJson(pdto);
//        given()
//                .header("Content-type", ContentType.JSON)
//                .body(requestBody)
//                .post("/person")
//                .then()
//                .assertThat()
//                .statusCode(HttpStatus.OK_200.getStatusCode())
//                .body("idPerson", equalTo(person.getId()))
//                .body("firstName", equalTo(person.getFirstName()));
//        System.out.println(person.getFirstName());
//
//
//        String string = "26262626";
//        given()
//                .contentType(ContentType.JSON)
//                .get("/person/{number}", string)
//                .then()
//                .assertThat()
//                .statusCode(HttpStatus.OK_200.getStatusCode())
//                .body("firstName", equalTo("Darth"));
//    }
//
//    @Test
//    public void getAllPersons(){
//        given()
//                .contentType(ContentType.JSON)
//                .get("/person/home")
//                .then()
//                .assertThat()
//                .statusCode(HttpStatus.OK_200.getStatusCode())
//                .body("size()", equalTo(2));
//    }
//
//    @Test
//    public void getPersonsByHobby() {
//        given()
//                .contentType(ContentType.JSON)
//                .get("/person/hobby/{hobby}", hobby1.getName())
//                .then()
//                .assertThat()
//                .statusCode(HttpStatus.OK_200.getStatusCode())
//                .body("size()", equalTo(1));
//    }
//
//    @Test
//    public void editPerson() {
//        p1.setLastName("Norris");
//        PersonDTO pDTO = new PersonDTO(p1);
//        String requestBody = gson.toJson(pDTO);
//        given()
//                .header("Content-type", ContentType.JSON)
//                .body(requestBody)
//                .put("/person/edit/{id}", p1.getId())
//                .then()
//                .assertThat()
//                .statusCode(HttpStatus.OK_200.getStatusCode())
//                .body("firstName", equalTo("Lando"));
//
//    }
//
//    @Test
//    public void getPersonsByZip(){
//        given()
//                .contentType(ContentType.JSON)
//                .get("/person/zip/{zip}", c1.getZipCode())
//                .then()
//                .assertThat()
//                .statusCode(HttpStatus.OK_200.getStatusCode())
//                .body("size()", equalTo(1));
//    }
//
//
//    @Test
//    public void testDummyMsg() throws Exception {
//        given()
//                .contentType("application/json")
//                .get("/person/test")
//                .then()
//                .assertThat()
//                .statusCode(HttpStatus.OK_200.getStatusCode())
//                .body("msg", equalTo("Hello World"));
//    }
//
//
//}
