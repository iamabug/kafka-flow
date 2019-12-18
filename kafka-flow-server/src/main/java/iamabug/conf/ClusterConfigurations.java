package iamabug.conf;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import iamabug.common.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 所有 Kafka 集群的配置
 */
public class ClusterConfigurations {

    private static final Logger logger = LoggerFactory.getLogger(ClusterConfigurations.class);

    private static ObjectMapper mapper = new ObjectMapper();

    private static final File configFile;

    static {
        String prefix = System.getProperty(Constants.PROPERTY_CONF_PATH);
        if (prefix != null) {
            configFile = new File(prefix + "/clusters.json");
        } else {
            configFile = new File("conf/clusters.json");
        }
    }


    private static List<ClusterConfiguration> clusters;

    static {
        clusters = loadConfigurations();
        logger.info("ClusterConfigurations loaded. Initial cluster configurations are : {}", clusters);
    }

    private ClusterConfigurations(){}

    public static List<ClusterConfiguration> getClusters() {
        return clusters;
    }

    /**
     * load from conf/clusters.json
     * @return
     */
    private static List<ClusterConfiguration> loadConfigurations() {
        if (!configFile.exists()) {
            return new ArrayList<>();
        }
        try {
            return mapper.readValue(configFile, new TypeReference<List<ClusterConfiguration>>() {});
        } catch (IOException e) {
            logger.error("Exception occurred while reading from clusters.json : {0}", e);
            return new ArrayList<>();
        }
    }

    public static void addCluster(ClusterConfiguration clusterConfiguration) {
        clusters.add(clusterConfiguration);
        try {
            if (!configFile.exists()) {
                configFile.createNewFile();
            }
            mapper.writeValue(configFile, clusters);

            logger.info("Current cluster configurations : {}.", clusters);
        } catch (IOException e) {
            logger.error("Exception occurred while adding cluster {} : {}", clusterConfiguration, e);
        }
    }

    public static void removeCluster() {

    }
}
