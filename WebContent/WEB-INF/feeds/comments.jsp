<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

	
<c:out value="${example}" />
<c:url value="/user/id/${user.userid}" var="profileurl" />
<div id ="feedContainer">
	<c:forEach var="comment" items="${itemList}">
	<div class="commentContainer">
	<span class="commentHead"><a href="${profileurl}"><c:out value="${user.firstName}" /> <c:out value="${user.lastName}" /></a> posted: </span>
<span class="commentDelete">x</span>
	<div class="commentText">
	
				
	
	<c:out value="${comment.commenttext}" />
	</div>
	</div>
	</c:forEach>

</div>