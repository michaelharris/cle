$(function() {

	//attach autocomplete
	$("#collectionFinder").autocomplete({

		//define callback to format results
		source : function(req, add) {
			//pass request to server
			$.getJSON(ROOTURL + "collection/jsonList", req, function(data) {

				//create array for response objects
				var suggestions = [];

				//process response
				$.each(data.collectionList, function(i, val) {
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
			location.href= ROOTURL + "collection/id/" + collectionId;

		}

	})

	.data("autocomplete")._renderItem = function(ul, item) {
		return $("<li></li>").data("item.autocomplete", item).append(
				"<a>" + item.title + "<br />  -- " + item.description + "</a>")
				.appendTo(ul);
	};

});

$(function() {

	//attach autocomplete
	$("#resourceFinder").autocomplete({

		//define callback to format results
		source : function(req, add) {
			//pass request to server
			$.getJSON(ROOTURL + "resource/jsonList", req, function(data) {

				//create array for response objects
				var suggestions = [];

				//process response
				$.each(data.resourceList, function(i, val) {
					suggestions.push(val);

				});

				//pass array to callback
				add(suggestions);
			});
		},

		//define select handler
		select : function(e, ui) {
			var resourceId = ui.item.resourceid;
			//create formatted friend

			var result = ui.item.value;
			location.href= ROOTURL + "resource/id/" + resourceId;

		}

	})

	.data("autocomplete")._renderItem = function(ul, item) {
		return $("<li></li>").data("item.autocomplete", item).append(
				"<a>" + item.title + "<br />  -- " + item.description + "</a>")
				.appendTo(ul);
	};

});

