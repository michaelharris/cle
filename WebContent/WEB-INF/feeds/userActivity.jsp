<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
This is the user feed 
	
<c:out value="${example}" />
<c:url value="/user/id/${user.userid}" var="profileurl" />
<div id ="feedContainer">
	<c:forEach var="item" items="${itemList}">
	<div class="feedItem">
	<span class="itemHead"><a href="${profileurl}"><c:out value="${user.firstName}" /> <c:out value="${user.lastName}" /></a> posted: </span>

	<div class="itemBody">
	<c:url value="/resource/id/${item.resourceId}" var="resourceurl" />
				
	
	&quot;<c:out value="${item.commentText}" />&quot; on <a href="${resourceurl}"><c:out value="${item.resourceTitle}" /></a>
	</div>
	</div>
	</c:forEach>

</div>