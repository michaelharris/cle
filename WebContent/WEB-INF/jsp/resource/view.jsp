<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!-- Use the Google AJAX Libraries API:
      http://code.google.com/apis/ajaxlibs/ -->
<script src="http://www.google.com/jsapi"></script>
<script type="text/javascript"> 
      google.load("swfobject", "2.1");
    </script>
<script type="text/javascript"
	src="<spring:url value="/js/jquery.ba-resize.js"></spring:url>">
	</script>
<script type="text/javascript"
	src="<spring:url value="/js/jquery.layout.rc.js"></spring:url>">
	</script>

<script type="text/javascript"
	src="<spring:url value="/js/resourceComment.js"></spring:url>">
	</script>
<script type="text/javascript"
	src="<spring:url value="/js/youtubeController.js"></spring:url>">
	</script>
<script type="text/javascript">
<c:url value="/comment/delete/id/" var="delUrl" />
	var numberOfPages = <c:out value="${noOfPages}"  />;
	var currentPage = <c:out value="${startPage}" />;
	var resourceId = <c:out value="${resource.resourceid}" escapeXml="true" />;
	var delUrl = "<c:out value="${delUrl}"escapeXml="false"  />";
	var commentOnCommentURL = "<c:url value="/comment/id/" />";
	var hn = window.location.host;
	//window.onload=updatePageLocation;
	var uId =0;
	var ownerId = <c:out value="${resource.user.userid}" />;
	<sec:authorize access="isAuthenticated()">
	uId = <sec:authentication property="principal.userid" />;
	</sec:authorize>
	$(document).ready(function() {
		outer = document;
		
		var host = window.location.host + window.location.pathname;
		$('#fbLink').attr( 'src', "http://www.facebook.com/plugins/like.php?href="+host+"&amp;layout=button_count&amp;show_faces=true&amp;width=450&amp;action=like&amp;font&amp;colorscheme=light&amp;height=21");
		$('#pId').val(currentPage);
		$('#resId').val(resourceId);
		$('#linkToThis').val(hn +ROOTURL +'resource/id/'+ resourceId +'?startPage=' +currentPage);
		
		

		getCommentStream();
		
		setInterval(function() {
		    getCommentStream(); //gets comment stream, and if logged in, private comments
		  
		}, 20000);

		if(uId == ownerId){
			var editLink = "<a href=\"" + ROOTURL+ "resource/id/"+ resourceId + "/editor" + "\" id=\"editLink"
			+ "\">Edit this resource</a>";
			
			var deleteLink = "<a href=\"" + ROOTURL+ "resource/id/"+ resourceId + "/delete" + "\" id=\"editLink"
			+ "\">Delete this resource</a>";

			var authorDiv = $('<div/>', {
				id : 'authorDiv',
				text : ""
			});
			
			authorDiv.append(editLink);
			authorDiv.append("<br />");
			authorDiv.append("<br />");
			authorDiv.append(deleteLink);
			$('#authorBox').append(authorDiv);
			
		}
		
		updatePageLocation();
		
		
	});
	
function IFLoaded() {
	//alert("ifloaded");
		//updatePageLocation();
		
		// Document is ready (but iframe isn't?)
		
		// Iframe is loaded (with id iframe)

		pageFrame = iframe.document;

		$(pageFrame).find('*').bind('click', noteSelection);
		iframe_content = $(pageFrame).contents().find('body');
		resourceHeight = iframe_content.height(); // get the real size of the
		// iframe page.
		resourceWidth = iframe_content.width();
		resizeEvent();
		$('iframe').resize(resizeEvent);
		
		
		
	}
	
	
	
	function nextPage() {//next page button
		if (currentPage < numberOfPages) {
			currentPage++;
			updatePageLocation();
		}
	}
	
	function updatePageLocation(){
		$('#iframe').attr(
				'src',
				"<spring:url value="/resourceid/${resource.resourceid}/page/"></spring:url>"
						+ currentPage + '/').fadeIn('slow');
		$('#pageLocation').html(
				'You are viewing page ' + currentPage + ' of ' + numberOfPages);
		$('#pId').val(currentPage);
		
		$('#linkToThis').val(hn +ROOTURL +'resource/id/'+ resourceId +'?startPage=' +currentPage);
		//resizeEvent();
		getCommentStream();
	}
	
	function prevPage() {//prev page button
		if (currentPage > 1) {
			currentPage--;
			updatePageLocation();			
		}
	}

	

	function createNewComment() {
		if(ytplayer){
			$('#location').val(ytplayer.getCurrentTime());
		}
		$.post('<c:url value="/comment/new" />', $('#commentForm').serialize(),
				function(data) {

					var submit = $(outer).find(":submit").attr('value',
							'Saved!'); //Creating closure for setTimeout function. 
					setTimeout(function() {
						$(submit).attr('value', 'Post');//change button text for 2 sec
						//clear textbox
						$('#commentdata').text('');
						
					}, 2000);
					//alert("postback");
					getCommentStream();
					getPrivateCommentStream();
				}, "json");
	}
	
	function createCommentOnComment() {
		$.post('<c:url value="/comment/new/onComment" />', $('#commentOnCommentForm').serialize(),
				function(data) {

					var submit = $(outer).find(":submit").attr('value',
							'Saved!'); //Creating closure for setTimeout function. 
					setTimeout(function() {
						$(submit).attr('value', 'Post');//change button text for 2 sec
						//clear textbox
						$('#commentdata').text('');
						
					}, 2000);
					//alert("postback");
					getCommentStream();
					
				}, "json");
	}
	
	function getCommentStream(){
			$.getJSON('<c:url value="/comment/stream" />', { page: currentPage, res: resourceId }, function(data) {
				
				$('#commentStream').html('');//remove current comment stream
				var streamContainer = $('#commentStream');
				 $.each(data, function(index, comment) {
					 newCommentDiv(streamContainer, comment);
					  });
		});
	
			 if(uId > 0){//if user is logged in, get their comments
				    getPrivateCommentStream();
				    }
	}
	
	function getPrivateCommentStream(){
		$.getJSON('<c:url value="/comment/privateStream" />', { page: currentPage, res: resourceId }, function(data) {
			
			$('#privateCommentStream').html('');//remove current comment stream
			var streamContainer = $('#privateCommentStream');
			 $.each(data, function(index, comment) {
				 newCommentDiv(streamContainer, comment);
				  });
	});

}
</script>




<script>
var outerLayout, innerLayout;


    $(document).ready(function () {
       outerLayout =  $('#resourceView').layout({ applyDefaultStyles: true,
    	   east__size:			270

       
       });
       innerLAyout = $('div.ui-layout-center').layout({ applyDefaultStyles: true });

   	$(function() {
		$( "#tabs" ).tabs();
	});

    });
</script>
</head>

<br />
<div id="resourceView" class="shadow">
	<div class="ui-layout-center">
		<!-- Inner Layout -->

		<c:url value="/resourceid/${resource.resourceid}/page/1/"
			var="frameurl" />
		<iframe class="ui-layout-center" id="iframe" name="iframe"
			src="${frameurl}" scrolling="auto">
			<p>Your browser does not support iframes.</p>
		</iframe>

		<div class="ui-layout-south">
			<div id="underResource" class="grid_16">
				<div class="grid_3">
					<input type="button" value="Prev" onclick="prevPage();" />
				</div>
				<div class="grid_10">
					<p id=pageLocation></p>
				</div>
				<div class="grid_3">
					<input type="button" value="Next" onclick="nextPage();" />
				</div>

			</div>
		</div>




		<!-- End of inner layout -->
	</div>



	<div class="ui-layout-south">
		<h1>Comments</h1>
		<sec:authorize access="isAuthenticated()">
			<form action="<spring:url value="/comment/new/"></spring:url>"
				method="post" id="commentForm">
				<input type="hidden" name="divId" id="divId" value="0"></input> <input
					type="hidden" name="location" id="location" value="0"></input> <input
					type="hidden" name="resId" id="resId" value="0" /> <input
					type="hidden" name="pId" id="pId" value="0"></input>
				<textarea id="commentdata" name="commentdata" rows="8" cols="50"></textarea>
				<select name="privacy">
					<option value="0">Private to me</option>
					<option selected="selected" value="1">Public</option>

				</select> <input type="submit" value="Post" />
			</form>
		</sec:authorize>

		<sec:authorize access="isAnonymous()">
					Please login to add a comment
					</sec:authorize>



	</div>

	<div class="ui-layout-east">



		<script>

	</script>

		<div id="tabs">
			<ul>
				<li><a href="#commentBox">Public</a></li>
				<li><a
					href="<spring:url value="#privateComments"></spring:url>">Private</a>
				</li>
			</ul>
			<div id="commentBox">
				<h1>Contributions</h1>
				<div id="commentStream"></div>
			</div>
			<div id="privateComments">
				<div id="privateCommentStream"></div>
			</div>
		</div>
	</div>




	<div class="ui-layout-west">
		<h1>
			Resource:
			<c:out value="${resource.title}" />
		</h1>
		<p>
			Description:
			<c:out value="${resource.description}" />
		</p>
		<p>
			Tags:
			<c:out value="${resource.tags}" />
		</p>
		<p>
			<c:out value="${noOfPages}" />
			pages
		</p>
		<br />
		<div id="authorBox"></div>
		<br />
		<hr />
		<br />
		<div id="linking">
			<h3>Link to this page:</h3>
			<textarea id="linkToThis">http://localhost:8080/cle/resource/id/6</textarea>

			<iframe id="fbLink"
				src="http://www.facebook.com/plugins/like.php?href=&amp;layout=button_count&amp;show_faces=true&amp;width=450&amp;action=like&amp;font&amp;colorscheme=light&amp;height=21"
				scrolling="no" frameborder="0"
				style="border: none; overflow: hidden; width: 450px; height: 21px;"
				allowTransparency="true"></iframe>

		</div>
	</div>


</div>