<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Insert title here</title>
</head>
<body>


<table align="center" >

<tr>

<td>Welcome:</td>

<td><core:out value="${module.title}" /></td>

</tr>

<tr>

<td>Description:</td>

<td><core:out value="${module.description}" /></td>

</tr>
<td>Tags:</td>

<td><core:out value="${module.tags}" /></td>

</tr>

</table>
</body>
</html>