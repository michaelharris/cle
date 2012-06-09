<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="grid_8 prefix_4">
	<div class="box shadow">
		<h1>Search Results: resources</h1>
		<ul>
		<c:forEach var="resource" items="${resourceList}">
		
		<c:url value="id/${resource.resourceid}" var="resourceurl" />
		<li>
		<h2>Name: <a href="${resourceurl}"><c:out value="${resource.title}" /></a></h2>
		<p>Created: <c:out value="${resource.created}" /></p> 
		<p>Modified: <c:out value="${resource.modified}" /></p> 
		
		</li>
		</c:forEach>
		</ul>
</div>
</div>
