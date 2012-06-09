<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script type="text/javascript"
	src="<spring:url value="/js/jquery.layout.rc.js"></spring:url>">
	
</script>
<script type="text/javascript"
	src="<spring:url value="/js/tiny_mce/jquery.tinymce.js"></spring:url>">
	
</script>
<script type="text/javascript"
	src="<spring:url value="/js/jquery.jeditable.js"></spring:url>">
	
</script>
<script>
	var outerLayout, innerLayout;
	var noOfPages = <c:out value="${noOfPages}" />;
	var resourceId = <c:out value="${resource.resourceid}" />;
	
	$(document).ready(function() {
		
		outerLayout = $('#resourceView').layout({
			applyDefaultStyles : true
		});
		innerLayout = $('div.ui-layout-center').layout({
			applyDefaultStyles : true
		});

	});
	
	function getPageObj(pageId) {
		//Retrieve the current page object
		var page;
		$.getJSON(ROOTURL + 'page/getPage.json', {
			pageId : pageId
		}, function(data) {
			page = data;
			return "hello";
			
		});
		return "page";
	}
	
</script>
<script type="text/javascript">
	$().ready(function() {

		$('#updateFormDiv').hide();
		setupMCE();
	});
	function setupMCE() {
		//$('#formPageData').show();
		$('#formPageData')
				.tinymce(
						{
	                        // Location of TinyMCE script
	                        script_url : '<spring:url value="/js/tiny_mce/tiny_mce.js"></spring:url>',

	                        // General options
	                        theme : "advanced",
	                        plugins : "pagebreak,style,layer,table,save,advhr,advimage,advlink,emotions,iespell,inlinepopups,insertdatetime,preview,media,searchreplace,print,contextmenu,paste,directionality,fullscreen,noneditable,visualchars,nonbreaking,xhtmlxtras,template",

	                        // Theme options
	                        theme_advanced_buttons1 : "bold,italic,underline,strikethrough,|,justifyleft,justifycenter,justifyright,justifyfull,styleselect,formatselect,fontselect,fontsizeselect",
	                        theme_advanced_buttons2 : "cut,copy,paste,pastetext,pasteword,|,search,replace,|,bullist,numlist,|,outdent,indent,blockquote,|,undo,redo,|,link,unlink,anchor,image,cleanup,help,code,|,insertdate,inserttime,preview,|,forecolor,backcolor",
	                        theme_advanced_buttons3 : "tablecontrols,|,hr,removeformat,visualaid,|,sub,sup,|,charmap,emotions,iespell,media,advhr,|,print,|,ltr,rtl,|,fullscreen",
	                        theme_advanced_buttons4 : "insertlayer,moveforward,movebackward,absolute,|,styleprops,|,cite,abbr,acronym,del,ins,attribs,|,visualchars,nonbreaking,template,pagebreak",
	                        theme_advanced_toolbar_location : "top",
	                        theme_advanced_toolbar_align : "left",
	                        theme_advanced_statusbar_location : "bottom",
	                        theme_advanced_resizing : true,

	                      
	                        // Drop lists for link/image/media/template dialogs
	                        template_external_list_url : "lists/template_list.js",
	                        external_link_list_url : "lists/link_list.js",
	                        external_image_list_url : "lists/image_list.js",
	                        media_external_list_url : "lists/media_list.js"

	                        
	                        
						}
				);

	}
</script>

<script type="text/javascript">
	var rootURL = <spring:url value="/" />;
	function buildPageLinks() {
		
		$('.pageLink').click(function() {

			var pageId = $(this).customdata("pageid");
			var pageNumber = $(this).customdata("pageno");
			$('#updateFormDiv').show();

			
		
			$.getJSON(ROOTURL + 'page/getPage.json', {
				pageId : pageId
			}, function(page) {
				$('#formPageData').html(page.content);
				$('#formPageId').val(page.pageid);
				
				$('#editableTags').text(page.tags +"-");
				$('#editableTitle').text(page.title);
			});
			
			
			$('.editable').editable(ROOTURL + 'page/id/' +pageId + '/editDetails', {
				indicator : 'Saving...',
		         tooltip   : 'Click to edit...',
				callback : function(value, settings) {
					$.getJSON(ROOTURL + 'page/getPage.json', {
						pageId : pageId
					}, function(page) {
						
						$('#editableTags').text(page.tags +"-");
						$('#editableTitle').text(page.title);
					});
			     }
			}
			
			);
			
			$('#deletePageLink').click(function(){
				$.post(ROOTURL + "page/delete", { pageId: pageId },
						   function(data) {
						     alert("Page " + pageNumber + " was deleted");
						     location.reload();
						   });
			});
			
		});
	}
	
	

	$(document).ready(
			function() {
				buildPageLinks();

				$('#pageUpdateForm').submit(
						function() {
							var outer = this;
							$.post('<c:url value="/page/update" />', $(
									'#pageUpdateForm').serialize(), function(
									data) {

								var submit = $(outer).find(":submit").attr(
										'value', 'Saved!'); //Creating closure for setTimeout function. 
								setTimeout(function() {
									$(submit).attr('value', 'Post');//change button text for 2 sec
									//clear textbox

								}, 2000);

							});
							return false;
						});
			});
	
</script>

<script type="text/javascript">
	$(document).ready(function() {

		$('#newPageLink').click(function(event) {
		
			$.post('<c:url value="/page/new" />', {
				resourceId : resourceId
			}, function(data) {
			
				noOfPages++;

				getPageList(resourceId);
			});

			return false;
		});
		
		$('#newVideoPageLink').click(function(event) {
			
			var videoId = prompt("Youtube Video ID", "Id String of video from url");
			$.post('<c:url value="/page/new/" />', {
				resourceId : resourceId, pageType: 'youtube', videoId: videoId
			}, function(data) {
				//console.debug("posted and got new page");
				noOfPages++;

				getPageList(resourceId);
			});

			return false;
		});

	});
	function getPageList(resId) {
		$
				.getJSON(
						rootURL + "page/getPageList",
						{
							resourceId : resId
						},
						function(resourceList) {

							$('#pageList').html('');
							$
									.each(
											resourceList.pageList,
											function(index, page) {
												// console.debug(index + ': ' + page.pagenumber); 

												spanText = '<span class="pageLink" data-pageNo="'+ page.pagenumber+ '"'
				  +' data-pageId="'+page.pageid +'"><a href="#">- '
														+ 'Page '
														+ page.pagenumber
														+ ' - '
														+ page.title
														+ ' </a> </span>';

												var listItem = $('<li/>', {
													id : page + "list",
													html : spanText
												});
												$('#pageList').append(listItem);

											});
							buildPageLinks();
							//console.debug(resourceList);
						});

	}
</script>

<div id="resourceView" class="shadow">
	<div class="ui-layout-center">
		middle stuff
		<!-- Inner Layout -->


		<div class="ui-layout-center">
			<div id="updateFormDiv">
			<form
				action="<spring:url value="/resource/editor/submit"></spring:url>"
				method="post" id="pageUpdateForm">

				<textarea id="formPageData" class="tinymce" name="formPageData"
					rows="8" cols="50"></textarea>

				<input id="formPageId" name="formPageId" type="hidden" value="" />
				<input type="submit" value="Save" />
			</form>
			</div>
			
			
		</div>

		<div class="ui-layout-south">
			<div id="underResource">
			<div class="light">Click to edit these page details</div>
				<div  class="grid_4">
					Title: <span class="editable" id= "editableTitle" />
					</div>
					<div  class="grid_4">
					Tags: <span class="editable" id= "editableTags" />
				</div>
				<div id="deletePage" class="grid_4 prefix_4"><a id="deletePageLink" href="#">Delete Page</a></div>


			</div>
		</div>




		<!-- End of inner layout -->

	</div>



	<div class="ui-layout-south">
		<h1>Comments</h1>



	</div>

	<div class="ui-layout-west">
		<h1>
			Resource:
			<c:out value="${resource.title}" />
		</h1>
		<c:url value="/resource/id/${resource.resourceid}/"
			var="presenterurl" />
		<a href="<c:out value="${presenterurl}" />">Presenter View</a>
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
		<a id="newPageLink" href="">Create New Page</a> <br />
		<a id="newVideoPageLink" href="">Create Youtube Page</a> <br />
		<hr />
		<br />
		<ul id="pageList">
			<c:forEach var="page" items="${pageList}">
				<li><span class="pageLink"
					data-pageNo="<c:out value="${page.pagenumber}" />"
					data-pageId="<c:out value="${page.pageid}" />"> <a href="#">-
							Page <c:out value="${page.pagenumber}" /> - <c:out
								value="${page.title}" /> </a> </span></li>
			</c:forEach>
		</ul>
	</div>
<p id="ptest" data-remote="ccc">customdata p</p>
</div>
