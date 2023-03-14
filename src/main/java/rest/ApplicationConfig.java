package rest;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;
import javax.servlet.*;
import javax.ws.rs.core.Application;

@javax.ws.rs.ApplicationPath("api")
public class ApplicationConfig extends Application{

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(errorhandling.GenericExceptionMapper.class);
        resources.add(org.glassfish.jersey.server.wadl.internal.WadlResource.class);
        resources.add(rest.RenameMeResource.class);
        resources.add(rest.PersonResource.class);
        resources.add(rest.PostalCodeResource.class);
        resources.add(errorhandling.PersonNotFoundException.class);
        resources.add(errorhandling.PersonNotFoundExceptionMapper.class);
        resources.add(rest.CorsFilter.class);
    }
    
}
