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
        <h2>消费数据</h2>
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
        <tr>
            <td><label>offset.reset：</label></td>
            <td>
                <div class="radio">
                    <input type="radio" value="earliest" name="offset" id="earliest" checked="checked" required>earliest
                    <input type="radio" value="latest" name="offset" id="latest">latest
                </div>
            </td>
        </tr>
        </tbody>
        </table>
        <div>
        <br>
        <script src="/js/consume.js"></script>
        <button type="button" class="btn btn-primary" id="start" onclick="consume()">开始消费</button> &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
        <button type="button" class="btn btn-secondary" id="stop" onclick="stop()">停止消费</button>
        </div>
        <br>
        <textarea class="form-control" rows="20" id="messages"></textarea>
        </div>
