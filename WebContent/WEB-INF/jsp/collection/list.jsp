<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript">
 $(document).ready(function() {
	// $('.box').removeClass('shadow');
	 
	 $('.collection').mouseenter(function() {
		 
		 //$('.collection').removeClass('mouseOver', 500);
		 
		 var id = $(this).attr('id');
		 //$('#cDetails').html("<h2>Need an ajax GET request for "+ id + "</h2>");
		 getCollectionDetails(id);
		
		// $(this).addClass('mouseOver', 500);
		 return false;
		 
	 });
 });
	 function getCollectionDetails(id){
			
			$.getJSON('<c:url value="/collection/id/" />'+id+'/details', function(data) {
				
				$('#cDetails').html('Title: ' + data.title + "<br />Author: " + data.ownerName);
				
	
		});
	 
 }
 
  
 </script>

<div class="grid_8">
	<div class="box shadow">
		<h1>Viewing all Collections</h1>

		<c:forEach var="collection" items="${collectionList}">
			<div id="<c:out value="${collection.collectionid}" />"
				class="collection mouseOver">
				<c:url value="id/${collection.collectionid}" var="collectionurl" />
				<h2>
					Title: <a href="${collectionurl}"><c:out
							value="${collection.title}" />
					</a>
				</h2>
				<ul>
					<li>Created: <c:out value="${collection.created}" />
					</li>
					<li>Modified: <c:out value="${collection.modified}" /></li>
				</ul>
				<hr />
			</div>
		</c:forEach>
		<c:url value="list?start=" var="pageurl"></c:url>
		<div id="colPager" class="pager">
			<c:if test="${start >0}">
				<a href="${pageurl}${prevStart}">Prev</a>
			</c:if>

			<a href="${pageurl}${nextStart}">Next</a>

		</div>
	</div>
</div>

<div class="grid_8">
	<div class="box shadow">
		<h1>Details:</h1>
		<div id="cDetails">
			<h2>Hover over a collection to see details</h2>
		</div>
	</div>

</div>