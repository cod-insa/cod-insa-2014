//Nicolas Vailliet
//Client for CodINSA final contest interface
//Plane simulation

/*
* Plane object: containing more info about each plane
*/
function Plane(m,health,fuel,rotation)//,radar,speed,state)
{
	this.marker = m;
	this.refreshed = false;
	this.health = health;
	this.fuel = fuel;
	this.rotation = rotation;
	//this.radar = radar;
	//this.speed = speed;
	//this.state = state;
}

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

//Hasmap of planes info : key = id
var nbPlanes = 0;
var cleaning = false;
var torem;

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

		for(var j = 0 ; j<json.snap.planes.length ; j++){

			var current_plane = json.snap.planes[j];
			var key = current_plane.ownerid*10000+current_plane.id;
			var getExisting = planesArray.get(key);

			if(getExisting === undefined)
			{
				//console.log("defining");
				planesArray.put(key,new Plane(
							new google.maps.Marker({
							position: new google.maps.LatLng(current_plane.latitude,current_plane.longitude),
							map: mymap,
							flat:true,
							icon:fighter_icon[current_plane.ownerid],
							title:"plane",
							zIndex:200
							}),
							current_plane.health,current_plane.fuel,current_plane.rotation
				));
				//.put(key,new Plane(current_plane.health,current_plane.fuel,current_plane.rotation));//,current_plane.radar,current_plane.speed,current_plane.state));
				nbPlanes++;
			}
			else
			{
				//console.log("updating");
				//getExisting.position = new google.maps.LatLng(current_plane.latitude,current_plane.longitude);
				var infoToUpdate = planesArray.get(key);
				//infoToUpdate.state = current_plane.state;
				//infoToUpdate.speed = current_plane.speed;
				infoToUpdate.refreshed = true;
				infoToUpdate.rotation = current_plane.rotation;
				infoToUpdate.fuel = current_plane.fuel;
				//infoToUpdate.radar = current_plane.radar;
				infoToUpdate.health = current_plane.health;
				getExisting.title = "Health:"+infoToUpdate.health+"\n"+"Fuel:"+infoToUpdate.fuel;
				//getExisting.setMap(mymap);
				updatePosition(key,current_plane.latitude, current_plane.longitude);
				//doMovePlane(key,current_plane.latitude, current_plane.longitude);
			}
		}


		if(cleaning)
		{
			//cleaning plane list
			var i = 0;
			for(i=0;i<torem.length;i++)
			{
				//planesArray.del(torem[i]);	//TODO MEMORY LEAK
			}
		}

		//Removing dead planes
		torem = new Array();

		planesArray.each(function(key,plane)
		{
			if(plane.refreshed)
			{
				plane.refreshed = false;
			}
			else
			{
				console.log("delete this plane");
				plane.marker.setMap(null);
				torem.push(key);
			}//TODO check if memory leak...
		});
		if(!cleaning)
			cleaning = true;
	
		//TODO
		// orientation, bullets...

		

		canProcess = true;
	}
	else
	{
		console.log("frame ignored");
	}
}
