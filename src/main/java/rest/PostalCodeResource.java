package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import facades.PostalCodeFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("postalcode")
public class PostalCodeResource {


    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    private static final PostalCodeFacade POSTAL_CODE_FACADE =  PostalCodeFacade.getPostalCodeFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();


    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getPersonByPhone() {
        return null;
    }



}
