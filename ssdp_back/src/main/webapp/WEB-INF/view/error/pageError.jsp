<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2017/7/25
  Time: 18:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
    <meta charset="utf-8">
    <title>异常页面</title>
</head>
<body>
    <p>1.通过java配置bean完成的异常信息.</p>
    <p>2.异常错误<%=request.getAttribute("ex")%></p>
</body>
</html>
