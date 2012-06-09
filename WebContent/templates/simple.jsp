<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Learning Collaboration Online</title>

<!-- Meta Tags -->

<meta charset="UTF-8" />
<meta name="description" content="Online collaborative learning system " />
<meta name="keywords"
	content="Learning, e-learning, student, education, university, degree, collaboration, notes, resources" />
<meta name="author" content="Michael Harris" />

<!-- Favicon -->
<c:url value="/css/favicon.ico" var="icourl" />
<link rel="shortcut icon" href="${icourl}" />

<!-- CSS -->
<link
	href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css"
	rel="stylesheet" type="text/css" />
<c:url value="/css/screen.css" var="url" />
<link rel="stylesheet" href="${url}" type="text/css" media="screen" />
<script type="text/javascript"
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.5.1/jquery.min.js"></script>

<script type="text/javascript"
	src="<spring:url value="/js/jquery-ui-1.8.11.custom.min.js"></spring:url>">
	
</script>
<script type="text/javascript"
	src="<spring:url value="/js/jquery.timeago.js"></spring:url>">
	
</script>
<script type="text/javascript"
	src="<spring:url value="/js/jquery.customdata.js"></spring:url>">
	
</script>
<script type="text/javascript"
	src="<spring:url value="/js/jquery.dateFormat-1.0.js"></spring:url>">
	
</script>

</head>
<body>
	<div id="pagewrapper">
		<div id="contentwrapper">
			<div id="content" class="container_16">

				<tiles:insertAttribute name="body" />
			</div>
		</div>
	</div>
	<!-- close pagewrapper -->
</body>
</html>
