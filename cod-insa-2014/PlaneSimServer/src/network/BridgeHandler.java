package network;

import genbridge.*;

import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TException;

public class BridgeHandler implements Bridge.Iface{


  public BridgeHandler() {
  }

@Override
public List<Plane> getMyPlanes() throws TException {
	System.out.println("getMyPlanes called");
	// TODO Auto-generated method stub
	return new ArrayList<Plane>();
}

@Override
public List<Plane> getMyBases() throws TException {
	System.out.println("getMyBases called");
	// TODO Auto-generated method stub
	return new ArrayList<Plane>();
}

@Override
public boolean addActionToPerform(Action a) throws InvalidActionException,
		TException {
	System.out.println("addActionToPerform called");
	// TODO Auto-generated method stub
	return false;
}

@Override
public Status getActionsListStatus() throws TException {
	System.out.println("getActionsListStatus called");
	// TODO Auto-generated method stub
	return Status.WAITING;
}

@Override
public int build_plane(int base_id) throws TException {
	System.out.println("getActionsListStatus called");
	// TODO Auto-generated method stub
	return 0;
}
 

}

