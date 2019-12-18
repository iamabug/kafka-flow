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
            <th>操作</th>
            </tr>
            </thead>
        <tbody>
        <% clusters = handler.getClusters(); %>
            <% for (int i = 0; i<handler.getClusterNumber(); i++){ %>

        <tr>
        <td id="name<%= i %>"><%= clusters.get(i).getName() %></td>
        <td id="servers<%= i %>"><%= clusters.get(i).getServers() %></td>
        <td><button id="<%= i %>" type="button" class="btn btn-outline-danger" onclick=removeCluster(this)>删除</button></td>
        </tr>
        <%}%>
        </tbody>
        </table>
        </div>

        <% } else { %>
     <% } %>
        <br>
        <div class="container">
        <a href="/new_config"><button type="button" class="btn btn-primary" >添加集群</button></a>
        </div>
        <script>
        function removeCluster(button) {
        name = document.getElementById("name"+button.id).innerHTML
        servers = document.getElementById("servers"+button.id).innerHTML
        $.ajax({
        type: "DELETE",
        url: "/rest/clusters",
        data: {name:name,servers:servers}
        })
        setTimeout(function(){ location.reload(); }, 200);
        }
        </script>
