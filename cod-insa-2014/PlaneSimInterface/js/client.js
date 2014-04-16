//Nicolas Vailliet
//Client for CodINSA final contest interface
//Plane simulation

//last JSON received from the server
var json;

//Two snapshot from the server to perform smooth animation
var lastSnap;
var nextSnap;

//Time before the two last snapshots
var lastTime;
var newTime;

//To remember how many teams are playing
var nbPlayers = 0;

//Hasmap of planes info : key = id
var planesInfo = new HashMap();

//plane keys to figure out which ones are dead (to avoid walking deads :p)
var keySetOld = new Array();
var keySetNew = new Array();

/*
* Plane object: containing more info about each plane
*/
function Plane(health,fuel,rotation)//,radar,speed,state)
{
	this.health = health;
	this.fuel = fuel;
	this.rotation = rotation;
	//this.radar = radar;
	//this.speed = speed;
	//this.state = state;
}

/*
* Connect to the server and handle messages coming from it
* @param ipad : the ip address of the server to connect to
*/
var initServerConnection = function (ipad) {

	urlserver = ipad + ":14588";

	window.WebSocket = window.WebSocket || window.MozWebSocket;
	var connection = new WebSocket('ws://'+urlserver);

	connection.onopen = function () {
		console.log("connected");
	};

	connection.onerror = function (error) {
		console.log("error: "+error);
		clearTimeout(timeout);
		window.setTimeout(displayError,500);
		window.setTimeout(showModal,500);
	};

	connection.onclose = function (error) {
		console.log("connection closed");
		clearTimeout(timeout);
		window.setTimeout(displayError,500);
		window.setTimeout(showModal,1000);

	};

	connection.onmessage = function (message) {
		//console.log("message received");
		//console.log(message.data);

		//JSON parsing
		json = 0;        
		try {
			json = JSON.parse(message.data);
		} catch (e) {
			console.log('This doesn\'t look like a valid JSON: ', message.data);
			return;
		}


		if(json != 0)
		{

		if(json.map)
		{
		setClock(0,0,0);
		updateClock();

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

		var current_base = json.map.bases[i];
		//console.log("draw base "+current_base.cityname);
		//console.log(base_icon[current_base.ownerid]);
		basesArray.push(new google.maps.Marker({
		position: new google.maps.LatLng(current_base.lati,current_base.longi),
		map: mymap,
		flat:true,
		icon:base_icon[current_base.oid],
		title:current_base.cname,
		zIndex:100
		}));
		}

		}

		if(json.snap && !lastSnap)
		{
		lastSnap = json;
		var ddd = new Date();
		lastTime = ddd.getTime();

		//Look at ownerid to refresh bases colors
		/* for(var i = 0 ; i<base_count ; i++){
		basesArray[i].icon = base_icon[json.snap.bases[i].ownerid];
		}*/

		//Look at planes
		/*for(var j = 0 ; j<json.snap.planes.length ; j++){

		var current_plane = json.snap.planes[j];
		planesArray.put(current_plane.ownerid*10000+current_plane.id,new google.maps.Marker({
		position: new google.maps.LatLng(current_plane.latitude,current_plane.longitude),
		map: mymap,
		icon:fighter_icon[current_plane.ownerid],
		title:"plane attacking"
		}));
		}*/

		}

		if(json.snap && lastSnap)
		{
		nextSnap = json;
		var ddd = new Date();
		newTime = ddd.getTime();

		//handling time
		var time = json.snap.time;
		var hh = Math.floor(time/3600);
		var mm = Math.floor(time/60) - (hh*60);
		var ss = time - hh*3600 - mm*60;
		setClock(hh,mm,ss);

		//refresh teams' scores
		var ind = 0;
		for(ind = 0 ; ind < nbPlayers ; ind++)
		document.getElementById("t"+(ind+1)).innerHTML = json.snap.players[ind].name + ":" + json.snap.players[ind].score;



		//Look at ownerid to refresh bases colors
		for(var i = 0 ; i<base_count ; i++){
		//console.log(i);
		basesArray[i].icon = base_icon[json.snap.bases[i].ownerid];
		}

		//Look at planes
		for(var j = 0 ; j<json.snap.planes.length ; j++){

		var current_plane = json.snap.planes[j];
		var key = current_plane.ownerid*10000+current_plane.id;
		keySetNew.push(key);
		var getExisting = planesArray.get(key);

		if(getExisting === undefined)
		{
		//console.log("defining");
		planesArray.put(key,new google.maps.Marker({
		position: new google.maps.LatLng(current_plane.latitude,current_plane.longitude),
		map: mymap,
		flat:true,
		icon:fighter_icon[current_plane.ownerid],
		title:"plane",
		zIndex:200
		}));

		planesInfo.put(key,new Plane(current_plane.health,current_plane.fuel,current_plane.rotation));//,current_plane.radar,current_plane.speed,current_plane.state));

		}
		else
		{
		//console.log("updating");
		/*getExisting.position = new google.maps.LatLng(current_plane.latitude,current_plane.longitude);
		*/
		var infoToUpdate = planesInfo.get(key);
		//infoToUpdate.state = current_plane.state;
		//infoToUpdate.speed = current_plane.speed;
		infoToUpdate.rotation = current_plane.rotation;
		infoToUpdate.fuel = current_plane.fuel;
		//infoToUpdate.radar = current_plane.radar;
		infoToUpdate.health = current_plane.health;
		getExisting.title = "Health:"+infoToUpdate.health+"\n"+"Fuel:"+infoToUpdate.fuel;
		//getExisting.setMap(mymap);

		doMovePlane(key,current_plane.latitude, current_plane.longitude);


		}
		}

		//Removing dead planes
		var exist = false;
		for(var i = 0 ; i<keySetOld.length ; i++)
		{
		var index = keySetOld[i];

		for(var j = 0 ; j<keySetNew.length ; j++)
		{
		if(index == keySetNew[j])
		{	exist = true;
		break;
		}
		}	
		if(!exist)
		{
		console.log("Delete this plane!");
		var plane = planesArray.get(index);
		plane.setMap(null);
		plane.setIcon(null);
		planesArray.del(index);
		planesInfo.del(index);
		}	
		}


		keySetOld = keySetNew;
		keySetNew = new Array();

		//TODO
		// orientation, bullets...

		lastSnap = nextSnap;
		}

		}

		lastTime = newTime;
		

	};//onmessage

};//initServerConnection
