package network;

import genbridge.*;

import java.util.ArrayList;
import java.util.List;
import org.apache.thrift.TException;
import players.PlayerManager;

public class BridgeHandler implements Bridge.Iface{

	private PlayerManager playerManager;
	private List<Integer> authorizedIDs;
	
  public BridgeHandler(PlayerManager pmanager) {
	  this.playerManager = pmanager;
	  this.authorizedIDs = new ArrayList<Integer>();
  }

@Override
public int connect(String nom) throws TException {

	int id = playerManager.addNewPlayer(nom);
	
	if(id != -1)
		authorizedIDs.add(id);
		
	return id;
}

@Override
public Data retrieveData(int idConnection) throws TException {
	// TODO Auto-generated method stub
	return new Data();
}

@Override
public Response addActionToPerform(int idConnection, Action act)
		throws TException {
	// TODO Auto-generated method stub
	return null;
}


}

