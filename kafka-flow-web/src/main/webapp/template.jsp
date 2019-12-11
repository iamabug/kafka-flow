    <%@ page contentType="text/html; charset=UTF-8" %>
        <!DOCTYPE html>
        <html>

        <head>
        <title>Kafka Flow</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://cdn.staticfile.org/twitter-bootstrap/4.3.1/css/bootstrap.min.css">
        <script src="https://cdn.staticfile.org/jquery/3.2.1/jquery.min.js"></script>
        <script src="https://cdn.staticfile.org/popper.js/1.15.0/umd/popper.min.js"></script>
        <script src="https://cdn.staticfile.org/twitter-bootstrap/4.3.1/js/bootstrap.min.js"></script>
        </head>

        <body>
        <div class="container-fluid">
        <ul class="nav nav-pills" role="tablist">
        <li class="nav-item">
        <a class="nav-link active" data-toggle="pill" href="#home">首页</a>
        </li>
        <li class="nav-item">
        <a class="nav-link" data-toggle="pill" href="#config_tab">集群配置</a>
        </li>
        <li class="nav-item">
        <a class="nav-link" data-toggle="pill" href="#produce_tab">生产数据</a>
        </li>
        <li class="nav-item">
        <a class="nav-link" data-toggle="pill" href="#consume_tab">消费数据</a>
        </li>
        <li class="nav-item">
        <a class="nav-link" data-toggle="pill" href="#duplex_tab">生产 & 消费</a>
        </li>
        </ul>
        </div>
        </body>
        </html>