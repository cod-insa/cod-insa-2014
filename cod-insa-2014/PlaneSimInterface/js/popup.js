/** Popup to ask for server's IP address */
/** NV for COdinSA 2014 **/

function doCheckAndConnect()
{
	$('#connectmodal').modal('hide');
	var ip = $('#ipad').val();
	initServerConnection(ip);
}

function showModal()
{
	$('#connectmodal').modal('show');
	initialisationMaps();
}

function displayError()
{
	$('#errormsg').html('<p style="color:red;">Error: This server does not respond!</p>');
}

function enterKey(e) {
    if (e.keyCode == 13) {
	doCheckAndConnect();
        return false;
    }
}
