package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.HobbyDTO;
import dtos.PersonDTO;
import dtos.PhoneDTO;
import dtos.PostalCodeDTO;
import entities.CityInfo;
import entities.Hobby;
import entities.Person;
import entities.Phone;
import facades.PersonFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("person")
public class PersonResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    private static final PersonFacade PERSON_FACADE = PersonFacade.getPersonFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();


    @GET
    @Path("{number}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getPersonByPhone(@PathParam("number") String number) {
//        PhoneDTO phoneDTO = new PhoneDTO(number);
        EntityManager em = EMF.createEntityManager();
        Query query = em.createNamedQuery("Phone.findByNumber");
        query.setParameter("number", number);
        Phone phone = (Phone) query.getSingleResult();

        PhoneDTO phoneDTOTEST = new PhoneDTO(phone);
        phoneDTOTEST.setId(phone.getId());
//        PhoneDTO phoneDTO = GSON.fromJson(number, PhoneDTO.class);
//        System.out.println(phoneDTO);
        PersonDTO personDTO = PERSON_FACADE.getPersonByNumber(phoneDTOTEST);
        return Response.ok().entity(GSON.toJson(personDTO)).build();
    }

    @GET
    @Path("test")
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello World\"}";
    }

    @GET
    @Path("home")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllPersons() {

        List<PersonDTO> personDTOList = PERSON_FACADE.getAllPersons();
        String json = GSON.toJson(personDTOList);

        return Response.ok().entity(json).build();
    }


    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response addPerson(String input) {
        PersonDTO personDTO = GSON.fromJson(input, PersonDTO.class);
        PersonDTO personDTONew = PERSON_FACADE.create(personDTO);
        return Response.ok().entity(personDTONew).build();
    }

    @GET
    @Path("count/{hobby}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response getPersonCount(@PathParam("hobby") String hobbyName) {

        HobbyDTO hobbyDTO = new HobbyDTO(hobbyName);

        Long count = PERSON_FACADE.getPersonCount(hobbyDTO);
        System.out.println(count + "Count here");
        String countString = "{\"count\":"+count+"}";
        return Response.ok().entity(countString).build();
    }

    @GET
    @Path("hobby/{hobby}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response getPersonsByHobby(@PathParam("hobby") String hobbyName) {

        HobbyDTO hobbyDTO = new HobbyDTO(hobbyName);

        return Response.ok().entity(PERSON_FACADE.getPersonByHobby(hobbyDTO)).build();
    }

    @PUT
    @Path("edit/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response editPerson(@PathParam("id")int id,String input) {
        PersonDTO personDTO = GSON.fromJson(input, PersonDTO.class);
        personDTO.setId(id);
        PersonDTO personDTONew = PERSON_FACADE.editPerson(personDTO);
        return Response.ok().entity(personDTONew).build();
    }

    @GET
    @Path("zip/{zip}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response getPersonsByZip(@PathParam("zip") String zip) {
        PostalCodeDTO postalCodeDTO = new PostalCodeDTO(zip);

        return Response.ok().entity(PERSON_FACADE.getPeopleByPostalCode(postalCodeDTO)).build();
    }


}
