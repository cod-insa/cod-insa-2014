//Nicolas Vailliet
//Client for CodINSA final contest interface
//Plane simulation

/*
* Plane object: containing more info about each plane
*/
function Plane(m,rot)//,radar,speed,state)
{
	this.mark = m;
	this.rotation = rot;
}

/*
* Base object
*/
function Base(marker, oid, name)
{
	this.marker = marker;
	this.owner = oid;
	this.name = name;
}

/*
* Synchronization : to not take in account a frame if we have not finished to process the previous one yet
*/
var canProcess = true;

/**
* Process data about the map
*/
function processDataMap(json)
{
	if(canProcess)
	{
		canProcess = false;

		//basic map info
		map_name = json.map.name;
		base_count = json.map.bazc;
		center_lat = json.map.lati;
		center_long = json.map.longi;
		zoom_level = json.map.zoom;
		updateMapWithInfoFromServer();

		//get numbers of players to prepare interface (score table)
		nbPlayers = json.nbp;
		console.log(nbPlayers+' are playing here!');
		initScoreTable();

		//adding bases to this map
		for(var i = 0 ; i<base_count ; i++){
			//console.log("draw base "+current_base.cityname);
			var current_base = json.map.bases[i];

			basesArray.push(new Base(
				new google.maps.Marker({
				position: new google.maps.LatLng(current_base.lati,current_base.longi),
				map: mymap,
				flat:true,
				icon:base_icon[current_base.oid],
				title:"Ville: "+current_base.cname+"\nOccupant: gaia"+"\nMilice: "+current_base.milit+"\nFuel: "+current_base.fuel,
				zIndex:1000}),
				current_base.oid,
				current_base.cname)
			);

			google.maps.event.addListener(basesArray[i], 'click', function() {
				showBaseInfo(basesArray[i].name,basesArray[i].marker.title);
			  });

		}

		canProcess = true;
	}
	else
	{
		console.log("frame ignored");
	}
}

//Some variable to not get lost
//var nbPlanes = 0;
//var keys = new Array();
//var torem;

/*
* Update plane info and position every time we receive a new frame from the server
*/
function processPlaneBaseInfo(json)
{
	if(canProcess)
	{
		canProcess = false;

		//Look at ownerid to refresh bases colors
		for(var i = 0 ; i<base_count ; i++){
			if(basesArray[i].owner != json.snap.bases[i].oid)
			{
				basesArray[i].marker.icon = base_icon[json.snap.bases[i].oid];
				basesArray[i].owner = json.snap.bases[i].oid;
				basesArray[i].marker.setMap(mymap);
			}
			if(basesArray[i].owner == 0)
				basesArray[i].marker.setTitle("Ville: "+basesArray[i].name+"\nOccupant: gaia"+"\nMilice: "+json.snap.bases[i].milit+"\nFuel: "+json.snap.bases[i].fuel);
			else
				basesArray[i].marker.setTitle("Ville: "+basesArray[i].name+"\nOccupant: "+names[json.snap.bases[i].oid-1]+"\nMilice: "+json.snap.bases[i].milit+"\nFuel: "+json.snap.bases[i].fuel);
		}

		planesArray.forEach(function(plane){
			//plane.mark.setPosition(new google.maps.LatLng(0.0,0.0));
			plane.mark.setMap(null);
		});
		planesArray = [];

		for(var j = 0 ; j<json.snap.planes.length ; j++){

			var current_plane = json.snap.planes[j];
			var icon_to_use;

			if(current_plane.type == "MILITARY")	//if military
			{
				icon_to_use = fighter_icon[current_plane.oid];
			}
			else				//else commercial
			{
				icon_to_use = civilian_icon[current_plane.oid];
			}

			planesArray.push(new Plane(
					new google.maps.Marker({
							position: new google.maps.LatLng(current_plane.lati,current_plane.longi),
							map: mymap,
							flat:true,
							icon:icon_to_use,
							title:"Health:"+current_plane.health+"\n"+"Fuel:"+current_plane.fuel,
							zIndex:2000
						}),
					current_plane.rota
			));

		}//for planes

			/*var key = current_plane.id;
			keys.push(key);

			var getExisting = planesArray[key];
			if(getExisting === undefined)
			{*/
				//console.log("new plane");
				//planesArray[key] = new Plane(
				/*var newplane = 		new google.maps.Marker({
								position: new google.maps.LatLng(current_plane.latitude,current_plane.longitude),
								map: mymap,
								flat:true,
								icon:fighter_icon[current_plane.ownerid],
								title:"plane",
								zIndex:200
							});*/
							/*,
							current_plane.health,
							current_plane.fuel,
							current_plane.rotation
				);*/
				//nbPlanes++;
			/*}
			else
			{*/
				//console.log("updating plane");
				/*getExisting.refreshed = true;
				getExisting.rotation = current_plane.rotation;
				getExisting.fuel = current_plane.fuel;
				getExisting.health = current_plane.health;
				getExisting.mark.title = "Health:"+getExisting.health+"\n"+"Fuel:"+getExisting.fuel;
				updatePosition(key,current_plane.latitude,current_plane.longitude);
			}*/
		

		//Removing dead planes
		/*torem = new Array();
		keys.forEach(function(ind) {
		    if(planesArray[ind].refreshed)
			{
				planesArray[ind].refreshed = false;
			}
			else
			{
				console.log("delete this plane");
				planesArray[ind].mark.setMap(null);
				torem.push(key);
			}
		});*/

		//cleaning key list
		/*torem.forEach(function(ind) {
			keys.remove(ind);
		});*/

		//bullets
		/*bullets.forEach(function(bull){
			bull.setPosition(new google.maps.LatLng(0.0,0.0));
			bull.setMap(null);
		});
		bullets = [];

		for(var j = 0 ; j<json.snap.bullets.length ; j++){

			var current_bull = json.snap.bullets[j];

			bullets.push(new google.maps.Marker({
							position: new google.maps.LatLng(current_bull.lati,current_bull.longi),
							map: mymap,
							flat:true//,
							icon:{
							      path: google.maps.SymbolPath.CIRCLE,
							      scale: 5
							    },
							//title:"Health:"+current_plane.health+"\n"+"Fuel:"+current_plane.fuel,
							zIndex:3000
		}));
		}//bullets


		//debris
		debris.forEach(function(deb){
			//deb.setPosition(new google.maps.LatLng(0.0,0.0));
			deb.setMap(null);
		});
		debris = [];

		for(var j = 0 ; j<json.snap.debris.length ; j++){

			var current_deb = json.snap.debris[j];

			debris.push(new google.maps.Marker({
							position: new google.maps.LatLng(current_deb.lati,current_deb.longi),
							map: mymap,
							flat:true,
							icon:{
							      path: google.maps.SymbolPath.BACKWARD_OPEN_ARROW,
							      scale: 5
							    },
							//title:"Health:"+current_plane.health+"\n"+"Fuel:"+current_plane.fuel,
							zIndex:3000
		}));
		}//debris*/


		canProcess = true;
	}
	else
	{
		console.log("frame ignored");
	}
}

