<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>



<div class="grid_8 prefix_4">
<div class="box shadow">
	

		<h1>Please confirm that you wish to delete this resource</h1>
		<h2>
			Resource:
			<c:out value="${resource.title}" />
		</h2>
		<p>
			Description:
			<c:out value="${resource.description}" />
		</p>

		<form method="POST" action="confirmDelete">
			<input type="submit" />


		</form>


	</div>


</div>