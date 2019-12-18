package iamabug.rest;

import iamabug.conf.ClusterConfiguration;
import iamabug.conf.ClusterConfigurations;
import org.glassfish.jersey.server.Uri;
import org.glassfish.jersey.server.mvc.Viewable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.net.URISyntaxException;

@Path("/clusters")
public class ClusterApi {

    private static final Logger logger = LoggerFactory.getLogger(ClusterApi.class);

    @POST
    @Produces(MediaType.TEXT_HTML)
    public Response addCluster(String request) throws URISyntaxException {
        logger.info("addCluster is called");
        ClusterConfigurations.addCluster(parseRequest(request));
        return Response.seeOther(new URI("/config")).build();
    }

    private ClusterConfiguration parseRequest(String request) {
        String[] fields = request.split("&");
        String name = fields[0].split("=")[1];
        String servers = fields[1].split("=")[1];
        return new ClusterConfiguration(name, servers);
    }

    @DELETE
    public void remoteCluster(String request) throws URISyntaxException{
        logger.info("remove cluster is called: " + request);
        ClusterConfigurations.removeCluster(parseRequest(request));
    }
}
