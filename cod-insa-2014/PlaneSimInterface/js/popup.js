


function doCheckAndConnect()
{
	$('#connectmodal').modal('hide');
	var ip = $('#ipad').val();
	
	//window.alert(ip);
	initServerConnection(ip);
}

function showModal()
{
	$('#connectmodal').modal('show');
}

function displayError()
{
	$('#errormsg').html('<p style="color:red;">Error: This server does not respond!</p>');

}

showModal();