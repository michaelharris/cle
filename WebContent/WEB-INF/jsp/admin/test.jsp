
 <%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
 
 Admin test page!!
 
 <sec:authorize access="hasRole('ROLE_SUPER')">

This content will only be visible to users who have
the "supervisor" authority in their list of <tt>GrantedAuthority</tt>s.

</sec:authorize>