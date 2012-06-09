<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="grid_8 prefix_4">
	<div class="box shadow">
		<h1>Search Results: Collections</h1>
		<ul>
		<c:forEach var="collection" items="${collectionList}">
		
		<c:url value="id/${collection.collectionid}" var="collectionurl" />
		<li>
		<h2>Name: <a href="${collectionurl}"><c:out value="${collection.title}" /></a></h2>
		<p>Created: <c:out value="${collection.created}" /></p> 
		<p>Modified: <c:out value="${collection.modified}" /></p> 
		
		</li>
		</c:forEach>
		</ul>
</div>
</div>
