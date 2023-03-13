package mappers;

import dtos.PostalCodeDTO;
import entities.CityInfo;
import errorhandling.PersonNotFoundException;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NamedQuery;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class PostalCodeMapper {



    public static List<PostalCodeDTO> getPostalCodes(EntityManagerFactory emf) throws PersonNotFoundException {
        EntityManager em = emf.createEntityManager();
        ArrayList<PostalCodeDTO> postalCodes = new ArrayList();
        try{
        Query query = em.createNamedQuery("CityInfo.findByOrderByCityDesc");
        List<CityInfo> rs = query.getResultList();
        for(CityInfo p : rs){
            PostalCodeDTO pDTO = new PostalCodeDTO(p);
            postalCodes.add(pDTO);
        }}
        catch (Exception e){
            throw new PersonNotFoundException("Could not get postal codes");
        }
        finally {
            em.close();
        }
        return postalCodes;
    }

}
