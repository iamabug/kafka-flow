    <%@ page contentType="text/html; charset=UTF-8" import="iamabug.jsp.JspHandler,iamabug.conf.ClusterConfiguration,java.util.List"%>
        <jsp:include page="template.jsp"></jsp:include>
           <%!
           private JspHandler handler;
           private List<ClusterConfiguration> clusters;
           %>
                  <%!
  public void jspInit(){
       handler = new JspHandler();
  }
  public void jspDestroy(){
  }
%>
        <div class="container">
        <h2>集群配置</h2>
        <table class="table">
        <% if (handler.getClusterNumber() > 0) { %>
            <thead>
            <tr>
            <th>集群名称</th>
            <th>bootstrap.servers</th>
            </tr>
            </thead>
        <tbody>
        <% clusters = handler.getClusters(); %>
            <% for (int i = 0; i<handler.getClusterNumber(); i++){ %>

        <tr>
        <td><%= clusters.get(i).getName() %></td>
        <td><%= clusters.get(i).getServers() %></td>
        </tr>
        <%}%>
        </tbody>
        </table>
        </div>

        <% } else { %>
            <tbody></tbody>
        </div>
     <% } %>
        <br>
        <div class="container">
        <button type="button" class="btn btn-primary" data-toggle="collapse" data-target="#addCluster">添加集群</button>
        <div id="addCluster" class="collapse">
            <br>
        <form id="add_cluster_form" action="/rest/clusters" method="post" onsubmit="setTimeout(function () { window.location.reload(); }, 10)">
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
