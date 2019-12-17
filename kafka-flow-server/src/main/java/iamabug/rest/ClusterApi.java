package iamabug.rest;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("")
public class ClusterApi {

    @POST
    public void addCluster() {
        System.out.println("addCluster is called.");
    }
}
