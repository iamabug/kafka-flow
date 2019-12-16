package iamabug.conf;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import iamabug.common.Constants;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 所有 Kafka 集群的配置
 */
public class ClusterConfigurations {

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
        System.out.println(clusters);
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
            System.out.println("clusters.json not found, an empty ClusterConfigurations is generated");
            try {
                configFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return new ArrayList<>();
        }
        try {
            return mapper.readValue(configFile, new TypeReference<List<ClusterConfiguration>>() {});
        } catch (IOException e) {
            e.printStackTrace();
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
            System.out.println(clusters);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void removeCluster() {

    }
}
