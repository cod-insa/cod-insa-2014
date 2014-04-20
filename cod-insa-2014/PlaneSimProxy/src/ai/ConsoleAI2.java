package ai;

import java.util.Scanner;

import model.AbstractBase;
import model.Base;
import model.Coord;
import model.Plane;
import model.Plane.BasicView;

import command.BuildPlaneCommand;
import command.DropMilitarsCommand;
import command.ExchangeResourcesCommand;
import command.FillFuelTankCommand;
import command.FollowCommand;
import command.LandCommand;
import command.MoveCommand;
import common.MapView;
//ok
//Ok
//Ok
//Ok
//Ok
//Ok
//Ok

public class ConsoleAI2 extends AbstractAI {

    private MapView<Integer, Base.BasicView> bases;
    private MapView<Integer, Plane.FullView> planes;
    private MapView<Integer, BasicView> ennemy_planes;

    public ConsoleAI2(String ip, int port) {
        super(ip, port);
    }

    @Override
    public void think() {
        think_blocking();
    }

    public boolean move(int planeId, int baseId) {
        Base.BasicView b = bases.get(baseId);
        Coord.View c = null;

        if (b == null) {
            if (game.getCountry().id() != baseId) {
                System.err.println("This id isn't corresponding neither to a base nor your country");
                return false;
            }
            c = game.getCountry().position();
        } else {
            c = b.position();
        }

        game.sendCommand(new MoveCommand(planes.get(planeId), c));

        return true;
    }

    public boolean land(int planeId, int baseId) {
        Base.BasicView b = bases.get(baseId);
        AbstractBase.View c = null;

        if (b == null) {
            if (game.getCountry().id() != baseId) {
                System.err.println("You can't see this base, move around it before you land");
                return false;
            }
            c = game.getCountry();
        } else {
            c = b;
        }

        game.sendCommand(new LandCommand(planes.get(planeId), c));
        return true;
    }

    public boolean load(int planeId, double nbMilitaries, double nbFuel, boolean delete) {
        if (nbMilitaries + nbFuel > planes.get(planeId).type.holdCapacity) {//Trop au total
            if (nbMilitaries >= planes.get(planeId).type.holdCapacity) {//Trop de militaires
                nbMilitaries = planes.get(planeId).type.holdCapacity;
                nbFuel = 0;
            } else {//Trop de fuel
                nbFuel = planes.get(planeId).type.holdCapacity - nbMilitaries;
            }
        }
        System.out.println(Double.toString(nbMilitaries));
        if (nbMilitaries == -1) {
            nbMilitaries = planes.get(planeId).type.holdCapacity;
            nbFuel = 0;
        } else if (nbFuel == -1) {
            nbMilitaries = 0;
            nbFuel = planes.get(planeId).type.holdCapacity;
        }
        game.sendCommand(new ExchangeResourcesCommand(planes.get(planeId), nbMilitaries, nbFuel, delete));
        return true;
    }
    
    public boolean unloadMilitaries(int planeId, int baseId, double nbMilitaries){
        Base.BasicView b = bases.get(baseId);
        AbstractBase.View c = null;

        if (b == null) {
            if (game.getCountry().id() != baseId) {
                System.err.println("You can't see this base, move around it before you land");
                return false;
            }
            c = game.getCountry();
        } else {
            c = b;
        }
        game.sendCommand(new DropMilitarsCommand(planes.get(planeId), c, nbMilitaries));
        return true;
    }
    
    public boolean buildPlane(int militar){ //1:militaire 2:commerce
        game.sendCommand(new BuildPlaneCommand(((militar==0)?Plane.Type.MILITARY:Plane.Type.COMMERCIAL)));
        return true;
    }
    
    public boolean followPlane(int idFollower, int idFollowed, int isEnemy){
        Plane.FullView p = planes.get(idFollower);
        Plane.BasicView b;
        
        if(isEnemy==0){
            b = planes.get(idFollowed);
        }else{
            b = ennemy_planes.get(idFollowed);
        }
        game.sendCommand(new FollowCommand(p, b));
        return true;
    }
    
    public boolean fillFuelTank(int planeId, double amount){
        Plane.FullView p = planes.get(planeId);
        if(amount < 0 || amount > p.type.tankCapacity){;
            amount = p.type.tankCapacity;
        }
        game.sendCommand(new FillFuelTankCommand(p, amount));
        return true;
    } 

    public void think_blocking() {
        Scanner in = new Scanner(System.in);
        int i=0;
        main_loop:
        while (true) {
            i++;
            game.updateSimFrame();
            bases = game.getAllBases();
            planes = game.getMyPlanes();
            for (Plane.FullView p : planes.valuesView()){
                
                if(p.militaryInHold()==0){
                    if(game.getCountry().planes().contains(p)){
                        load(p.id(),p.type.holdCapacity,0,false);
                    } else {
                        land(p.id(),game.getCountry().id());
                    }
                } else {
                    unloadMilitaries(p.id(),p.id()%bases.size(),p.militaryInHold());
                }
            }
            if (i%5000000==0){
                buildPlane((i/5000000)%2);
            }
            
        
            

        }
    }


    public static void main(String[] args) throws InterruptedException {
        if (args.length != 2) {
            System.out.println("Usage : java MoveAI ip port");
            System.exit(1);
        }
        String ip = args[0];
        int port = Integer.parseInt(args[1]);

        AbstractAI ai = new ConsoleAI2(ip, port);
        ai.think();
    }
}
