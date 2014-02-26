//Nicolas Vailliet
//Map for CodINSA final contest interface
//Plane simulation

/*
	*** TODO ***

*/

//Map
var map;
var center_lat = 48.0;
var center_long = 2.0;
var zoom_level = 6;

//Objects to draw
var basesArray = new Array();	
var planesArray = new Array();

//Initialization	
function initialisationMaps(){

		 // Définition de la position courante
		 var myLatlng = new google.maps.LatLng(center_lat,center_long);
		 
		 // Définitions des options
		 var myOptions = {
			zoom: zoom_level,
			center: myLatlng,
			mapTypeControl: false,
			mapTypeControlOptions: {style: google.maps.MapTypeControlStyle.DROPDOWN_MENU},
			panControl: false,
			zoomControl: false, 
			mapTypeControl: false, 
			scaleControl: false, 
			streetViewControl: false, 
			overviewMapControl: false,
			zoomControlOptions: { style: google.maps.ZoomControlStyle.SMALL	},
			mapTypeId: google.maps.MapTypeId.SATELLITE
		}

		 // On crée notre carte avec options
		 map = new google.maps.Map(document.getElementById("mapcontain"), myOptions);
		  	 
		 //Event sur le zoom de la map
		 /*google.maps.event.addListener(carte, "zoom_changed", function() {
			updateByZoom();	});*/
 
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
		
 }
 
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