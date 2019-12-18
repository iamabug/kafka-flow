package iamabug.jsp;

import iamabug.conf.ClusterConfiguration;
import iamabug.conf.ClusterConfigurations;

import java.util.List;

public class JspHandler {

    public JspHandler(){}

    public int getClusterNumber() {
        return ClusterConfigurations.getClusters().size();
    }

    public List<ClusterConfiguration> getClusters() {
        return ClusterConfigurations.getClusters();
    }
}
