<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<script type="text/javascript"
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.5.1/jquery.min.js"></script>

<script type="text/javascript"
	src="<spring:url value="/js/jquery-ui-1.8.11.custom.min.js"></spring:url>">	
</script>
<script type="text/javascript"
	src="<spring:url value="/js/jquery.timeago.js"></spring:url>">	
</script>
<script type="text/javascript"
	src="<spring:url value="/js/jquery.customdata.js"></spring:url>">
	
</script>
<script type="text/javascript"
	src="<spring:url value="/js/jquery.dateFormat-1.0.js"></spring:url>">
	
</script>

<script type="text/javascript">
	var ROOTURL = <spring:url value="/"  />;

	jQuery(document).ready(function() {//create nice timestamps
		$('.timeago').each(function(index) {
			var d = $.format.date($(this).text().toString(), 'yyyy/MM/dd hh:mm:ss a');//format java data to js
			
			var date = new Date(d);
			niceTime = jQuery.timeago(date);
			$(this).text(niceTime);
		});

	});
	$(document).ready(function() {
		// $('.box').removeClass('shadow');

		$('.box').mouseenter(function() {
			$(this).addClass('extrashadow');
		});

		$('.box').mouseleave(function() {
			$(this).removeClass('extrashadow');
		});

		$(function() {
			$('.draggable').draggable({
				handle : 'h1'
			});

		});

	});
</script>

<div id="topbanner" class="container_16">
	<div class="logo grid_6">
		<c:url value="/" var="mainurl" />
		<h1>
			<a href="${mainurl}">Welcome to the <strong>Online
					Collaboration System</strong> </a>
		</h1>
	</div>

	<div id="middle" class="grid_5">
		<h2></h2>
	</div>

	<div class="buttons grid_5">

		<sec:authorize access="isAnonymous()">
			<c:url value="/login" var="loginurl" />
			<c:url value="/user/register" var="registerurl" />
			<a href="${loginurl}" class="button wide">Sign In</a>
			<a href="${registerurl}" class="button wide">Register</a>
		</sec:authorize>

		<sec:authorize access="isAuthenticated()">

			<c:url value="/user/id/" var="profileurl" />
			
			<a
				href="${profileurl}<sec:authentication property="principal.userid" />">Hello
				<sec:authentication property="principal.firstName" /> </a>
				
			<br />
			<a
				href="${profileurl}<sec:authentication property="principal.userid" />">
				Your Profile </a>

			<sec:authorize access="hasRole('ROLE_ADMIN')">

				<a href="<spring:url value="/admin/" htmlEscape="true" />">Admin
					Panel</a>

			</sec:authorize>|
			<a
				href="<spring:url value="/j_spring_security_logout" htmlEscape="true" />">
				Logout</a>
		</sec:authorize>

	</div>



</div>
