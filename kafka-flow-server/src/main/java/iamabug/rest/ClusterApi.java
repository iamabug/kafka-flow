package iamabug.rest;

import iamabug.conf.ClusterConfiguration;
import iamabug.conf.ClusterConfigurations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/clusters")
public class ClusterApi {

    private static final Logger logger = LoggerFactory.getLogger(ClusterApi.class);

    @POST
    public void addCluster(String request) {
        logger.info("addCluster is called");
        ClusterConfigurations.addCluster(parseRequest(request));
    }

    private ClusterConfiguration parseRequest(String request) {
        String[] fields = request.split("&");
        String name = fields[0].split("=")[1];
        String servers = fields[1].split("=")[1];
        return new ClusterConfiguration(name, servers);
    }
}
