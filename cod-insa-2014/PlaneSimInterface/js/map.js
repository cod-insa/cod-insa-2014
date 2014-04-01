//Nicolas Vailliet
//Map for CodINSA final contest interface
//Plane simulation

var mymap;

//Map Info from server
var map_name;
var base_count;
var center_lat;
var center_long;
var zoom_level;

//Objects to draw
var basesArray = new Array();	
var planesArray = new HashMap();


//Images

var base_icon = new Array(
	"../img/home/home_white.png", 
	"../img/home/home_red.png", 
	"../img/home/home_green.png", 
	"../img/home/home_blue.png", 
	"../img/home/home_yellow.png", 
	"../img/home/home_pink.png", 
	"../img/home/home_black.png");

var fighter_icon = new Array(
	"../img/army/air_force_white.png", 
	"../img/army/air_force_red.png", 
	"../img/army/air_force_green.png", 
	"../img/army/air_force_blue.png", 	
	"../img/army/air_force_yellow.png", 
	"../img/army/air_force_pink.png", 
	"../img/army/air_force_black.png");

var civilian_icon = new Array(
	"../img/people/plane_white.png", 
	"../img/people/plane_red.png", 
	"../img/people/plane_green.png", 
	"../img/people/plane_blue.png", 	
	"../img/people/plane_yellow.png", 
	"../img/people/plane_pink.png", 
	"../img/people/plane_black.png");

//Initialization	
function initialisationMaps(){

		 // Current location
		 var myLatlng = new google.maps.LatLng(center_lat,center_long);
		 
		 // Map options
		 var myOptions = {
			zoom: zoom_level,
			center: myLatlng,
			disableDefaultUI: true,
			panControl: false,
			zoomControl: false,
			mapTypeControl: false,
			scaleControl: false,
			streetViewControl: false,
			overviewMapControl: false,
			mapTypeId: google.maps.MapTypeId.SATELLITE
		}

		 // Map creation with options
		mymap = new google.maps.Map(document.getElementById("mapcontain"), myOptions);
		mymap.setOptions({draggable: true, zoomControl: false, scrollwheel: false, disableDoubleClickZoom: true});
		google.maps.event.clearInstanceListeners(mymap);
}

