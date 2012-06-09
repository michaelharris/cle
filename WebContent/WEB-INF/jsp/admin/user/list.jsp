<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="box shadow">
<h1>Viewing all Members</h1>
<table class="admin">
<tr><th>id</th><th>Email</th><th>First Name</th><th>Last Name</th><th>Delete</th><th>Roles</th></tr>
<c:forEach var="user" items="${userList}">
<tr>
<td> <c:out value="${user.userid}" /></td>
<td> <c:out value="${user.email}" /></td>
<td>Description <c:out value="${user.firstName}" /></td>
<td>Tags: <c:out value="${user.lastName}" /></td>
<td><form action="delete" method ="post"><input type ="hidden" name="id" value="<c:out value='${user.userid}' />"/> 
<input type = "submit" value="Delete" /> </form></td>
<td>
<a href="roleEdit?user=<c:out value='${user.userid}' />" > Role Editor </a>


</td>
</tr>

</c:forEach>
</table>
</div>