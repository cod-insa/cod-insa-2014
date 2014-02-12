package network;

import genbridge.*;

import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TException;

public class BridgeHandler implements Bridge.Iface{


  public BridgeHandler() {
  }

@Override
public int connect(String nom) throws TException {
	// TODO Auto-generated method stub
	return 0;
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

