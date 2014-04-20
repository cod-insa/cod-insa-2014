//Nicolas Vailliet
//Map for CodINSA final contest interface
//Plane simulation

//THE MAP!
var mymap;

//Objects to draw
var basesArray = new Array();	
var planesArray = new Array();
var bullets = new Array();
var debris = new Array();

//See settings.js for other variables
//See resources.js for markers images

/*
* Initializes the map before the connection with the server	
*/
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
		mymap.setOptions({draggable: false, zoomControl: false, scrollwheel: false, disableDoubleClickZoom: true});
		google.maps.event.clearInstanceListeners(mymap);
}

/*
* Update the displayed location according to first info sent by the server
* (ie: server sends map info as first frame, so we use it)
*/
function updateMapWithInfoFromServer()
{
	mymap.setCenter(new google.maps.LatLng(center_lat,center_long));
	mymap.setZoom(zoom_level);
}

/*
* Update a plane position
*/
/*function updatePosition(index,latitude,longitude)
{
	if(planesArray[index] === undefined)
	{
		console.error("Trying to move a plane that does not exist...");
	}
	else
	{
		planesArray[index].mark.position = new google.maps.LatLng(latitude,longitude);
		planesArray[index].mark.setMap(mymap);
	}
}*/

/*
* Function to move a plane from LatLongFrom to LatLongTo
* TODO improve/optimize this function
*/
/*function doMovePlane(index,latitudeTo, longitudeTo) {
	
	var currentplane = planesArray.get(index);

	if(currentplane === undefined)
	{
		console.error("Trying to move a plane that does not exist...");
	}
	else
	{
		//From and to initialization
		var latitudeFrom = currentplane.position.lat();
		var longitudeFrom = currentplane.position.lng();
		var newlat = 0;
		var newlong = 0;

		//Moving a step ahead
		if(latitudeTo > latitudeFrom){
			newlat = latitudeFrom + pas;
		}else{
			newlat = latitudeFrom - pas;
		}

		if(longitudeTo > longitudeFrom){
			newlong = longitudeFrom + pas;
		}else{
			newlong = longitudeFrom - pas;
		}

		if(Math.abs(latitudeFrom - latitudeTo) < pas){
			newlat = latitudeTo;
		}

		if(Math.abs(longitudeFrom - longitudeTo) < pas){
			newlong = longitudeTo;
		}	

		currentplane.position = new google.maps.LatLng(newlat,newlong);
		currentplane.setMap(mymap);

		if( (newlat-latitudeTo) > pas || (newlong-longitudeTo) > pas)
		{
			setTimeout(function(){doMovePlane(index,latitudeTo,longitudeTo);},refreshtime);
		} 
	}
}*/

/*
* Function to test doMovePlane
* A yellow plane moves from France to Spain
*/
/*function testMove()
{
	planesArray.put("0",new google.maps.Marker({
		position: new google.maps.LatLng(48.0,2.0),
		map: mymap,
		flat:true,
		icon:fighter_icon[4],
		title:"plane attacking"
		}));
	doMovePlane("0",40.0,-2.0);
}*/

