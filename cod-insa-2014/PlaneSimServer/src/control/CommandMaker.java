package control;

import game.World;
import genbridge.CommandData;
import genbridge.CoordData;
import genbridge.LandCommandData;
import genbridge.MoveCommandData;
import genbridge.Response;
import genbridge.WaitCommandData;
import model.Coord;
import model.Plane;

import command.Command;
import command.LandCommand;
import command.MoveCommand;
import command.WaitCommand;
import common.Couple;
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
	static Couple<Nullable<Command>, Response> frameIdError(CommandData data, World.Snapshot s) {
		return new Couple<>(
				new Nullable<Command>(),
				new Response(Command.ERROR_TIME_OUT, "Current frame id is "+s.id+" command frame id is "+data.numFrame)
		);
	}
	
	static public Couple<Nullable<Command>, Response> make(MoveCommandData data, World.Snapshot s) {
	
		if (!checkFrameId(data.pc.c, s))
			return frameIdError(data.pc.c, s);
		
//		try {
//			get plane using data.pc.idPlane;
//		} catch() { // when plane is not found
//			return error
//		}

//		Plane.FullView p = s.planes.view.get(data.pc.idPlane);
//		if (p == null)
//			return new Couple<>(
//					new Nullable<Command>(),
//					new Response(Command.ERROR_COMMAND, "Cannot find plane of id "+data.pc.idPlane)
//				);
		
		if (data.pc.idPlane > s.planes.view.size())
			return new Couple<>(
					new Nullable<Command>(),
					new Response(Command.ERROR_COMMAND, "Cannot find plane of id "+data.pc.idPlane)
				);
		Plane.FullView p = s.planes.view.get(data.pc.idPlane);
		
		// TODO migrate other checks from CommandChecker here

		return new Couple<>(
			new Nullable<Command>(new MoveCommand(p, make(data.posDest).view())),
			new Response(Command.SUCCESS, "")
		);
	}

	static public Couple<Nullable<Command>,Response> make(WaitCommandData data, World.Snapshot s) {

		if (!checkFrameId(data.pc.c, s))
			return frameIdError(data.pc.c, s);

		// TODO checks

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









