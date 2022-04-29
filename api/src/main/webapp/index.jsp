<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>Hello world</title>
</head>
<body>
<h1>Hello world</h1>
<th><spring:message code="message" text="message" /></th>
<a href="${pageContext.request.contextPath}/gifts">Gifts</a>
<a href="${pageContext.request.contextPath}/test">Test</a>
<a href="${pageContext.request.contextPath}/gift">Gift</a>
<a href="${pageContext.request.contextPath}/tag">Tag</a>
</body>
</html>
