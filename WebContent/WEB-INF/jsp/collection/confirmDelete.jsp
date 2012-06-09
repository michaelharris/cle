<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
	<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
	
	
<div class="container_16">
	<div class="grid_8 prefix_4">
		<div id="collectionDetails" class="box shadow">
			<h1>Confirm delete of <c:out value="${collection.title}" /></h1>
			<h2>Are you sure that you would like to delete this collection?</h2>
			<p>Name: <c:out value="${collection.title}" /></p>
			<p>Created: <c:out value="${collection.created}" /></p> 
			<p>Modified: <c:out value="${collection.modified}" /></p> 
			
			<form method="POST" action="confirmDelete">
			<input type="submit" value="Delete" />


		</form>
		</div>
	</div>
	

</div>