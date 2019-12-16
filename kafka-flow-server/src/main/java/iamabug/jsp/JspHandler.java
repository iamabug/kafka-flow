package iamabug.jsp;

import iamabug.conf.ClusterConfiguration;
import iamabug.conf.ClusterConfigurations;

public class JspHandler {

    public JspHandler(){}

    public int getClusterNumber() {
        return ClusterConfigurations.getClusters().size();
    }
}
