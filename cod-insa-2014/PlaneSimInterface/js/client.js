//Nicolas Vailliet
//Client for CodINSA final contest interface
//Plane simulation

//Two snapshot from the server to perform smooth animation
var lastSnap;
var nextSnap;

//last JSON received from the server
var json;

//URL server
var urlserver = "localhost:14588";

//Connect to the server and handle messages coming from it
var initServerConnection = function () {

    // if user is running mozilla then use its built-in WebSocket
    window.WebSocket = window.WebSocket || window.MozWebSocket;
    var connection = new WebSocket('ws://'+urlserver);


    connection.onopen = function () {
        // connection is opened and ready to use
        console.log("connected");
    };

    connection.onerror = function (error) {
        // an error occurred when sending/receiving data
        console.log("error: "+error);
	window.alert("Oops "+urlserver+" does not respond!");
	initialisationMaps();
    };
    
    connection.onclose = function (error) {
        console.log("connection closed");
	window.alert("Connection with "+urlserver+" has been closed!");
	clearTimeout(timeout);
    };

    connection.onmessage = function (message) {
        // try to decode json (I assume that each message from server is json)
	console.log("message received");
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

			//basic map info
			map_name = json.map.name;
			base_count = json.map.basecount;
			center_lat = json.map.latitude;
			center_long = json.map.longitude;
			zoom_level = json.map.zoom;
			initialisationMaps();
			
			//adding bases to this map
			for(var i = 0 ; i<base_count ; i++){
				
				var current_base = json.map.bases[i];
				//console.log("draw base "+current_base.cityname);
				//console.log(base_icon[current_base.ownerid]);
				basesArray.push(new google.maps.Marker({
		    			position: new google.maps.LatLng(current_base.latitude,current_base.longitude),
		    			map: mymap,
		    			icon:base_icon[current_base.ownerid],
		    			title:current_base.cityname
				}));
			}
		}

		if(json.snap && !lastSnap)
		{
			lastSnap = json;

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

			//Look at ownerid to refresh bases colors
			 for(var i = 0 ; i<base_count ; i++){
				basesArray[i].icon = base_icon[json.snap.bases[i].ownerid];
			}

			 //Look at planes
			 for(var j = 0 ; j<json.snap.planes.length ; j++){

				var current_plane = json.snap.planes[j];
				var key = current_plane.ownerid*10000+current_plane.id;
				var getExisting = planesArray.get(key);
				
				if(getExisting === undefined)
				{
					//console.log("defining");
					planesArray.put(key,new google.maps.Marker({
		    			position: new google.maps.LatLng(current_plane.latitude,current_plane.longitude),
		    			map: mymap,
		    			icon:fighter_icon[current_plane.ownerid],
		    			title:"plane attacking"
					}));

				}
				else
				{
					//console.log("updating");
					getExisting.position = new google.maps.LatLng(current_plane.latitude,current_plane.longitude);
					getExisting.title = "plane updated";
					getExisting.setMap(mymap);
				}

			}

				//TODO
				// orientation, bullets...

			 lastSnap = nextSnap;
		}

		


        }

	//console.log(message.data);
    };
    
	/*input.keydown(function(e) {
		// send the message as an ordinary text
		//connection.send(e.keyCode);
	});*/
    
};

initServerConnection();
