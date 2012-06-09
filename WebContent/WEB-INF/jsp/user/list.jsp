<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

 <script type="text/javascript">
 $(document).ready(function() {
	// $('.box').removeClass('shadow');
	 
	 $('.user').mouseover(function() {
		 var id = $(this).attr('id');
		 $('#uDetails').html("<h2>User id: "+ id + "</h2>");
		 
		 
	 });
	 
	 
	 
 });
  
 </script>

<div class="grid_8">
	<div class="box shadow">
		<h1>Viewing all Members</h1>
		
		<c:forEach var="user" items="${userList}">
			<div id="<c:out value='${user.userid}' />" class="user mouseOver">
				<c:url value="id/${user.userid}" var="profileurl" />
				
				<h2><a href="${profileurl}"><c:out value="${user.firstName}" /> <c:out value="${user.lastName}" /></a></h2>
				<ul>
					<li>
					Email: <a href="mailto:<c:out value='${user.email}' />"><c:out value='${user.email}' /></a>
					</li>
				</ul>
			</div>
			<hr />
		</c:forEach>
	</div>
</div>

<div class="grid_8">
	<div class="box shadow">
	<h1>Details:</h1>
	<div id="uDetails">
	<h2>Hover over a user to see details</h2>
	</div>
	</div>
	
</div>
