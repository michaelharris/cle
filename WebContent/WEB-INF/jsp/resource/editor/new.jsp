<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<h1>Create new Resource</h1>


<form:form  commandName="resource" modelAttribute="resource" >

	<table align="center">

		

		<tr>

			<td>Resource Title :<form:errors path="title" cssClass="errors"/></td>

			<td><form:input path="title" /></td>

		</tr>
		
		
		<tr>

			<td>Resource Description :<form:errors path="description" cssClass="errors"/></td>

			<td><form:input path="description" /></td>

		</tr>
		
		<tr>

			<td>Resource Tags :<form:errors path="tags" cssClass="errors"/></td>

			<td><form:input path="tags" /></td>

		</tr>
		
		
	
		<tr>

			<td></td>

			<td><input type="submit" value="Submit" /></td>

		</tr>

	</table>

</form:form>
