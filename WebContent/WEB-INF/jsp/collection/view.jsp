<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
	<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
	
	<script>
	
	var uId =0;
	var ownerId = <c:out value="${collection.user.userid}" />;
	<sec:authorize access="isAuthenticated()">
	uId = <sec:authentication property="principal.userid" />;
	</sec:authorize>
	var collectionId = <c:out value="${collection.collectionid}" />;
	$(document).ready(function() {
		
		getResources();
	});
	function getResources() {
		$.getJSON(ROOTURL + 'collection/jsonResourceList', {
			collectionId : collectionId
		}, function(data) {
			$('#resourceList').html('');
			var ul = $('<ul/>', {
				class : '',
				html : ''
			});
			
			$.each(data.resourceList, function(index, resource) {
				//console.debug("each" + index + " " + resource.title);
				

				var item = $('<li/>', {
					html : ''
				});

				var link = $('<a/>',
						{
							href : ROOTURL + "resource/id/"
									+ resource.resourceid,
							text : resource.title
						});
				var text = "<br />Description: " + resource.description;

				item.append(link);
				item.append(text);
				
				if(uId == ownerId){//viewer is owner or TODO admin
					var removelink = $('<a/>',
							{
								href : ROOTURL +"collection/id/" + collectionId + "/remove/resource/id/"
										+ resource.resourceid,
								text : "Remove"
							});
					item.append(" - ");
					item.append(removelink);
				}
				
				
				$(ul).append(item);

			});
			$('#resourceList').append(ul);
		});
	}
</script>
<div class="container_16">
	<div class="grid_5">
		<div id="collectionDetails" class="box shadow">
			<h1>Collection: <c:out value="${collection.title}" /></h1>
			<p>Created: <c:out value="${collection.created}" /></p> 
			<p>Modified: <c:out value="${collection.modified}" /></p> 
			<script type="text/javascript">
			if(uId == ownerId){
			
			
			var deleteLink = "<a href=\"" + ROOTURL+ "collection/id/"+ collectionId + "/delete" + "\" id=\"editLink"
			+ "\">Delete this collection</a>";

			var authorDiv = $('<div/>', {
				id : 'authorDiv',
				text : ""
			});
			
		
			authorDiv.append(deleteLink);
			$('#collectionDetails').append(authorDiv);
			
		}
			</script>
		</div>
	</div>
	<div class="grid_5">
		<div class="box shadow">
		<h1>Resources</h1>
		<div id="resourceList">
				
				<ul>
				<c:forEach var="resource" items="${resourceList}">
				<c:url value="/resource/id/${resource.resourceid}" var="resourceurl" />
				<li><a href="${resourceurl}"><c:out value="${resource.title}" /></a>
				<br />
				Description: <c:out value="${resource.description}" />
				</li>
				
				</c:forEach>
				</ul>
				
				</div>
				<c:url value="/collection/id/${collection.collectionid}/add" var="collectionurl" />
				<a href="${collectionurl}">Upload a resource</a>
				
				<hr />
				
				
		</div>
		</div>
		<sec:authorize access="isAuthenticated()">
		<div class="grid_6">
		<div class="box shadow">
		<h1>Collections:</h1>
		<h2>Add an existing resource to this collection</h2>
		<script>
			$(function() {

				//attach autocomplete
				$("#to").autocomplete(
						{

							//define callback to format results
							source : function(req, add) {
								req['collectionId'] = collectionId;
								//pass request to server
								$.getJSON(ROOTURL
										+ "resource/selective/jsonList", req,
										function(data) {

											//create array for response objects
											var suggestions = [];

											//process response
											$.each(data.resourceList,
													function(i, val) {
														suggestions.push(val);

													});

											//pass array to callback
											add(suggestions);
										});
							},

							//define select handler
							select : function(e, ui) {
								var resourceId = ui.item.resourceid;
								//create formatted friend
								console.debug("second" + ui.item.resourceid);
								var result = ui.item.value;

								span = $("<span>").text(result + "  "),

								$.post(ROOTURL + 'collection/addResource', {
									collectionId : collectionId,
									resourceId : resourceId
								}, function(data) {

									console.debug("submitted");
									getResources(); //reload collection list
								});

								
							
							
								
							}

						})

				.data("autocomplete")._renderItem = function(ul, item) {
					return $("<li></li>").data("item.autocomplete", item)
							.append(
									"<a>" + item.title + "<br> "
											+ item.description + "</a>")
							.appendTo(ul);
				};
				
			});
		
		</script>





		<div class="ui-widget">
			<label for="to">Resource finder: </label> <input id="to" />
		</div>
		<h2>Or you can create and edit your own:</h2>
<h2>Create New Resource</h2>
				<br />
				
					
				
				<c:url value="/resource/create" var="newresourceurl" />
				<form id="newResource" action="<c:out value="${newresourceurl}" />"  method="post">
				<label>Name</label><input id="name" name="name" type="text" />
				<br />
				<label>Description</label><input id="description" name="description" type="text" />
				<br />
				<label>Tags</label><input id="tags" name="tags" type="text" />
				<br />
				<input type="hidden" name ="collectionId" value = "<c:out value="${collection.collectionid}" />" />
				<br />
				<input type="submit"  value="Next" />
				
				</form>

		
		
		</div>
		
	</div>
</sec:authorize>

</div>