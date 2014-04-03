package control;

import game.World;
import genbridge.CommandData;
import genbridge.CoordData;
import genbridge.LandCommandData;
import genbridge.MoveCommandData;
import genbridge.PlaneCommandData;
import genbridge.Response;
import genbridge.WaitCommandData;
import model.Base;
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
	
	public static Plane.FullView findPlaneById(int idPlane, World.Snapshot s) {
		Plane.FullView plane = null;
		int i = 0;
		ListView<Plane.FullView> planes = s.planes.view();
		while (plane == null && i < planes.size())
		{
			Plane.FullView curPlane = planes.get(i);
			if (curPlane.id() == idPlane)
				plane = curPlane;
		}
		return plane;
	}
	
	public static Base.View findBaseById(int idBase, World.Snapshot s) {
		Base.View base = null;
		int i = 0;
		ListView<Base.View> bases = s.bases.view();
		while (base == null && i < bases.size())
		{
			Base.View curBase = bases.get(i);
			if (curBase.id() == idBase)
				base = curBase;
		}
		return base;
	}
	
	public static boolean checkCoord(CoordData coord, World.Snapshot s)
	{
		return coord.x > 0 && 
				coord.x < s.width &&
				coord.y > 0 &&
				coord.y < s.height;
	}
	
	// Errors
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
	static Couple<Nullable<Command>, Response> baseIdError(int idBase, World.Snapshot s) {
		return new Couple<>(
				new Nullable<Command>(),
				new Response(Command.ERROR_COMMAND, "Cannot find base of id "+idBase)
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

		Plane.FullView p = findPlaneById(data.pc.idPlane, s);

		if (p == null)
			return planeIdError(data.pc.idPlane, s);
		
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

		Plane.FullView p = findPlaneById(data.pc.idPlane, s);

		if (p == null)
			return planeIdError(data.pc.idPlane, s);
		
		return new Couple<>(
				new Nullable<Command>(new WaitCommand(p)),
				new Response(Command.SUCCESS, "")
		);
	}
	

	static public Couple<Nullable<Command>,Response> make(LandCommandData data, World.Snapshot s) {
		
		// check id
		if (!checkFrameId(data.pc.c, s))
			return frameIdError(data.pc.c, s);

		// check plane
		Plane.FullView p = findPlaneById(data.pc.idPlane, s);
		if (p == null)
			return planeIdError(data.pc.idPlane, s);

		// check base
		Base.View b = findBaseById(data.idBase, s);
		if (b == null)
			return baseIdError(data.idBase, s);
		
		// Everything all right
		return new Couple<>(
				new Nullable<Command>(new LandCommand(p, b)),
				new Response(Command.SUCCESS, "")
		);
	}

}









