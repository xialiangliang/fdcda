<!--<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"  %>-->
<head>
    <meta charset="UTF-8">
    <#--<script src="/js/jquery.min.js" type="application/javascript"></script>-->
    <#--<script src="/js/jquery.md5.js" type="application/javascript"></script>-->
    <#--<link rel="stylesheet" href="/bootstrap/css/bootstrap.min.css">-->
    <#--<script src="/bootstrap/js/bootstrap.min.js"></script>-->

    <#--cdn加速 http://www.bootcdn.cn-->
    <script src="https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://cdn.bootcss.com/blueimp-md5/2.8.0/js/md5.min.js"></script>
    <link href="https://cdn.bootcss.com/bootstrap/3.3.4/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.bootcss.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>

    <title>后台管理系统</title>
</head>


<nav class="navbar navbar-default" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="/">后台管理系统</a>
        </div>
        <div>
            <ul class="nav navbar-nav navbar-right">
                <li class="dropdown">
                    <a href="/account/index" class="dropdown-toggle" data-toggle="dropdown">
                        账户
                        <b class="caret"></b>
                    </a>
                    <ul class="dropdown-menu">
                        <li>
                            <a href="/account/passwordPage">
                                <span class="glyphicon glyphicon-th"></span>
                                修改密码
                            </a>
                        </li>
                        <li>
                            <a href="/account/logout">
                                <span class="glyphicon glyphicon-off"></span>
                                退出
                            </a>
                        </li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</nav>