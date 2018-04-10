package no.ntnu.vislab.vislabcontroller.factories;

import java.util.Iterator;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;

import no.ntnu.vislab.vislabcontroller.providers.Projector;

public class ProjectorFactory {
    private final ServiceLoader<Projector> loader;
    private static ProjectorFactory service;

    private ProjectorFactory(){
        loader = ServiceLoader.load(Projector.class);
    }

    public static synchronized ProjectorFactory getInstance(){
        if(service == null){
            service = new ProjectorFactory();
        }
        return service;
    }

    public Projector getProjector(String make, String model){
        Projector projector = null;
        try{
            Iterator<Projector> projectors = loader.iterator();
            while(projector == null && projectors.hasNext()){
                Object obj = projectors.next();
                Projector tempProjector = (Projector) obj;
                if(tempProjector.getMake().toLowerCase().equals(make.toLowerCase())
                        && tempProjector.getModel().toLowerCase().equals(model.toLowerCase())){
                    projector = tempProjector;
                }
            }
        } catch (ServiceConfigurationError serviceError){
            projector = null;
            System.out.println("ERROR");
            serviceError.printStackTrace();
        }
        return projector;
    }
}
