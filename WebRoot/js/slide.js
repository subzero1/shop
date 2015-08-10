$(document).ready(function() {
	
	// Expand Panel
	$("#open").click(function(){
		$("div#panel").slideToggle(600);
	});	
	
	// Collapse Panel
	$("#close").click(function(){
		$("div#panel").slideToggle(600);
	});		
	
	// Switch buttons from "Log In | Register" to "Close Panel" on click
	$("#toggle a").click(function () {
		$("#toggle a").toggle(600);
	});		
		
});
