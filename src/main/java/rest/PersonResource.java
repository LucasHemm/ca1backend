package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.HobbyDTO;
import dtos.PersonDTO;
import facades.PersonFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("person")
public class PersonResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    private static final PersonFacade PERSON_FACADE = PersonFacade.getPersonFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();


    @GET
    @Path("number")
    @Produces({MediaType.APPLICATION_JSON})
    public String getPersonByPhone() {
        return null;
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello World\"}";
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
    @Path("count")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response getPersonCount(String input) {
        HobbyDTO hobbyDTO = GSON.fromJson(input, HobbyDTO.class);
        Long count = PERSON_FACADE.getPersonCount(hobbyDTO);
        return Response.ok().entity(count).build();
    }



}
