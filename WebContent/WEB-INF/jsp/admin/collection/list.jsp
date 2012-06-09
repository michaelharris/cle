<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
 <div class="box shadow">
<h1>Viewing all Collections</h1>
<table class="admin">
<tr><th>id</th><th>Title</th><th>Modified</th><th>Created</th><th>Resources:</th><th>Action</th></tr>
<c:forEach var="collection" items="${collectionList}">
<tr>
<td> <c:out value="${collection.collectionid}" /></td>
<td> <c:out value="${collection.title}" /></td>
<td>Description <c:out value="${collection.modified}" /></td>
<td>Tags: <c:out value="${collection.created}" /></td>
<td><a href="<spring:url value="${collection.collectionid}/resources/"  />" /> Show resources</a></td>

<td><form action="delete" method ="post"><input type ="hidden" name="id" value="<c:out value='${collection.collectionid}' />"/> 
<input type = "submit" value="Delete" />
 </form></td>
</tr>

</c:forEach>
</table>
</div>