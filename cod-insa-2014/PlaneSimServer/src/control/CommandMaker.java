package control;

import game.World;
import genbridge.CommandData;
import genbridge.CoordData;
import genbridge.LandCommandData;
import genbridge.MoveCommandData;
import genbridge.PlaneCommandData;
import genbridge.Response;
import genbridge.WaitCommandData;
import model.Coord;
import model.Plane;
import command.Command;
import command.LandCommand;
import command.MoveCommand;
import command.WaitCommand;
import common.Couple;
import common.ListView;
import common.Nullable;

/**
 * Also performs the checks on commands
 */
public class CommandMaker {

	public static Coord make(CoordData data) {
		return new Coord(data.x, data.y);
	}

	public static boolean checkFrameId(CommandData data, World.Snapshot s) {
		return data.numFrame == s.id;
	}
	public static boolean checkIdPlane(int idPlane, World.Snapshot s) {
		return idPlane < s.planes.view.size() && idPlane > 0;
	}
	public static boolean checkCoord(CoordData coord, World.Snapshot s)
	{
		return coord.x > 0 && 
				coord.x < s.width &&
				coord.y > 0 &&
				coord.y < s.height;
	}
	
	
	static Couple<Nullable<Command>, Response> frameIdError(CommandData data, World.Snapshot s) {
		return new Couple<>(
				new Nullable<Command>(),
				new Response(Command.ERROR_TIME_OUT, "Current frame id is "+s.id+" command frame id is "+data.numFrame)
		);
	}
	
	static Couple<Nullable<Command>, Response> planeIdError(int idPlane, World.Snapshot s) {
		return new Couple<>(
				new Nullable<Command>(),
				new Response(Command.ERROR_COMMAND, "Cannot find plane of id "+idPlane)
		);
	}
	
	static Couple<Nullable<Command>, Response> coordError(CoordData coord, World.Snapshot s) {
		return new Couple<>(
				new Nullable<Command>(),
				new Response(Command.ERROR_COMMAND,"The coord ("+coord.x+","+coord.y+") are not valid."));
	}
	
	static public Couple<Nullable<Command>, Response> make(MoveCommandData data, World.Snapshot s) {
	
		if (!checkFrameId(data.pc.c, s))
			return frameIdError(data.pc.c, s);		

		if (!checkIdPlane(data.pc.idPlane, s))
			return planeIdError(data.pc.idPlane, s);
		
		Plane.FullView p = s.planes.view.get(data.pc.idPlane);
		
		if (!checkCoord(data.posDest, s))
			return coordError(data.posDest, s);
		
		return new Couple<>(
			new Nullable<Command>(new MoveCommand(p, make(data.posDest).view())),
			new Response(Command.SUCCESS, "")
		);
	}

	static public Couple<Nullable<Command>,Response> make(WaitCommandData data, World.Snapshot s) {

		if (!checkFrameId(data.pc.c, s))
			return frameIdError(data.pc.c, s);

		if (!checkIdPlane(data.pc.idPlane, s))
			return planeIdError(data.pc.idPlane, s);

		return new Couple<>(
				new Nullable<Command>(new WaitCommand(data.pc.idPlane)),
				new Response(Command.SUCCESS, "")
		);
	}
	

	static public Couple<Nullable<Command>,Response> make(LandCommandData data, World.Snapshot s) {
		
		if (!checkFrameId(data.pc.c, s))
			return frameIdError(data.pc.c, s);

		// TODO checks

		return new Couple<>(
				new Nullable<Command>(new LandCommand(data.pc.idPlane, data.idBase)),
				new Response(Command.SUCCESS, "")
		);
	}

}









