<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="grid_8">
		<div class="box shadow">
			<h1>Viewing all modules</h1>
			<ul>
			<c:forEach var="module" items="${moduleList}">
			<li>
			<c:url value="id/${module.moduleid}" var="moduleurl" />
			<h2>Module Name: <a href="${moduleurl}"><c:out value="${module.title}" /></a></h2>
			<p>Description <c:out value="${module.description}" /></p> 
			<p>Tags: <c:out value="${module.tags}" /></p> 
			</li>
			</c:forEach>
			</ul>
		</div>
</div>