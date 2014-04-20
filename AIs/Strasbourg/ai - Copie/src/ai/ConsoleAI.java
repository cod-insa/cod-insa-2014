package ai;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.AbstractBase;
import model.Base;
import model.Coord;
import model.Plane;
import model.Plane.BasicView;
import command.AttackCommand;
import command.Command;
import command.DropMilitarsCommand;//ok
import command.LandCommand;//Ok
import command.MoveCommand;//Ok
import command.ExchangeResourcesCommand;//Ok
import command.BuildPlaneCommand;//Ok
import command.FollowCommand;//Ok
import command.FillFuelTankCommand;//Ok
import common.MapView;

public class ConsoleAI extends AbstractAI {

    private MapView<Integer, Base.BasicView> bases;
    private MapView<Integer, Plane.FullView> planes;
    private MapView<Integer, BasicView> ennemy_planes;

    public ConsoleAI(String ip, int port) {
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
                   System.out.println("quantité dispo: "+p.type.holdCapacity);
                    if(game.getCountry().planes().indexOf(p)!=-1){
                        double reserv=p.type.holdCapacity;
                        if(reserv==20){
                            load(p.id(),5,15,false);
                        } else {
                            load(p.id(),reserv,0,false);
                        }
                    } else {
                        land(p.id(),game.getCountry().id());
                    }
                } else {
                   if (p.type==Plane.Type.COMMERCIAL){
                       if (p.state()==Plane.State.AT_AIRPORT){
                           load(p.id(),-5,-15,false);
                       } else {
                           land(p.id(),(p.id()+12)%bases.size());
                       }
                       
                   } else {
                       unloadMilitaries(p.id(),(p.id()+12)%bases.size(),p.militaryInHold());
                   }
                }
            }
            if (i%5==0){
                buildPlane((i/5)%2);
            }
            
        
            
            //En commentaire le système pour le controler manuellement
            /*
            System.out.print("Next action ([move|land|attk] id; list; exit): ");

            String[] cmd = in.nextLine().split(" ");

            System.out.print("Processing command... ");
            System.out.flush();

            game.updateSimFrame();

            bases = game.getAllBases();
            planes = game.getMyPlanes();
            ennemy_planes = game.getEnnemyPlanes();

            List<Command> coms = new ArrayList<>();

            try {
                boolean recognized = true;
                
                switch (cmd[0]) {
                    case "exit":
                        break main_loop;
                    case "move": {
                        if (cmd.length == 3) {
                            int planeId = Integer.parseInt(cmd[1]);
                            int id = Integer.parseInt(cmd[2]);
                            move(planeId, id);
                        }
                        break;
                    }
                    case "build":{
                        int type = Integer.parseInt(cmd[1]);
                        buildPlane(type);
                        break;
                    }
                    case "fill": {
                        int id = Integer.parseInt(cmd[1]);
                        double amount = Float.parseFloat(cmd[2]);
                        fillFuelTank(id, amount);
                        break;
                    }
                    case "load": {
                        int planeId = Integer.parseInt(cmd[1]);
                        int nbMilitaries = ((cmd.length >= 3) ? Integer.parseInt(cmd[2]) : -1);
                        int nbFuel = ((cmd.length == 4) ? Integer.parseInt(cmd[3]) : 0);
                        load(planeId, nbMilitaries, nbFuel, false);
                        break;
                    }
                    case "unload": {
                        int planeId = Integer.parseInt(cmd[1]);
                        int baseId = Integer.parseInt(cmd[2]);
                        int nbMilitaries = ((cmd.length == 4) ? Integer.parseInt(cmd[3]) : -1);
                        unloadMilitaries(planeId, baseId, nbMilitaries);
                        break;
                    }
                    case "land": {
                        int id = Integer.parseInt(cmd[2]);
                        int idPlane = Integer.parseInt(cmd[1]);
                        land(idPlane, id);

                        break;
                    }
                    case "follow":{
                        int idFollower = Integer.parseInt(cmd[1]);
                        int idFollowed = Integer.parseInt(cmd[2]);
                        int isEnemy = Integer.parseInt(cmd[3]);
                        followPlane(idFollower, idFollowed, isEnemy);
                    }
                    case "attk":
                        Plane.BasicView ep = ennemy_planes.get(Integer.parseInt(cmd[1]));
                        if (ep == null) {
                            System.err.println("Bad id, this plane does not exists");
                            break;
                        }
                        for (Plane.FullView p : planes.valuesView()) {
                            coms.add(new AttackCommand(p, ep));
                        }
                        break;
                    case "list":
                        System.out.println();
                        System.out.println(">> My planes:");
                        for (Plane.FullView p : planes.valuesView()) {
                            System.out.println(p);
                            System.out.println(Double.toString(p.fuelInTank()));
                        }
                        System.out.println(">> Ennemy planes:");
                        for (Plane.BasicView p : ennemy_planes.valuesView()) {
                            System.out.println(p);
                        }
                        System.out.println(">> Visible bases :");
                        for (Base.FullView b : game.getVisibleBase().valuesView()) {
                            System.out.println(b);
                        }
                        System.out.println(">> Your Country");
                        System.out.println(game.getCountry());
                        break;
                    default:
                        recognized = false;
                        System.err.println("Unrecognized command!");
                }
                if (recognized) {
                    System.out.println("Processed");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Command failed: " + e);
                System.err.println("Command failed: " + e);
            }*/

        }
    }


    public static void main(String[] args) throws InterruptedException {
        if (args.length != 2) {
            System.out.println("Usage : java MoveAI ip port");
            System.exit(1);
        }
        String ip = args[0];
        int port = Integer.parseInt(args[1]);

        AbstractAI ai = new ConsoleAI(ip, port);
        ai.think();
    }
}
