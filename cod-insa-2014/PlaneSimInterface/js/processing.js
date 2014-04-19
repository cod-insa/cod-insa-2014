//Nicolas Vailliet
//Client for CodINSA final contest interface
//Plane simulation

/*
* Plane object: containing more info about each plane
*/
/*function Plane(m,health,fuel,rotation)//,radar,speed,state)
{
	this.mark = m;
	//this.refreshed = false;
	//this.health = health;
	//this.fuel = fuel;
	//this.rotation = rotation;
	//this.radar = radar;
	//this.speed = speed;
	//this.state = state;
}*/

/*
* Base object
*/
function Base(marker, oid)
{
	this.marker = marker;
	this.owner = oid;
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
				title:current_base.cname,
				zIndex:100}),
				current_base.oid
				)
			);
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
			if(basesArray[i].owner != json.snap.bases[i].ownerid)
			{
				basesArray[i].marker.icon = base_icon[json.snap.bases[i].ownerid];
				basesArray[i].marker.setMap(mymap);
				basesArray[i].owner = json.snap.bases[i].ownerid;
			}
		}

		planesArray.forEach(function(plane){
		plane.setMap(null);
		});
		planesArray = [];

		for(var j = 0 ; j<json.snap.planes.length ; j++){

			var current_plane = json.snap.planes[j];

			planesArray.push(new google.maps.Marker({
							position: new google.maps.LatLng(current_plane.latitude,current_plane.longitude),
							map: mymap,
							flat:true,
							icon:fighter_icon[current_plane.ownerid],
							title:"Health:"+current_plane.health+"\n"+"Fuel:"+current_plane.fuel,
							zIndex:200
			}));

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
		}

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

		//TODO
		// orientation, bullets...

		

		canProcess = true;
	}
	else
	{
		console.log("frame ignored");
	}
}
