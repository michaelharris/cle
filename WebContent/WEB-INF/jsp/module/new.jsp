<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="grid_8 prefix_4">
	<div class="box shadow">
	
	<form:form commandName="module"	modelAttribute="module">

			<table align="center">
				<tr>
					<td>Module Title :<form:errors path="title" cssClass="errors" /></td>
					<td><form:input path="title" /></td>
				</tr>
				<tr>
					<td>Module Description :<form:errors path="description"
						cssClass="errors" /></td>
					<td><form:input path="description" /></td>
				</tr>
		
				<tr>
		
					<td>Module Tags :<form:errors path="tags" cssClass="errors" /></td>
		
					<td><form:input path="tags" /></td>
		
				</tr>
				<tr>
					<td></td>
					<td><input type="submit" value="Submit" /></td>
				</tr>
		
			</table>
			</form:form>
	</div>
</div>
	
	

