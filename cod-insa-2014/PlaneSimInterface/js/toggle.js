
function runEffectLeft() {

	var selectedEffect = "slide";
	var options = {direction:"left"};

	// run the effect
	$( "#leftcontain" ).toggle( selectedEffect, options, 500 );
	
};

function runEffectRight() {

	var selectedEffect = "slide";
	var options = {direction:"right"};

	// run the effect
	$( "#rightcontain" ).toggle( selectedEffect, options, 500 );
	
};
	
	
// set effect from select menu value
$( "#closeonright" ).click(function() {
	runEffectRight();
	return false;
});

// set effect from select menu value
$( "#closeonleft" ).click(function() {
	runEffectLeft();
	return false;
});

// set effect from select menu value
$( "#logobutton" ).click(function() {
	runEffectLeft();
	runEffectRight();
	return false;
});

