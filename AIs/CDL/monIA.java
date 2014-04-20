package ai;

import java.util.ArrayList;
import java.util.List;

import model.AbstractBase;
import model.Base.BasicView;
import model.Plane;
import model.Plane.FullView;

import command.BuildPlaneCommand;
import command.Command;
import command.ExchangeResourcesCommand;
import command.FillFuelTankCommand;
import command.LandCommand;
import common.MapView;


public class monIA extends AbstractAI{

	public monIA(String ip, int port) {
		super(ip, port);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void think() {
		// TODO Auto-generated method stub
			
			MapView<Integer, BasicView> basesComplete;
			MapView<Integer, FullView> planes;
			AbstractBase.View toutesLesbases;
			AbstractBase.View maBase;
			BasicView b;
			
			int distanceBase = 9999999;
			int distanceBaseAvion = 9999999;
			int numeroBase = 0, i = 0;
			
			/*on remplit le tableau des map pour savoir laquelle est remplie*/
			basesComplete = this.game.getAllBases();
	
			while (true) {
				
				  this.game.updateSimFrame();
				  basesComplete = this.game.getAllBases();
			      planes = this.game.getMyPlanes(); 
			      maBase = this.game.getCountry();
			      numeroBase = maBase.id();
			      
			      List<Command> comm =  new ArrayList<Command>();
				  
				  /*création d'autres avions cargos*/
			      int nombreCargo = 0, nombreMilitaire = 0;
				  if(this.game.getCountry().productionLine().size() < 2)
				  {
					  for (Plane.FullView p : planes.valuesView())
					  {
						  if(p.type.equals(p.type.COMMERCIAL))
		    			  {
							  nombreCargo++;
		    			  }
						  else
						  {
							  nombreMilitaire++;
						  }
						  System.out.print("id avion : ");
						  System.out.println(p.id());
					  }
					  if( (nombreCargo) <2)
					  {
						  comm.add(new BuildPlaneCommand(Plane.Type.COMMERCIAL));
						  //comm.add(new BuildPlaneCommand(Plane.Type.MILITARY));
					  }
				  }
			      
		    	  for (Plane.FullView p : planes.valuesView())
				  {
			    	  /*recherche de la base la plus proche*/
	    			  distanceBase = 99999;
	    			  distanceBaseAvion = 99999;
			    	  for(BasicView baseBaladeuse : basesComplete.valuesView())
		    		  {
		    			  if(p.position().distanceTo(baseBaladeuse.position()) < distanceBase &&  baseBaladeuse.isFriend(maBase))
		    			  {
		    				  distanceBase = (int) p.position().distanceTo(baseBaladeuse.position());
		    				  numeroBase = baseBaladeuse.id();
			    		  }
		    			  else
		    			  {
		    				  
		    			  }
		    		  }  

			    	  
		    		  /*si l'avion est commercial et qu'il n'a pas encore décollé*/
		    		  if(p.type.equals(p.type.COMMERCIAL))
	    			  {
		    			  if(p.state().equals(p.state().AT_AIRPORT))
		    			  {
		    				  /*s'il est à la base initiale on charge*/
		    				  if(p.position().squareDistanceTo(maBase.position()) < p.radarRange())
		    				  {
		    					  if(p.fuelInHold() == 0)
		    					  {
		    						  comm.add(new ExchangeResourcesCommand(p, p.type.COMMERCIAL.holdCapacity/2, p.type.COMMERCIAL.holdCapacity/2, false));
		    						  
		    					  }
		    					  if(p.fuelInTank() < p.type.COMMERCIAL.tankCapacity)
		    					  {
		    						  comm.add(new FillFuelTankCommand(p, (p.type.COMMERCIAL.tankCapacity) -  p.fuelInTank()));
		    					  }
		    					  else
		    					  {
		    						  if(p.fuelInTank() >= p.type.COMMERCIAL.tankCapacity && p.fuelInHold() != 0)
		    						  {
		    							  comm.add(new LandCommand(p, basesComplete.get(numeroBase)));
		    						  }
		    					  }
		    				  }
		    				  /*s'il est pas déjà en train de charger et qu'il n'est pas à la base*/
		    				  else
			    			  {
	    						  comm.add(new ExchangeResourcesCommand(p, -p.fuelInHold(), -p.militaryInHold(), false));
		    					  comm.add(new LandCommand(p, this.game.getCountry()));  
			    			  } 
		    			  }
		    			  else
		    			  {
		    	
		    				  if(p.state().equals(p.state().LANDING))
		    				  {
		    					  System.out.println(numeroBase);
		    					  if(p.fuelInHold() == 0)
		    					  {
		    						  if(p.position().squareDistanceTo(this.game.getCountry().position()) < p.radarRange())
		    						  {
		    							  comm.add(new LandCommand(p, this.game.getCountry()));
		    						  }
		    					  }
		    					  else
		    					  {
		    					     comm.add(new LandCommand(p, basesComplete.get(numeroBase)));
		    						 
		    					  }  
		    				  }
		    			  }
	    			  }
			      }

			      for(Command commande : comm)
			    	  this.game.sendCommand(commande);
			}
	}
	
	public static void main(String[] args) throws InterruptedException {
		if (args.length != 2)
		{
			System.out.println("Usage : java MoveAI ip port");
			System.exit(1);
		}
		String ip = args[0];
		int port = Integer.parseInt(args[1]);
		
		AbstractAI ai = new monIA(ip,port);
		ai.think();
		}
}
