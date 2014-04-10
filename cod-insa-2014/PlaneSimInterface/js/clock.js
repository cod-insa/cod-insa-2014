//clock.js
//NV for CodINSA 2014

var hours;
var minutes;
var seconds;
var timeout;

function setClock (h,m,s)
{
	hours = h;
	minutes = m;
	seconds = s;
	var currentTimeString = hours + ":" + minutes + ":" + seconds;
  	document.getElementById("clock").innerHTML = currentTimeString;
}


function updateClock()
{


  seconds = seconds - 1;

  if(seconds == -1)
{
	seconds = 59;
	minutes = minutes - 1;
}

  if(minutes == -1)
{
	minutes = 59;
	hours = hours - 1;
}

  if(hours == -1)
{
	hours = 0;
	minutes = 0;
	seconds = 0;
	clearTimeout(timeout);
	return;
}

  // Compose the string for display
  var currentTimeString = hours + "h " + minutes + "m " + seconds + "s";

  // Update the time display
  document.getElementById("clock").innerHTML = currentTimeString;


	
  timeout = setTimeout(updateClock,1000);
}


/*setClock(1,1,10);
updateClock();*/
