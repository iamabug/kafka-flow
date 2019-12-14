    <%@ page contentType="text/html; charset=UTF-8" %>
        <jsp:include page="template.jsp"></jsp:include>
            <%
                  out.println("Hello World！");
           %>
            <div class="container">
            <h2>集群配置</h2>
            <table class="table">
            <thead>
            <tr>
            <th>集群名称</th>
            <th>bootstrap.servers</th>
            <th>Email</th>
            </tr>
            </thead>
            <tbody>
            <tr>
            <td>John</td>
            <td>Doe</td>
            <td>john@example.com</td>
            </tr>
            <tr>
            <td>Mary</td>
            <td>Moe</td>
            <td>mary@example.com</td>
            </tr>
            <tr>
            <td>July</td>
            <td>Dooley</td>
            <td>july@example.com</td>
            </tr>
            </tbody>
            </table>
            </div>
