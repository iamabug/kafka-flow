package iamabug.conf;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Objects;

/**
 * 一个 Kafka 集群的配置
 */
public class ClusterConfiguration {

    private static ObjectMapper mapper = new ObjectMapper();

    private String name;
    private String servers;

    // for jackson deserialization
    public ClusterConfiguration() {}

    public ClusterConfiguration(String name, String servers) {
        this.name = name;
        this.servers = servers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getServers() {
        return servers;
    }

    public void setServers(String servers) {
        this.servers = servers;
    }

    public ClusterConfiguration fromJson(String json) throws JsonProcessingException {
        return mapper.readValue(json, ClusterConfiguration.class);
    }

    public String toJson() throws JsonProcessingException {
        return mapper.writeValueAsString(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClusterConfiguration that = (ClusterConfiguration) o;
        return name.equals(that.name) &&
                servers.equals(that.servers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, servers);
    }

    @Override
    public String toString() {
        return "{" +
                "name='" + name + '\'' +
                ", servers='" + servers + '\'' +
                '}';
    }
}
