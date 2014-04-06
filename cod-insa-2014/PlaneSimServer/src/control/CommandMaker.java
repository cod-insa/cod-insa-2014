package control;

import game.World;
import game.World.Snapshot;
import genbridge.AttackCommandData;
import genbridge.CommandData;
import genbridge.CoordData;
import genbridge.DropMilitarsCommandData;
import genbridge.FillFuelTankCommandData;
import genbridge.FollowCommandData;
import genbridge.LandCommandData;
import genbridge.LoadResourcesCommandData;
import genbridge.MoveCommandData;
import genbridge.PlaneCommandData;
import genbridge.Response;
import genbridge.StoreFuelCommandData;
import genbridge.WaitCommandData;
import model.Base;
import model.Coord;
import model.Plane;
import command.AttackCommand;
import command.Command;
import command.DropMilitarsCommand;
import command.FillFuelTankCommand;
import command.FollowCommand;
import command.LandCommand;
import command.LoadResourcesCommand;
import command.MoveCommand;
import command.StoreFuelCommand;
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

	// Frame checking
	public static boolean checkFrameId(CommandData data, World.Snapshot s) {
		return data.numFrame == s.id;
	}
	static Couple<Nullable<Command>, Response> frameIdError(CommandData data, World.Snapshot s) {
		return new Couple<>(
				new Nullable<Command>(),
				new Response(Command.ERROR_TIME_OUT, "Current frame id is "+s.id+" command frame id is "+data.numFrame)
		);
	}

	// Plane checking
	public static Plane.FullView findPlaneById(int idPlane, World.Snapshot s) {
		for (Plane.FullView p : s.planes.view())
			if (p.id() == idPlane)
				return p;
		return null;
	}
	static Couple<Nullable<Command>, Response> planeIdError(int idPlane, World.Snapshot s) {
		return new Couple<>(
				new Nullable<Command>(),
				new Response(Command.ERROR_COMMAND, "Cannot find plane of id "+idPlane)
		);
	}
	
	// Base checking
	public static Base.View findBaseById(int idBase, World.Snapshot s) {
		for (Base.View b : s.bases.view())
			if (b.id() == idBase)
				return b;
		return null;
	}
	static Couple<Nullable<Command>, Response> baseIdError(int idBase, World.Snapshot s) {
		return new Couple<>(
				new Nullable<Command>(),
				new Response(Command.ERROR_COMMAND, "Cannot find base of id "+idBase)
		);
	}
	
	// Coord checking
	public static boolean checkCoord(CoordData coord, World.Snapshot s)
	{
		return coord.x > 0 && 
				coord.x < s.width &&
				coord.y > 0 &&
				coord.y < s.height;
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

	static public Couple<Nullable<Command>,Response> make(AttackCommandData data, World.Snapshot s)	{
		
		// check id
		if (!checkFrameId(data.pc.c, s))
			return frameIdError(data.pc.c, s);
		
		// check plane (source)
		Plane.FullView ps = findPlaneById(data.pc.idPlane, s);
		if (ps == null)
			return planeIdError(data.pc.idPlane, s);
		
		// check target
		Plane.BasicView pt = findPlaneById(data.idTarget, s);
		if (pt == null)
			return planeIdError(data.idTarget, s);
		
		
		// Everything all right
		return new Couple<>(
				new Nullable<Command>(new AttackCommand(ps, pt)),
				new Response(Command.SUCCESS, "")
		);
	}
	
	static public Couple<Nullable<Command>,Response> make(DropMilitarsCommandData data, World.Snapshot s)	{
		
		// check id
		if (!checkFrameId(data.pc.c, s))
			return frameIdError(data.pc.c, s);
		
		// check plane (source)
		Plane.FullView p = findPlaneById(data.pc.idPlane, s);
		if (p == null)
			return planeIdError(data.pc.idPlane, s);
		
		// check target
		Base.View b = findBaseById(data.base_id, s);
		if (b == null)
			return planeIdError(data.base_id, s);
		
		if (data.quantity < 0)
			return new Couple<>(
					new Nullable<Command>(),
					new Response(Command.ERROR_COMMAND,"Can't drop a negative quantity of resources !"));
		
		if (data.quantity > p.militarResourceCarried())
			return new Couple<>(
					new Nullable<Command>(),
					new Response(Command.ERROR_COMMAND,"Invalid quantity to drop: " + data.quantity + ". Can't drop more than " +p.militarResourceCarried()));
		
		if (!p.canAttack() && b.ownerId() != p.ownerId())
			return new Couple<>(
					new Nullable<Command>(),
					new Response(Command.ERROR_COMMAND,"Can't drop military resources over an enemy base with a comercial plane !"));
		
		
		// Everything all right
		return new Couple<>(
				new Nullable<Command>(new DropMilitarsCommand(p, b, data.quantity)),
				new Response(Command.SUCCESS, "")
		);
	}

	static public Couple<Nullable<Command>,Response> make(FillFuelTankCommandData data, World.Snapshot s)	{
		
		// check id
		if (!checkFrameId(data.pc.c, s))
			return frameIdError(data.pc.c, s);
		
		// check plane (source)
		Plane.FullView p = findPlaneById(data.pc.idPlane, s);
		if (p == null)
			return planeIdError(data.pc.idPlane, s);
		
		// check quantity
		if (data.quantity < 0) 
			return new Couple<>(
					new Nullable<Command>(),
					new Response(Command.ERROR_COMMAND,"Can't fill the tank with a negative quantity of fuel !"));
		
		if (data.quantity > p.capacityTank() - p.remainingGaz())
			return new Couple<>(
					new Nullable<Command>(),
					new Response(Command.ERROR_COMMAND,"Can't fill this much fuel !"));
		
		// Everything all right
		return new Couple<>(
				new Nullable<Command>(new FillFuelTankCommand(p, data.quantity)),
				new Response(Command.SUCCESS, "")
		);
	}

	static public Couple<Nullable<Command>,Response> make(FollowCommandData data, World.Snapshot s)	{
		
		// check id
		if (!checkFrameId(data.pc.c, s))
			return frameIdError(data.pc.c, s);
		
		// check plane (source)
		Plane.FullView ps = findPlaneById(data.pc.idPlane, s);
		if (ps == null)
			return planeIdError(data.pc.idPlane, s);
		
		// check base
		Plane.BasicView pt = findPlaneById(data.idTarget, s);
		if (pt == null)
			return planeIdError(data.idTarget, s);
		
		// Everything all right
		return new Couple<>(
				new Nullable<Command>(new FollowCommand(ps, pt)),
				new Response(Command.SUCCESS, "")
		);
	}

	static public Couple<Nullable<Command>,Response> make(LoadResourcesCommandData data, World.Snapshot s)	{
		
		// check id
		if (!checkFrameId(data.pc.c, s))
			return frameIdError(data.pc.c, s);
		
		// check plane (source)
		Plane.FullView p = findPlaneById(data.pc.idPlane, s);
		if (p == null)
			return planeIdError(data.pc.idPlane, s);
		
		if (data.fuel_quantity < 0 || data.militar_quantity < 0) 
			return new Couple<>(
					new Nullable<Command>(),
					new Response(Command.ERROR_COMMAND,"Can't get a negative quantity of resources !"));
		
		
		// check quantity
		if (data.fuel_quantity + data.militar_quantity + p.militarResourceCarried() + p.fuelResourceCarried() > p.capacityHold())
			return new Couple<>(
					new Nullable<Command>(),
					new Response(Command.ERROR_COMMAND,"Too much resources to load. Maximum is " + 
							(p.capacityHold() - (data.fuel_quantity + data.militar_quantity + p.militarResourceCarried() + p.fuelResourceCarried()))
					));
		
		// Everything all right
		return new Couple<>(
				new Nullable<Command>(new LoadResourcesCommand(p,data.militar_quantity,data.fuel_quantity)),
				new Response(Command.SUCCESS, "")
		);
	}

	static public Couple<Nullable<Command>,Response> make(StoreFuelCommandData data, World.Snapshot s)	{
		
		// check id
		if (!checkFrameId(data.pc.c, s))
			return frameIdError(data.pc.c, s);
		
		// check plane (source)
		Plane.FullView p = findPlaneById(data.pc.idPlane, s);
		if (p == null)
			return planeIdError(data.pc.idPlane, s);
		
		if (data.quantity < 0) 
			return new Couple<>(
					new Nullable<Command>(),
					new Response(Command.ERROR_COMMAND,"Can't store a negative quantity of resources !"));
		
		
		// check quantity
		if (data.quantity > p.militarResourceCarried())
			return new Couple<>(
					new Nullable<Command>(),
					new Response(Command.ERROR_COMMAND,"Too much resources to store. The plane contains " + 
							p.militarResourceCarried() + " resources at maximum"
					));
		
		// Everything all right
		return new Couple<>(
				new Nullable<Command>(new StoreFuelCommand(p,data.quantity)),
				new Response(Command.SUCCESS, "")
		);
	}

	
}









