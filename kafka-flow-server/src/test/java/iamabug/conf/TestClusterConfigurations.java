package iamabug.conf;

import org.junit.Test;

import java.io.File;

public class TestClusterConfigurations {

    @Test
    public void testAddCluster() {
        ClusterConfigurations.addCluster(new ClusterConfiguration("name", "servers"));
    }


}
