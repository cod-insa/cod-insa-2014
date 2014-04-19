package control;

import game.World;
import game.World.Snapshot;
import genbridge.AttackCommandData;
import genbridge.BuildPlaneCommandData;
import genbridge.CancelBuildRequestCommandData;
import genbridge.CommandData;
import genbridge.CoordData;
import genbridge.DropMilitarsCommandData;
import genbridge.ExchangeResourcesCommandData;
import genbridge.FillFuelTankCommandData;
import genbridge.FollowCommandData;
import genbridge.LandCommandData;
import genbridge.MoveCommandData;
import genbridge.Response;
import genbridge.WaitCommandData;
import model.AbstractBase;
import model.Base;
import model.Coord;
import model.Country;
import model.Plane;
import model.Plane.State;
import command.AttackCommand;
import command.BuildPlaneCommand;
import command.CancelRequestCommand;
import command.Command;
import command.DropMilitarsCommand;
import command.ExchangeResourcesCommand;
import command.FillFuelTankCommand;
import command.FollowCommand;
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
	public static AbstractBase.View findBaseById(int idBase, World.Snapshot s, int ai_id) {
		for (Base.FullView b : s.bases.view())
			if (b.id() == idBase)
				return b;
		for (Country.View c : s.countries.view())
			if (c.id() == idBase && c.ownerId() == ai_id)
				return c;
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
		AbstractBase.View b = findBaseById(data.idBase, s, p.ownerId()); // This is necessary the id of the AI
		if (b == null)
			return baseIdError(data.idBase, s);
		
		// check that the base is neutral or owned
		if (b.ownerId() != 0 && b.ownerId() != p.ownerId())
			return new Couple<>(
					new Nullable<Command>(),
					new Response(Command.SUCCESS, "")
			);
		
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
		
		if (!ps.canAttack())
			return new Couple<>(
					new Nullable<Command>(),
					new Response(Command.ERROR_COMMAND, "The commercial plane " + ps.id() + " can't attack"));
		
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
		AbstractBase.View b = findBaseById(data.base_id, s,p.ownerId());
		if (b == null)
			return planeIdError(data.base_id, s);
		
		if (data.quantity < 0)
			return new Couple<>(
					new Nullable<Command>(),
					new Response(Command.ERROR_COMMAND,"Can't drop a negative quantity of resources !"));
		
		if (data.quantity > p.militaryInHold())
			return new Couple<>(
					new Nullable<Command>(),
					new Response(Command.ERROR_COMMAND,"Invalid quantity to drop: " + data.quantity + ". Can't drop more than " +p.militaryInHold()));
		
		// Not allowed to drop unit in the middle of the ennemy's territory
		if (b.ownerId() != p.ownerId() && 
			b.ownerId() != 0 && 
			b instanceof Base.FullView && 
			((Base.FullView)b).isInTerritory())
				return new Couple<>(
					new Nullable<Command>(),
					new Response(Command.ERROR_COMMAND,"Cannot drop military resources over an enemy base in the middle of their territory !"));
		
		if (b instanceof Base.BasicView)
			return new Couple<>(
					new Nullable<Command>(),
					new Response(Command.ERROR_COMMAND,"You can't drop any militar resources over a base you don't see"));
		
		// Should not happen
		if (b instanceof Country.View && b.ownerId() != p.ownerId())
			return new Couple<>(
					new Nullable<Command>(),
					new Response(Command.ERROR_COMMAND,"Do you really think that you can drop something over an ennemy country ?"));
			
			
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
		
		// check if at airport
		if (p.state() != State.AT_AIRPORT)
			return new Couple<>(
					new Nullable<Command>(),
					new Response(Command.ERROR_COMMAND,"Cannot fill the tank in flight"));
		
		// check quantity
		if (p.fuelInTank() + data.quantity < 0) 
			return new Couple<>(
					new Nullable<Command>(),
					new Response(Command.ERROR_COMMAND,"Cannot deposit " + data.quantity + " fuel from tank. Minimum is : " + p.fuelInTank()));
		if (data.quantity > p.type.tankCapacity - p.fuelInTank())
			return new Couple<>(
					new Nullable<Command>(),
					new Response(Command.ERROR_COMMAND,"Too much fuel to fill"));
			
		// check if enough fuel in base
		if (p.curBase() instanceof Base.FullView)
			if (data.quantity > ((Base.FullView)p.curBase()).fuelInStock())
				return new Couple<>(
						new Nullable<Command>(new FillFuelTankCommand(p, ((Base.FullView)p.curBase()).fuelInStock())),
						new Response(Command.WARNING_COMMAND,"Not enough fuel in base stock. All fuel in the base have been taken instead."));
		
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

	static public Couple<Nullable<Command>,Response> make(ExchangeResourcesCommandData data, World.Snapshot s)	{
		
		// check id
		if (!checkFrameId(data.pc.c, s))
			return frameIdError(data.pc.c, s);
		
		// check plane (source)
		Plane.FullView p = findPlaneById(data.pc.idPlane, s);
		if (p == null)
			return planeIdError(data.pc.idPlane, s);
		
		// check negative fuel post exchange
		if (p.fuelInHold() + data.fuel_quantity < 0) 
			return new Couple<>(
					new Nullable<Command>(),
					new Response(Command.ERROR_COMMAND,"Cannot deposit " + data.fuel_quantity + ". Minimum is : " + -p.fuelInHold()));
		
		// check negative military post exchange
		if (p.militaryInHold() + data.militar_quantity < 0)
			return new Couple<>(
					new Nullable<Command>(),
					new Response(Command.ERROR_COMMAND,"Cannot deposit " + data.militar_quantity + ". Minimum is : " + -p.militaryInHold()));
			
		// check if at airport if not deleting resources
		if (p.state() != State.AT_AIRPORT && !data.deleteResources)
			return new Couple<>(
					new Nullable<Command>(),
					new Response(Command.ERROR_COMMAND,"Cannot exchange resources if in flight and not deleting resources"));
		
		// check that delete resources => data.military_quantity < 0 && data.fuel_quantity < 0
		if (data.deleteResources && (data.militar_quantity > 0 || data.fuel_quantity > 0))
			return new Couple<>(
					new Nullable<Command>(),
					new Response(Command.ERROR_COMMAND,"Cannot load resources in delete mode. The quantity must be negative to delete resources"));
		
			
		// check if too much quantity is loaded
		if (data.fuel_quantity + data.militar_quantity + p.militaryInHold() + p.fuelInHold() > p.type.holdCapacity)
			return new Couple<>(
					new Nullable<Command>(),
					new Response(Command.ERROR_COMMAND,"Too much resources to load. Maximum additionned quantity is " + 
							(p.type.holdCapacity - (data.fuel_quantity + data.militar_quantity + p.militaryInHold() + p.fuelInHold()))
					));
		
		// Everything all right
		return new Couple<>(
				new Nullable<Command>(new ExchangeResourcesCommand(p,data.militar_quantity,data.fuel_quantity,data.deleteResources)),
				new Response(Command.SUCCESS, "")
		);
	}

	public static Couple<Nullable<Command>, Response> make(
			BuildPlaneCommandData data, Snapshot s) {

		// check id
		if (!checkFrameId(data.c, s))
			return frameIdError(data.c, s);
		
		// check type of build request
		if (data.planeTypeId < 0 || data.planeTypeId > 1)
			return new Couple<>(
					new Nullable<Command>(),
					new Response(Command.ERROR_COMMAND,"Unrecognized plane type num " + data.planeTypeId
					));
	
		// Everything all right
		return new Couple<>(
				new Nullable<Command>(new BuildPlaneCommand(Plane.Type.get(data.planeTypeId))),
				new Response(Command.SUCCESS, "")
		);
	}

	public static Couple<Nullable<Command>, Response> make(
			CancelBuildRequestCommandData data, Snapshot s, int ai_id) {
		
		// check id
		if (!checkFrameId(data.c, s))
			return frameIdError(data.c, s);
		
		
		
		Country.Request.View r = s.countries.view().get(ai_id-1).productionLine().get(data.id_request);
			
		// check existance of the request
		if (r == null)
			return new Couple<>(
				new Nullable<Command>(),
				new Response(Command.ERROR_COMMAND,"Error, this request does not seem to be in the production line, maybe the plane is already built"
				));
		
		// Everything all right
			return new Couple<>(
					new Nullable<Command>(),
					new Response(Command.ERROR_COMMAND,"This command has not been implemented"
					));
	}

	
}









