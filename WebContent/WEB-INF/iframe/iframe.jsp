<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head><link rel="stylesheet" href="css/resource.css" type="text/css" media="screen" />
<script src="http://www.google.com/jsapi"></script>
<script type="text/javascript"> 
      google.load("swfobject", "2.1");
    </script>
<script>

function onYouTubePlayerReady(){

ytplayer = document.getElementById("ytplayer");
ytplayer.addEventListener("onStateChange", "onytplayerStateChange");
}

function onytplayerStateChange(newState) {
	 	  parent.ytState(newState);
	  	}
</script>
<title>Page <c:out value="${page.pagenumber}"/></title>
</head>

<c:out value="${page.content}" escapeXml="false" />

<script type="text/javascript">
//<![CDATA[
   
   parent.IFLoaded();
//]]>
</script>