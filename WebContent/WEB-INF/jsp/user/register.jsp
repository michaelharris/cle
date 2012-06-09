
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="grid_8 prefix_4">
	<div class="box shadow">
	<h1>Registration</h1>
		<form:form action="details"	commandName="user" modelAttribute="user">
	
		<table align="center">
			<tr>
				<td>Email :<form:errors path="email" cssClass="errors" /></td>
	
				<td><form:input path="email" /></td>
			</tr>
		<tr>
	
				<td>First Name :<form:errors path="firstName" cssClass="errors" /></td>
	
				<td><form:input path="firstName" /></td>
	
			</tr>
	
			<tr>
	
				<td>Last Name :<form:errors path="lastName" cssClass="errors" /></td>
	
				<td><form:input path="lastName" /></td>
	
			</tr>
	
			<tr>
	
				<td>Password :<form:errors path="password" cssClass="errors" /></td>
	
				<td><form:input type="password" path="password"  /></td>
	
			</tr>
	
			<tr>
	
				<td>Confirm Password :<form:errors path="confirmPassword"
					cssClass="errors" /></td>
	
				<td><form:input type="password" path="confirmPassword" /></td>
	
			</tr>
	
			<tr>
	
				<td></td>
	
				<td><input type="submit" value="Submit" /></td>
	
			</tr>
	
		</table>
	
	</form:form></div>
</div>