var ytplayer;
$(document).ready(function() {
	$('#iframe').load(function() {
		ytplayer = iframe.document.getElementById("ytplayer");
		});
	
	pageFrame = iframe.document;
	
//	alert("doc ready "+ pageFrame);
	
	  
	
	
	
});

function ytGoTo(location){
	ytplayer.seekTo(location, true);
	
}
function ytState(state){
	
	//alert("time" + ytplayer.getCurrentTime());
	$('#divId').val(5);
	//$('#location').val(ytplayer.getCurrentTime());
}

