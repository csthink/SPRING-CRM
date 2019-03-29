<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    request.setAttribute("basePath", basePath);
%>
<c:set value="${requestScope['javax.servlet.forward.request_uri']}" var="requestUri"/>
<c:set value="${requestScope['javax.servlet.forward.query_string']}" var="queryString"/>