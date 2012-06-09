<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<script>
	$(document).ready(function() {
		$('#uploadResource').submit(function() {//disable multiple posts
			$('input[type=submit]', this).attr('disabled', 'disabled');
		});
	});
</script>

<div class="grid_10 prefix_3">
	<div class="box shadow">
		<h1>Create new Resource</h1>

		<h2>This form allows a resource to be created from a PDF file</h2>
		<form:form id="uploadResource" commandName="resource"
			modelAttribute="resource" enctype="multipart/form-data">

			<table align="center">



				<tr>

					<td>Resource Title :<form:errors path="title"
							cssClass="errors" />
					</td>

					<td><form:input path="title" />
					</td>

				</tr>


				<tr>

					<td>Resource Description :<form:errors path="description"
							cssClass="errors" />
					</td>

					<td><form:input path="description" />
					</td>

				</tr>

				<tr>

					<td>Resource Tags :<form:errors path="tags" cssClass="errors" />
					</td>

					<td><form:input path="tags" />
					</td>

				</tr>


				<tr>
					<td><form:label for="fileData" path="fileData">File (PDF)</form:label>
					</td>

					<td><form:input path="fileData" type="file" />
					</td>

				</tr>

				<tr>

					<td></td>

					<td><input type="submit" value="Submit" />
					</td>

				</tr>

			</table>

		</form:form>
	</div>
</div>
