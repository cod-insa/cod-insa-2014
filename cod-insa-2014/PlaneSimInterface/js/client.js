//Nicolas Vailliet
//Client for CodINSA final contest interface
//Plane simulation

//last JSON received from the server
var json;

//To remember how many teams are playing
var nbPlayers = 0;

//Player names
var names = new Array();

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

		if(json != 0 && json.map)
		{
			setClock(0,0,0);
			updateClock();
			processDataMap(json);
		}

		if(json != 0 && json.snap)
		{
			//handling time
			var time = json.snap.time;
			var hh = Math.floor(time/3600);
			var mm = Math.floor(time/60) - (hh*60);
			var ss = time - hh*3600 - mm*60;
			setClock(hh,mm,ss);

			//refresh teams' scores
			var ind = 0;
			for(ind = 0 ; ind < nbPlayers ; ind++){
			document.getElementById("t"+(ind+1)).innerHTML = json.snap.players[ind].name + ":" + json.snap.players[ind].score;
			names.push(json.snap.players[ind].name);
			}

			//Look at planes
			processPlaneBaseInfo(json);

		}//if snap to process

	};//onmessage

};//initServerConnection
