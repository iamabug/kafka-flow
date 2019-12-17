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
        Lorem ipsum dolor sit amet, consectetur adipisicing elit,
        sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,
        quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.
        </div>
        </div>
