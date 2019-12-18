    <%@ page contentType="text/html; charset=UTF-8" import="iamabug.jsp.JspHandler"%>
        <jsp:include page="template.jsp"></jsp:include>
           <%!
           private JspHandler handler;
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
        <tr>
        <td>John</td>
        <td>Doe</td>
        </tr>
        <tr>
        <td>Mary</td>
        <td>Moe</td>
        </tr>
        <tr>
        <td>July</td>
        <td>Dooley</td>
        </tr>
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
