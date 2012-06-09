<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="grid_8 prefix_4">
	<div class="box shadow">
		<h1>Thank you for registering</h1>
		
		
		<p> Thanks <c:out value="${user.firstName}," />
		
		
		your registered email is: 
		
		<c:out value="${user.email}" />
		</p>
		<p>You can now login</p>
	</div>
</div>
