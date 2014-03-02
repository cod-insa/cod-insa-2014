$(function () {

 	var input = $('#input');
 
    // if user is running mozilla then use it's built-in WebSocket
    window.WebSocket = window.WebSocket || window.MozWebSocket;

    var connection = new WebSocket('ws://192.168.1.89:14588');

    connection.onopen = function () {
        // connection is opened and ready to use
        console.log("connected");
    };

    connection.onerror = function (error) {
        // an error occurred when sending/receiving data
        console.log("error: "+error);
    };
    
    connection.onclose = function (error) {
        console.log("connection closed");
    };

    connection.onmessage = function (message) {
        // try to decode json (I assume that each message from server is json)
        try {
            var json = JSON.parse(message.data);
        } catch (e) {
            console.log('This doesn\'t look like a valid JSON: ', message.data);
            return;
        }
        console.log("message received");
        // handle incoming message
    };
    
     /*** Send mesage when user presses Enter key*/
	input.keydown(function(e) {
		// send the message as an ordinary text
		connection.send(e.keyCode);
	});
    
});






