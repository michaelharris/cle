<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<style>
.ui-autocomplete-loading {
	background: white url('images/ui-anim_basic_16x16.gif') right center
		no-repeat;
}
</style>

<script>
var uId =0;
<sec:authorize access="hasRole(\'ROLE_LECTURER\')">
uId = <sec:authentication property="principal.userid" />;
</sec:authorize>
	var moduleId = <c:out value="${module.moduleid}" />;
	$(document).ready(function() {
		
	});
	function getCollections() {
		$.getJSON(ROOTURL + 'module/jsonCollectionList', {
			moduleId : moduleId
		}, function(data) {
			$('#collectionList').html('');
			$.each(data.collectionList, function(index, collection) {
				

				var item = $('<li/>', {
					class : 'commentHead',
					html : ''
				});

				var link = $('<a/>',
						{
							href : ROOTURL + "collection/id/"
									+ collection.collectionid,
							text : collection.title
						});

				item.append(link);
				$('#collectionList').append(item);

			});
		});
	}
</script>

<div class="grid_8">
	<div id="moduleDetails" class="box shadow">
		<h1>
			Module:
			<c:out value="${module.title}" />
		</h1>
		<p>
			Description:
			<c:out value="${module.description}" />
		</p>
		<p>
			Tags:
			<c:out value="${module.tags}" />
		</p>
		<script>
		
		if(uId > 0){
			
			
			var deleteLink = "<a href=\"" + ROOTURL+ "module/id/"+ moduleId + "/delete" + "\" id=\"deleteLink"
			+ "\">Delete this Module</a>";
		$('#moduleDetails').append(deleteLink);
		}
		
		</script>
		<br />
		<h2>Collections:</h2>
		<div>
			<ul id="collectionList">
				<c:forEach var="collection" items="${module.collections}">
					<c:url value="/collection/id/" var="colUrl" />
					<li><a
						href="<c:out value="${colUrl}" /><c:out value="${collection.collectionid}" />"><c:out
								value="${collection.title}" />
					</a></li>

					
				</c:forEach>
			</ul>
		</div>
	</div>
</div>

<div class="grid_8">
	<div class="box shadow">
		<h1>Add collections:</h1>
		<h2>Type in the box to find a collection</h2>
		<script>
			$(function() {

				//attach autocomplete
				$("#to").autocomplete(
						{

							//define callback to format results
							source : function(req, add) {
								req['moduleId'] = moduleId;
								//pass request to server
								$.getJSON(ROOTURL
										+ "collection/selective/jsonList", req,
										function(data) {

											//create array for response objects
											var suggestions = [];

											//process response
											$.each(data.collectionList,
													function(i, val) {
														suggestions.push(val);

													});

											//pass array to callback
											add(suggestions);
										});
							},

							//define select handler
							select : function(e, ui) {
								var collectionId = ui.item.collectionid;
								//create formatted friend
								
								var result = ui.item.value;

								span = $("<span>").text(result + "  "),

								$.post(ROOTURL + 'module/addCollection', {
									moduleId : moduleId,
									collectionId : collectionId
								}, function(data) {

								
									getCollections(); //reload collection list
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
			<label for="to">Choose a Collection: </label> <input id="to" />
		</div>


		


	</div>

</div>