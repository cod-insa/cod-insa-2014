package network;

import genbridge.*;

import java.util.List;

import org.apache.thrift.TException;

public class BridgeHandler implements Bridge.Iface{


  public BridgeHandler() {
  }

@Override
public List<Plane> getMyPlanes() throws TException {
	// TODO Auto-generated method stub
	return null;
}

@Override
public List<Plane> getMyBases() throws TException {
	// TODO Auto-generated method stub
	return null;
}

@Override
public boolean addActionToPerform(Action a) throws InvalidActionException,
		TException {
	// TODO Auto-generated method stub
	return false;
}

@Override
public Status getActionsListStatus() throws TException {
	// TODO Auto-generated method stub
	return null;
}

@Override
public int build_plane(int base_id) throws TException {
	// TODO Auto-generated method stub
	return 0;
}
 

}

