<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="box shadow">
	<h1>Viewing all resources</h1>
	<table class="admin">
		<tr>
			<th>id</th>
			<th>Title</th>
			<th>Tags</th>
			<th>Description</th>
			<th>Action</th>
		</tr>
		<c:forEach var="resource" items="${resourceList}">
			<tr>
				<td><c:out value="${resource.resourceid}" />
				</td>
				<td><c:out value="${resource.title}" />
				</td>
				<td>Description <c:out value="${resource.tags}" />
				</td>
				<td>Tags: <c:out value="${resource.description}" />
				</td>
				<td><form action="delete" method="post">
						<input type="hidden" name="id"
							value="<c:out value='${resource.resourceid}' />" /> <input
							type="submit" value="Delete" />
					</form>
				</td>
			</tr>
		</c:forEach>
	</table>
</div>