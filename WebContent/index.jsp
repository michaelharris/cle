<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%-- Redirected because we can't set the welcome page to a virtual URL. <c:redirect url="/hello.htm"/> --%>
hello world - index.jsp

<li><a href="user/register.html">Register</a></li>

<li> <a href="module/new">New Module</a> </li>
<li> <a href="collection/new">New Collection</a> </li>
<li> <a href="admin/test">Admin secure pages</a> </li>
 <li><a href="<spring:url value="/j_spring_security_logout" htmlEscape="true" />">Logout</a></li>
  
  <form action="j_spring_security_logout">
    <input type="submit" value="Logout">
</form>