<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="box shadow">
<h1>Viewing all Modules</h1>
<table class="admin">
<tr><th>id</th><th>Title</th><th>Tags</th><th>Description</th><th>Action</th></tr>
<c:forEach var="module" items="${moduleList}">
<tr>
<td> <c:out value="${module.moduleid}" /></td>
<td> <c:out value="${module.title}" /></td>
<td>Description <c:out value="${module.tags}" /></td>
<td>Tags: <c:out value="${module.description}" /></td>
<td><form action="delete" method ="post"><input type ="hidden" name="id" value="<c:out value='${module.moduleid}' />"/> 
<input type = "submit" value="Delete" />
 </form></td>
</tr>

</c:forEach>
</table>
</div>