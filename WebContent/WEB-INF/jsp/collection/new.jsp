<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div class="grid_8 prefix_4">
	<div class="box shadow">
		<form:form commandName="collection/new" modelAttribute="collection">
		<h1>Create a new Collection</h1>
			<fieldset>
		
		
		
			<ol>
		
				<li><form:errors path="title" cssClass="errors" /> <form:label path="title">Title:</form:label><form:input path="title" /></li>
				<li><label path="description">Description: </label><form:errors
					path="description" cssClass="errors" /> <form:input
					path="description" /></li>
				
			</ol>
		
		
			</fieldset>
			<fieldset class="submit"><input type="submit" value="Submit" />
		
			</fieldset>
		
		</form:form>
	</div>
</div>
