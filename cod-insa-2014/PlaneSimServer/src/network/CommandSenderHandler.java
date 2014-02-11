package network;

import genbridge.*;

import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TException;

import com.sun.corba.se.impl.activation.CommandHandler;

public class CommandSenderHandler implements CommandSender.Iface{


  public CommandSenderHandler() {
  }

@Override
public Response addActionToPerform(Action act, int idConnection)
		throws TException {
	// TODO Auto-generated method stub
	return new Response(0,"ok"); // 0 : ok, -1 : timeout, -2 : param errors
}




}

