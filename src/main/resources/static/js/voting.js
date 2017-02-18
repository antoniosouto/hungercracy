$(document).ready( readyFn );

function readyFn( jQuery ) {
	//alert([[${voteResult}]]);
	
	if($("#voteResultOk").val() == "true") {
		$("form").hide();
		$("p").css('visibility', 'visible');
	} else if($("#voteResultOk").val() == "false") {
		$("p").css('visibility', 'visible');	
	}
	
}