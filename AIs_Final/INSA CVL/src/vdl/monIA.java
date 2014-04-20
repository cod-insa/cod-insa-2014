package vdl;

import genbridge.LandCommandData;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.w3c.dom.views.AbstractView;

import model.AbstractBase;
import model.Base;
import model.Base.BasicView;
import model.Coord;
import model.Coord.View;
import model.Plane;
import model.Plane.FullView;
import model.Plane.Type;

import command.AttackCommand;
import command.BuildPlaneCommand;
import command.Command;
import command.DropMilitarsCommand;
import command.ExchangeResourcesCommand;
import command.FillFuelTankCommand;
import command.LandCommand;
import command.MoveCommand;
import common.MapView;

import ai.AbstractAI;


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
			MapView<Integer, model.Base.FullView> baseFull;
			AbstractBase.View toutesLesbases;
			AbstractBase.View maBase;
			BasicView b;
			
			int distanceBase = 9999999;
			int numeroBase = 0, i = 0;
			
			/*on remplit le tableau des map pour savoir laquelle est remplie*/
			basesComplete = this.game.getAllBases();
	
			while (true) {
				
				  this.game.updateSimFrame();
				  basesComplete = this.game.getAllBases();
			      planes = this.game.getMyPlanes();
			      baseFull = this.game.getMyBases();
			      maBase = this.game.getCountry();
			      numeroBase = maBase.id();
			      
			      List<Command> comm =  new ArrayList<Command>();
				  
				  /*création d'autres avions cargos*/
			      int nombreCargo = 0, nombreMilitaire = 0;
				  if(this.game.getCountry().productionLine().size() == 0)
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
					  if( (nombreCargo) <4)
					  {
						  comm.add(new BuildPlaneCommand(Plane.Type.COMMERCIAL));						  
					  }
					  if( (nombreMilitaire) <6)
					  {
						  comm.add(new BuildPlaneCommand(Plane.Type.MILITARY));						  
					  }
					  
				  }
					 
					 
			      
		    	  for (Plane.FullView p : planes.valuesView())
				  {
			    	  /*recherche de la base la plus proche*/
		    		 
	 		    	  
		    		  /*si l'avion est commercial et qu'il n'a pas encore décollé*/
		    		  if(p.type.equals(p.type.COMMERCIAL))
	    			  {
		    			  distanceBase = 99999;
				    	  for(BasicView baseBaladeuse : basesComplete.valuesView())
			    		  {
			    			  if(p.position().distanceTo(baseBaladeuse.position()) < distanceBase &&  baseBaladeuse.isFriend(maBase))
			    			  {
			    				  distanceBase = (int) p.position().distanceTo(baseBaladeuse.position());
			    				  numeroBase = baseBaladeuse.id();
				    		  }
			    		  }  
				    	//  if (numeroBase==0){
				    	//	  distanceBase = 0;
				    	//	  for(BasicView baseBaladeuse : basesComplete.valuesView())
				    	//	  {
				    	//		  if(p.position().distanceTo(baseBaladeuse.position()) > distanceBase &&  baseBaladeuse.isFriend(maBase))
				    	//		  {
				    	//			  distanceBase = (int) p.position().distanceTo(baseBaladeuse.position());
				    	//			  numeroBase = baseBaladeuse.id();
					    //		  }
				    	//	  }  
				    	 // }
		    			  if(p.state().equals(p.state().AT_AIRPORT))
		    			  {
		    				  /*s'il est à la base initiale on charge*/
		    				  if(p.position().squareDistanceTo(maBase.position()) < p.radarRange() && !p.state().equals(p.state().DROPPING))
		    				  {
		    					  if(p.militaryInHold()<5)
		    					  {
		    						  comm.add(new ExchangeResourcesCommand(p, p.type.COMMERCIAL.holdCapacity/2-p.militaryInHold(), p.type.COMMERCIAL.holdCapacity/2-p.fuelInHold(), false));
		    						  
		    					  }
		    					  else{
		    						  
		    					  if(p.fuelInTank() < p.type.COMMERCIAL.tankCapacity)
		    					  {
		    						  comm.add(new FillFuelTankCommand(p, p.type.COMMERCIAL.tankCapacity));
		    					  }	
	    						  else{
	    							  if(p.fuelInTank() >= p.type.COMMERCIAL.tankCapacity && p.fuelInHold() != 0)
		    						  {
		    							  comm.add(new MoveCommand(p, basesComplete.get(numeroBase).position()));
		    						  }
	    						  }
		    					  }
		    					  
		    				  }
		    				  /*s'il est pas déjà en train de charger et qu'il n'est pas à la base*/
		    				  else
			    			  {
	    						  comm.add(new ExchangeResourcesCommand(p, -p.fuelInHold()/2, -p.militaryInHold()/2, false));
		    					  if (p.militaryInHold()<(p.type.COMMERCIAL.holdCapacity/8-1)){
		    						  comm.add(new MoveCommand(p, this.game.getCountry().position()));
		    						  comm.add(new LandCommand(p, this.game.getCountry()));
		    					  }
		    					  else   {
		    						  comm.add(new MoveCommand(p, basesComplete.get(numeroBase).position()));
		    					  }
			    			  } 
		    			  }
		    			  else
		    			  {
		    	
		    				  if(!p.state().equals(p.state().LANDING))
		    				  {
		    					  if(p.militaryInHold() < 5)
		    					  {
		    							  comm.add(new LandCommand(p, this.game.getCountry()));	 
		    					  }
		    					  else
		    					  {			
		    						  //System.out.print("id afdfon : ");
		    						 //System.out.println(baseFull.get(numeroBase).isInTerritory());
		    						  //if ((p.position().squareDistanceTo(basesComplete.get(numeroBase).position())<5)&&(baseFull.get(numeroBase).isInTerritory())){
		    						//	  if (basesComplete.get(numeroBase).isFriend(maBase)){
		    						//		  comm.add(new LandCommand(p, basesComplete.get(numeroBase)));
		    						//	  }
		    						//	  else{
		    						//		  comm.add(new MoveCommand(p, basesComplete.get(numeroBase).position()));
		    						//	  }
		    						 // }
		    						  if ((p.position().squareDistanceTo(basesComplete.get(numeroBase).position())<1)&&(basesComplete.get(numeroBase).isFriend(maBase))){
		    							  comm.add(new LandCommand(p, basesComplete.get(numeroBase)));
		    						  }
		    						  
		    						  else{
		    							  distanceBase = 99999;
		    					    	  for(BasicView baseBaladeuse : basesComplete.valuesView())
		    				    		  {
		    				    			  if(p.position().distanceTo(baseBaladeuse.position()) < distanceBase &&  baseBaladeuse.isFriend(maBase))
		    				    			  {
		    				    				  distanceBase = (int) p.position().distanceTo(baseBaladeuse.position());
		    				    				  numeroBase = baseBaladeuse.id();
		    					    		  }
		    				    		  }  
		    							  comm.add(new MoveCommand(p, basesComplete.get(numeroBase).position()));
		    						  }
		    						     
			 
		    					  }  
		    				  }
		    			  }
	    			  }
		    		  if(p.type.equals(p.type.MILITARY))
	    			  {
		    			  distanceBase = 9999;
				    	  for(BasicView baseBaladeuse : basesComplete.valuesView())
			    		  {
			    			  if((p.position().distanceTo(baseBaladeuse.position()) < distanceBase) && (baseBaladeuse.isFriend(maBase)))
			    			  {
			    				  distanceBase = (int) p.position().distanceTo(baseBaladeuse.position());
			    				  numeroBase = baseBaladeuse.id();
			    				  
				    		  }
			    		  }  
		    			  if(p.state().equals(p.state().AT_AIRPORT))
		    			  {
		    				  /*s'il est à la base initiale on charge*/
		    				  if(p.position().squareDistanceTo(maBase.position()) < p.radarRange() && !p.state().equals(p.state().DROPPING))
		    				  {
		    					  if(p.militaryInHold()==0)
		    					  {
		    						  comm.add(new ExchangeResourcesCommand(p, p.type.MILITARY.holdCapacity/2, p.type.MILITARY.holdCapacity/2, false));
		    					  }
		    					  else{
		    						 
		    					  if(p.fuelInTank() < p.type.MILITARY.tankCapacity)
		    					  {
		    						  comm.add(new FillFuelTankCommand(p, p.type.MILITARY.tankCapacity));
		    					  }	
	    						  else{
	    							  if(p.fuelInTank() >= p.type.MILITARY.tankCapacity)
		    						  {
		    							  comm.add(new MoveCommand(p, basesComplete.get(numeroBase).position()));
		    						  }
	    						  }
		    					  }
		    					  
		    				  }
		    				  /*s'il est pas déjà en train de charger et qu'il n'est pas à la base*/
		    				  else
			    			  {
		    					  if(p.militaryInHold() >= p.type.MILITARY.holdCapacity/2)
		    					  {
		    						  comm.add(new ExchangeResourcesCommand(p, -p.fuelInHold(), -p.militaryInHold(), false));  
		    					  }
		    					  else
		    					  {
		    						
	    						  if(p.fuelInTank() < p.type.MILITARY.tankCapacity/2)
		    					  {
	    							  double p1=p.fuelInTank();
	    							  comm.add(new FillFuelTankCommand(p, p.type.MILITARY.tankCapacity/2));
	    							  if (p.fuelInTank()==p1) {
	    								  comm.add(new MoveCommand(p, basesComplete.get(numeroBase).position())); 
	    							  }
		    					  	}		  
	    						  	else{
	    						  		comm.add(new MoveCommand(p, basesComplete.get(numeroBase).position()));
	    						  	}  						  		    						  
		    					  }
			    			  } 
		    			  }
		    			  else
		    			  {
		    	
		    				  if(!p.state().equals(p.state().AT_AIRPORT))
		    				  {
		    					  if ((p.fuelInTank() < 5) && (p.health()>(0.4*p.type.MILITARY.fullHealth)))
		    					  {
		    							  comm.add(new LandCommand(p, basesComplete.get(numeroBase)));	 
		    					  }
		    					  else
		    					  {					 
		    						 // MapView ennemy_planes = this.game.getEnnemyPlanes();
		    						  //Plane.BasicView ep = (Plane.BasicView)ennemy_planes.get(Integer.valueOf(Integer.parseInt(cmd[1])));
		    				          //  if (ep == null)
		    				          //  {
		    				          //    System.err.println("Bad id, this plane does not exists");
		    				         //     break label1072;
		    				         //   }
		    				          //  for (Plane.FullView p : planes.valuesView())
		    				         //     coms.add(new AttackCommand(p, ep));
		    				              MapView<Integer,Plane.BasicView> ennemy_planes = this.game.getEnnemyPlanes();
			    						  for (Plane.BasicView ep : ennemy_planes.valuesView())
			    						  {
			    							  
			    								  comm.add(new AttackCommand(p, ep));  
			    							  
			    						  }
		    						   
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
