package network;

import game.World;
import game.World.Snapshot;
import genbridge.Response;
import model.BaseModel;
import model.PlaneModel;

import command.Command;
import command.LandCommand;
import command.MoveCommand;
import command.WaitCommand;
import common.ListView;

public abstract class CommandChecker {

	public static Response checkMoveCommand(MoveCommand mc, Snapshot s)
	{
		boolean planeFound = false;
		int i = 0;
		ListView<PlaneModel.View> planes = s.planes.view();
		while (!planeFound && i < planes.size())
			if (planes.get(i++).id() == mc.planeId)
				planeFound = true;
		if (!planeFound)
			return new Response(Command.ERROR_COMMAND,"The plane n°"+mc.planeId+" doesn't exist. Command ignored.");
		
		if (mc.destination.x() < 0 || 
				mc.destination.x() > World.WIDTH ||
				mc.destination.y() < 0 ||
				mc.destination.y() > World.HEIGHT)
			return new Response(Command.ERROR_COMMAND,"The coord ("+mc.destination.x()+","+mc.destination.y()+") is not valid. Command ignored.");
		
		// plane id is ok, coords are ok
		return new Response(Command.SUCCESS,"");
	}
	
	public static Response checkWaitCommand(WaitCommand wc, Snapshot s)
	{
		boolean planeFound = false;
		int i = 0;
		ListView<PlaneModel.View> planes = s.planes.view();
		while (!planeFound && i < planes.size())
			if (planes.get(i++).id() == wc.planeId)
				planeFound = true;
		if (!planeFound)
			return new Response(Command.ERROR_COMMAND,"The plane n°"+wc.planeId+" doesn't exist. Command ignored.");
		
		return new Response(Command.SUCCESS,"");
	}
	
	public static Response checkLandCommand(LandCommand lc, Snapshot s)
	{
		boolean planeFound = false;
		int i = 0;
		ListView<PlaneModel.View> planes = s.planes.view();
		while (!planeFound && i < planes.size())
			if (planes.get(i++).id() == lc.planeId)
				planeFound = true;
		if (!planeFound)
			return new Response(Command.ERROR_COMMAND,"The plane n°"+lc.planeId+" doesn't exist. Command ignored.");
		
		boolean baseFound = false;
		i = 0;
		ListView<BaseModel.View> bases = s.bases.view();
		while (!baseFound && i < bases.size())
			if (bases.get(i++).id() == lc.planeId)
				baseFound = true;
		if (!baseFound)
			return new Response(Command.ERROR_COMMAND,"The base n°"+lc.planeId+" doesn't exist. Command ignored.");
		
		return new Response(Command.SUCCESS,"");
	}
}
