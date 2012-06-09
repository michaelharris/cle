 <%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
 <%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
 <div class="box shadow">
 <h1>App Administration</h1>
 
<h2><a href="collections/">Collection Management</a></h2>

<p>Collection management page</p>
<br />

<hr />

<h2><a href="modules/">Module Management</a> </h2>

<p>Modules creation or editing</p>
<br />

<hr />


<h2><a href="users/">User Management</a></h2>

<p>Create or edit uses and roles</p>
<br />
<hr />

<h2><a href="roles/">Role Management</a></h2>

<p>Create or edit roles</p>
<br />
<hr />
 <sec:authorize access="isAuthenticated()">
 <p>You are logged in
 <a href="<spring:url value="/j_spring_security_logout" htmlEscape="true" />">Logout</a></p>
</sec:authorize>

 <sec:authorize access="isAnonymous()">
 <p>
 You are not logged in yet
 
 <a href="<spring:url value="/login" htmlEscape="true" />">Login</a></p>
 </sec:authorize>
 
 </div>