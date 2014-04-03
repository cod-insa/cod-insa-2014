//Nicolas Vailliet
//Map for CodINSA final contest interface
//Plane simulation

var mymap;

//Map Info from server
var map_name;
var base_count;
var center_lat = 48.0;
var center_long = 2.0;
var zoom_level = 5;

//Objects to draw
var basesArray = new Array();	
var planesArray = new HashMap();

//Moving planes
var pas = 0.3
var refreshtime = 20;

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


		//testMove();
}


function doMovePlane(index,latitudeTo, longitudeTo) {
	var currentplane = planesArray.get(index);

	if(currentplane === undefined){}else{

	var latitudeFrom = currentplane.position.lat();
	var longitudeFrom = currentplane.position.lng();
	
	//console.log("MovePlane: ("+latitudeFrom+":"+longitudeFrom+") to ("+latitudeTo+":"+longitudeTo+")");

	var newlat = 0;
	var newlong = 0;

	if(latitudeTo > latitudeFrom)
	{
		newlat = latitudeFrom + pas;
	}
	else
	{
		newlat = latitudeFrom - pas;
	}

	if(longitudeTo > longitudeFrom)
	{
		newlong = longitudeFrom + pas;
	}
	else
	{
		newlong = longitudeFrom - pas;
	}

	if(Math.abs(latitudeFrom - latitudeTo) < pas)
	{
		newlat = latitudeTo;
	}

	if(Math.abs(longitudeFrom - longitudeTo) < pas)
	{
		newlong = longitudeTo;
	}	

	currentplane.position = new google.maps.LatLng(newlat,newlong);
	currentplane.setMap(mymap);

	if( (newlat-latitudeTo) > pas || (newlong-longitudeTo) > pas)
	{
	setTimeout(function(){doMovePlane(index,latitudeTo,longitudeTo);},refreshtime);
	} 
	}
}


function testMove()
{

	planesArray.put("0",new google.maps.Marker({
		position: new google.maps.LatLng(48.0,2.0),
		map: mymap,
		icon:fighter_icon[4],
		title:"plane attacking"
		}));

	doMovePlane("0",40.0,-2.0);


}

