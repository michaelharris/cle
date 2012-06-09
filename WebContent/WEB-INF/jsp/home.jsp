<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<script type="text/javascript"
	src="<spring:url value="/js/home.js"></spring:url>">
	
</script>


<div class="grid_6">
	<div class="box shadow">
		<h1>Collections</h1>
		<h2>Recently updated</h2>
		<ul>
			<c:forEach var="collection" items="${collectionList}">
				<c:url value="collection/id/${collection.collectionid}"
					var="collectionurl" />
				<li><a href="${collectionurl}"><c:out
							value="${collection.title}" /> </a> - ${collection.description} <span
					class="light">updated <span class="timeago"><c:out
								value="${collection.modified}" />
					</span> </span>
				</li>
			</c:forEach>
		</ul>
		<br />

		<div class="ui-widget">
			<label for="collectionFinder">Find a Collection: </label> <input
				id="collectionFinder" />
		</div>
		<br />
		<hr />
		<span><a href="collection/new">Create new Collection</a> or <a
			href="collection/list">List Collections</a> </span>

	</div>

	<div class="box shadow">
		<h1>Resources</h1>
		<h2>Recently updated</h2>
		<ul>
			<c:forEach var="resource" items="${resourceList}">
				<c:url value="resource/id/${resource.resourceid}" var="resourceurl" />
				<li><a href="${resourceurl}"><c:out
							value="${resource.title}" /> </a> - ${resource.description} - <span
					class="light">updated <span class="timeago"><c:out value="${resource.modified}" /></span> </span></li>
			</c:forEach>
		</ul>
		<br />
		<div class="ui-widget">
			<label for="resourceFinder">Find a Resource: </label> <input
				id="resourceFinder" />
		</div>
		<br />
		<h2>Please enter your search below, you can use wildcards (* ?)</h2>
		<c:url value="resource/advancedSearch"
					var="advurl" />
				<a href="${advurl}">Advanced Search</a>

	</div>
</div>





<div class="grid_6">
	<div class="box shadow">
		<h1>Modules</h1>
		<h2>Recently updated</h2>
		<ul>
			<c:forEach var="module" items="${moduleList}">
				<c:url value="module/id/${module.moduleid}" var="moduleurl" />
				<li><a href="${moduleurl}"><c:out value="${module.title}" />
				</a> <span class="light"> updated <span class="timeago"><c:out
								value="${module.modified}" />
					</span> </span>
				</li>
			</c:forEach>
		</ul>
		<br /> <a href="module/new">Create new module</a> or <a
			href="module/list">List Modules</a>
	</div>
</div>

<div class="grid_4">
	<div class="box shadow">
		<sec:authorize access="isAnonymous()">
			<!-- Not logged in -->

			<h1>Login</h1>
			<h2>Please login here</h2>
			<form name="f" action="<c:url value='j_spring_security_check'/>"
				method="POST">

				User:<br /> <input type='text' name='j_username'
					value='<c:if test="${not empty param.login_error}"><c:out value="${SPRING_SECURITY_LAST_USERNAME}"/></c:if>' /><br />
				Password:<br /> <input type='password' name='j_password' /> <br />
				<input type="checkbox" name="_spring_security_remember_me" />Remember
				me <input name="submit" type="submit" value="Login -->" />

			</form>

		</sec:authorize>

		<!-- Is Authenticated -->
		<sec:authorize access="isAuthenticated()">

			<h1>Welcome</h1>
			<h2>
				Hello
				<sec:authentication property="principal.firstName" />
			</h2>

			<sec:authorize access="hasRole('ROLE_ADMIN')">
				<p>
					You have admin privileges <a
						href="<spring:url value="/admin/" htmlEscape="true" />">Admin
						box</a>
				</p>
			</sec:authorize>
			<a
				href="<spring:url value="/j_spring_security_logout" htmlEscape="true" />">Logout</a>
		</sec:authorize>
	</div>
	<div class="box shadow">
		<h1>Messages</h1>
		<ul>
			<li>Welcome to the new collaboration system</li>
		</ul>
		<p></p>
	</div>
	<div class="box shadow">
		<h1>People</h1>
		<p>
			Find people here: <a href="user/list">List users</a>
		</p>
	</div>

</div>






