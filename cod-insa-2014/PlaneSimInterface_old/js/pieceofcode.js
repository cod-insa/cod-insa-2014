	// To add the marker to the map, use the 'map' property
		/*var marker = new google.maps.Marker({
		    position: new google.maps.LatLng(48.5632,-1.5896),
		    map: map,
		    icon:plane1,
		    title:"Rennes-Plane1"
		});*/

		// To add the marker to the map, use the 'map' property
		/*var marker = new google.maps.Marker({
		    position: new google.maps.LatLng(48.2345,1.8963),
		    map: map,
		    icon:plane2,
		    title:"Rennes-Plane2"
		});*/
		
		// To add the marker to the map, use the 'map' property
		/*var marker = new google.maps.Marker({
		    position: new google.maps.LatLng(48.120955,-1.635003),
		    map: map,
		    icon:base,
		    title:"Rennes-Home"
		});*/

 
 
 
 
 	
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

