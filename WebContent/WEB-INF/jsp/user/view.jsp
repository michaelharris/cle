<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


	<script>
	
	var userId = <c:out value='${user.userid}' />
 $(document).ready(function() {
	
	 
	$('#feed').load(ROOTURL + 'user/recentActivity',{userId: userId }).fadeIn("slow");
	 var auto_refresh = setInterval(
			 function ()
			 {
			 $('#feed').load(ROOTURL + 'user/recentActivity',{userId: userId }).fadeIn("slow");
			 }, 10000); 
});
</script>

<div class="grid_8">
		<div class="box shadow">
			<h1>Profile for <c:out value="${user.firstName}" /> <c:out value="${user.lastName}" /></h1>
			<ul>
					<li>
					Email: <a href="mailto:<c:out value='${user.email}' />"><c:out value='${user.email}' /></a>
					</li>
					<li>
					Roles:
					<c:forEach var="role" items="${user.roles}">
					 <c:out value='${role.rolename}' />
					</c:forEach>
					</li>
				</ul>
			
		</div>
		
		<div class="box shadow">
		<h1>Recent Activity</h1>
		<div id= "feed">
	
		
		
		
		</div>
		
		
		
		</div>
</div>