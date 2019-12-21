    <%@ page contentType="text/html; charset=UTF-8" import="iamabug.jsp.JspHandler,iamabug.conf.ClusterConfiguration,java.util.List" %>
        <jsp:include page="template.jsp"></jsp:include>
            <%!
           private JspHandler handler;
           private List<ClusterConfiguration> clusters;
           public void jspInit(){
           handler = new JspHandler();
           }
           public void jspDestroy(){
            }
           %>

            <div class="container">
        <h2>生产数据</h2>
        <br>
        <table>
        <tbody>
        <tr>
        <td>
            <label>集群：</label>
        </td>
        <td>
            <select id="cluster" style="width:200px">
                <% clusters = handler.getClusters(); %>
                <% for (int i = 0; i<handler.getClusterNumber(); i++){ %>
            <option><%= clusters.get(i).getName()%></option>
            <%}%>
            </select>
        </td>
        </tr>
        <tr>
            <td height="30"><label>topic：</label></td>
            <td height="30"><input type="text" style="width:200px" id="topic"></td>
        </tr>
        </tbody>
        </table>
        <div>
        <br>
        <script src="/js/produce.js"></script>
        <button type="button" class="btn btn-primary" id="produce" onclick="produce()">生产</button>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
        <button type="button" class="btn btn-primary" id="clear" onclick="$('#messages').val('')">清空</button>
        </div>
        <br>
        <textarea class="form-control" rows="20" id="messages"></textarea>
        </div>
