
 <%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
 Standard admin user can see this bit!!
 <br />
 <sec:authorize access="hasRole('ROLE_SUPER')">
 This page is testing the @preAuthorize tag for the controller method. Only users with admin super should see this.
 </sec:authorize>