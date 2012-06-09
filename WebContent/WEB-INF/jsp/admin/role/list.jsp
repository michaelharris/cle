<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="box shadow">
<h1>Viewing all roles</h1>
<table class="admin">
<tr><th>id</th><th>Title</th><th>Action</th></tr>
<c:forEach var="role" items="${roleList}">
<tr>
<td> <c:out value="${role.roleid}" /></td>
<td> <c:out value="${role.rolename}" /></td>

<td><form action="delete" method ="post"><input type ="hidden" name="id" value="<c:out value='${role.roleid}' />"/> 
<input type ="submit" value="Delete" />
 </form></td>
</tr>



</c:forEach>
</table>

New Role
<form action="new" method="post">
<input type="text" name="roleName" />
<input type="submit" value="Create" />
</form>
</div>