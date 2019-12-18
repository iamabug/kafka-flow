    <%@ page contentType="text/html; charset=UTF-8" import="iamabug.jsp.JspHandler,iamabug.conf.ClusterConfiguration,java.util.List"%>
        <jsp:include page="template.jsp"></jsp:include>

        <div class="container">
        <h2>添加集群</h2>
        <br>
        <form id="add_cluster_form" action="/rest/clusters" method="post" >
        <div class="form-group">
        <label for="new_cluster_name">集群名称</label>
        <input class="form-control" id="new_cluster_name" name="new_cluster_name" required>
        </div>
        <div class="form-group">
        <label for="new_servers">bootstrap.servers</label>
        <input class="form-control" id="new_servers" name="new_servers" placeholder="localhost:9092" required>
        </div>
        <button type="submit" class="btn btn-primary" id="add">确认添加</button>
        </form>
        </div>