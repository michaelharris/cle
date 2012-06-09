/**
 * 
 */

var pageFrame;
var outer;
var resourceHeight, resourceWidth;
var iframe_content;
function addZero(num)
{
	(String(num).length < 2) ? num = String("0" + num) :  num = String(num);
	return num;		
}


function resizeEvent() {
	//alert("resizeEvent");
	

	var height = $('#iframe').height();
	var width = $('#iframe').width();

	var factor = height / resourceHeight;
	var factor2 = width / resourceWidth;
	var min = Math.min(factor, factor2);
	min = Math.floor(min * 100) / 100;// round down to 2 d.p
	var factorString = "scale(" + min + ")";
	// need to apply adjustments to div that contains page - this is the
	// first element underneath body
	$(pageFrame).find('body > *').css({
		'transform' : factorString,
		'-moz-transform' : factorString,
		'-webkit-transform' : factorString
	});
	$(pageFrame).find('body > *').css({
		'transform-origin' : 'top left',
		'-moz-transform-origin' : 'top left',
		'-webkit-transform-origin' : 'top left'
	});
//	alert("ENDresizeEvent");
	

}
$(document).ready(function() {

	$('#commentForm').submit(function() {
		// alert('Handler for .submit() on comment form called.');
		createNewComment();

		$('#selected').html("<p>Saved</p>");
		return false;
	});

});
function getSelectedText() {
	var t = '--';
	if (window.getSelection) {
		t = iframe.window.getSelection();
	} else if (document.getSelection) {
		t = iframe.document.getSelection();
	} else if (document.selection) {
		t = iframe.document.selection.createRange().text;
	}

	// document.getElementById("target").value = t;

}
function noteSelection() {

	var target = $(this).attr("data-elementid"); // id of selected div
	if (target > 10) {
		var div = $(this);

		if (!$(this).hasClass("selected")) {
			$('*', pageFrame).removeClass("selected"); // if not selected -
			// deselect
			// everything else.
			// $(this).css("background-color", "BEF781");
			$(this).addClass("selected"); // select the div that was clicked
			// on
			$('#selected').html("<p>" + $(this).text() + "</p>"); // Put the
			// text of
			// the
			// selected
			// line in
			// the
			// selected
			// div
			$('#divId').val(target);// set id in hidden form
			// alert($(this).text());
		} else {
			$(this).removeClass("selected"); // if it was selected, remove
			// selection.
			$('#divId').val("");// set id in hidden form to blank
			$('#selected').html("<p>Nothing selected</p>");
		}
	}
	return false;
}

function pageElementHighlight(elementId) {
	frame = $("#iframe").contents().find('body');
	$('[data-elementid=' + elementId + ']', frame).each(function(index, item) {
		$(item).addClass('commentMouseOver');
	});

}
function pageElementRemoveHighlight(elementId) {
	frame = $("#iframe").contents().find('body');
	$('[data-elementid=' + elementId + ']', frame).each(function(index, item) {
		$(item).removeClass('commentMouseOver');
	});
}

function newCommentDiv(streamContainer, comment) {
	var date = new Date(comment.modified);
	niceTime = jQuery.timeago(date);

	var newContainer = $('<div/>', {
		id : 'com_' + comment.commentid,

		class : 'commentContainer'
	});

	var postText = $('<div/>', {
		class : 'commentText',
		text : comment.commenttext
	});
	
	
	var nameSpan = $('<span/>', {
		class : 'commentHead',
		html : "<a href=\"" + ROOTURL + "user/id/" + comment.user.userid + "\">" + comment.user.firstName +" "+ comment.user.lastName + "</a>"
	});
	
	

	var delSpan = $('<span/>', {
		class : 'commentDelete',
		text : ""
	});
	var delLink = "<a href=\"" + delUrl + comment.commentid + "\" id=\"del_"
			+ comment.commentid + "\">x</a>";

	var details = $('<div/>', {
		class : 'timestamp light',
		text : "posted " + niceTime + "."
	});
	// nameSpan.appendTo(newDiv);
	newContainer.append(nameSpan);
	if (comment.user.userid == uId) {
		delSpan.append(delLink);
		newContainer.append(delSpan);
		// only show delete button if comment belongs to logged in user (TODO
		// admin perms)
	}

	newContainer.append(postText);
	
	if(comment.location && comment.location != 0){//Add time link for video
		secVar0 = Math.round(comment.location);                            // The initial data, in seconds
		minVar = Math.floor(secVar0/60);  // The minutes
		secVar = addZero(secVar0 % 60);              // The balance of seconds
		var locationLink = $('<a/>', {
			class : 'locationLink',
			href: '#',
			text : "at "+ minVar +":" +secVar + " "
			
		});
		$(locationLink).attr('data-location',comment.location);
		
		$(locationLink).click(function() {
			ytGoTo(comment.location);
			return false;
			});
		newContainer.append(locationLink);
	}
	newContainer.append(details);
	// subcomment div

	var subComments = $('<div/>', {
		id : 'subComments_' + comment.commentid,
		class : 'subCommentContainer'

	});
	// find all subcomments
	var result = $.getJSON(commentOnCommentURL + comment.commentid + "/stream",
			function(data) {
				$.each(data, function(index, comment) {
					var subcomment = $('<div/>', {
						id : 'subComment_' + comment.commentid,
						class : 'subComment',
						text : comment.commenttext
					});

					var subcommentinfo = $('<div/>', {

						class : 'subCommentInfo light',
						text : comment.user.firstName + " "
								+ comment.user.lastName
					});
					subcomment.append(subcommentinfo);
					subComments.append(subcomment);

				});

			});

	// add the subcomments
	newContainer.append(subComments);

	newContainer.appendTo(streamContainer);// add the comment to the list on
	// the view
	var thisCommentDiv = '#com_' + comment.commentid; // comment div id is a no.
	// prefixed for clarity
	$(thisCommentDiv).attr('data-elementid', comment.elementid);// which div does this
	// comment reference
	$(thisCommentDiv).attr('data-commentid', comment.commentid);
	// add mouseover
	$(thisCommentDiv).mouseenter(function() {
		$(this).addClass('commentMouseOver');
		pageElementHighlight(comment.elementid);
		// alert($(this).customdata('elementid'));
	}).mouseleave(function() {
		$(this).removeClass('commentMouseOver');
		pageElementRemoveHighlight(comment.elementid);
	});
	;

	$("#del_" + comment.commentid).click(function() {// deleting a post
		$.get(delUrl + comment.commentid);
		// getCommentStream();Does this before db is updated
		$('#com_' + comment.commentid).remove();
		return false;
	});
	// $('.timestamp').cuteTime();

	// bind a click to enable comment on comment

	$(thisCommentDiv).click(function(event) {
		event.stopPropagation();
		commentClick(this);

	});
}

function commentClick(comment) {
	var commentId = $(comment).customdata('commentid');
	$(comment).focus();
	// alert("commentClicked id: " + $(this).customdata('commentid'));

	if ($(comment).hasClass("commentOnComment")) {
		$(document).click(function() {
			$('.commentForm').remove();
			$(comment).removeClass("commentOnComment");
		});
	} else {
		var text = '<form id="commentOnCommentForm" ><input type="hidden" name="parentId" id="parentId" value="'
				+ commentId
				+ '"></input>'
				+ '<textarea id="commentdata" name="commentdata" ></textarea>'
				+ '<input type="submit" value="Post" />' + '</form>';

		var commentOnComment = $('<div/>', {
			class : 'commentForm',
			html : text
		});

		$(comment).append(commentOnComment);
		$(comment).addClass("commentOnComment");

		$('#commentOnCommentForm').submit(function() {
			createCommentOnComment();

			return false;
		});

	}

}
