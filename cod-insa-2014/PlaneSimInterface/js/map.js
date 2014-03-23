//Nicolas Vailliet
//Map for CodINSA final contest interface
//Plane simulation

/**
*	TODO
*	get map info from server
*	add planes and bases images
*	lock map position
**/

//Map info
var map;

//France
var center_lat = 48.138;
var center_long = 2.0;
var zoom_level = 6;

//US
//var center_lat = 39.0;
//var center_long = -94.0;
//var zoom_level = 5;


//Objects to draw
var basesArray = new Array();	
var planesArray = new Array();

//Images
var plane1 = '../img/plane1.png';
var plane2 = '../img/plane2.png';
var base = '../img/home.png';

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
		map = new google.maps.Map(document.getElementById("mapcontain"), myOptions);
		map.setOptions({draggable: false, zoomControl: false, scrollwheel: false, disableDoubleClickZoom: true});
		google.maps.event.clearInstanceListeners(map);
		
		// To add the marker to the map, use the 'map' property
		var marker = new google.maps.Marker({
		    position: new google.maps.LatLng(48.5632,-1.5896),
		    map: map,
		    icon:plane1,
		    title:"Rennes-Plane1"
		});

		// To add the marker to the map, use the 'map' property
		var marker = new google.maps.Marker({
		    position: new google.maps.LatLng(48.2345,1.8963),
		    map: map,
		    icon:plane2,
		    title:"Rennes-Plane2"
		});
		
				// To add the marker to the map, use the 'map' property
		var marker = new google.maps.Marker({
		    position: new google.maps.LatLng(48.120955,-1.635003),
		    map: map,
		    icon:base,
		    title:"Rennes-Home"
		});

 }
 
 
 
 
 		//Paramètres communs des cercles
		/*var optionsCercles = {
			center: maLatlng,
			clickable: false,
			editable: false,
			fillColor: "rgb("+r+","+g+","+b+")",
			fillOpacity: 0.5,
			map: carte,
			radius: 10,
			strokeWeight:0,
			visible:false,
			zIndex:0.9
		}*/
					 		
		//Création de max_post_display cercles
		/*for(var i = 0 ; i<max_post_display ; i++){
					cercle = new google.maps.Circle(optionsCercles);
					circlesArray[i] = cercle;
		}*/
		
 //Update du rayon de recherche des posts suivant zoom de la carte
 /*function updateByZoom(){
	window.mapsLooper.stop();
	var zoom = carte.getZoom();
	if(zoom>10) rayon = 10*kilometre*(20-zoom); else rayon = 10000*kilometre;
	window.mapsLooper.start();
}*/
 
 //Ecran d'attente de chargement de la page
 /*function chargement(){
	var contenantMap = getElementById("maptemplate");
	var contenantLoad = getElementById("loadtemplate");
	if( contenantMap && contenantLoad ){
	contenantLoad.setAttribute("style","display:none");
	contenantMap.removeAttribute("style");
	}
 }*/
 
 		 // event on zooming
		 /*google.maps.event.addListener(carte, "zoom_changed", function() {
			updateByZoom();	});*/
			
 //Taille, couleur et transparance suivant date et rank.
 /*function optionsByDateAndRank(rank,date,index,circle){
	
	//Date by Tranparency 
	//circle.fillOpacity = 1-(date/time);
	//Paramètre de bordure Bordure
	//circle.strokeWeight = 0.5;//px
	//circle.strokeColor = "black";
	
	//Initialement : radius = 10m
	circle.radius = 10*(rank+1);
	
	//Couleur de remplissage
	//Couleur rouge du site : 206 30 66
	index = Math.floor(index/10);
	var red = r;
	var green = g;
	var blue = b;
	if(red+index>255) red=255; else red+=index;
	if(green+index>255) green=255; else green+=index;
	if(blue+index>255) blue=255; else blue+=index;
	circle.fillColor = "rgb("+red+","+green+","+blue+")";
	//alert("rgb("+red+","+green+","+blue+")");
	circle.visible = true;
 }*/
