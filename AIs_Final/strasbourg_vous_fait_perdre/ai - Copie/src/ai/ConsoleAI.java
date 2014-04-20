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
import command.WaitCommand;
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
    
    
    
    public Base.BasicView[] classement (MapView<Integer, Base.BasicView> bases){
        Base.BasicView[] res ;
        res = new Base.BasicView[bases.size()];
        for(int i = 0; i<bases.size();i++){
            res[i]=bases.get(i+1);
        }
        for(int j=1;j<bases.size()-1;j++){
            if (res[j].axes().size()<res[j+1].axes().size()){
                Base.BasicView temp = res[j+1];
                res[j+1]=res[j];
                res[j]=temp;
                j=1;
            }
        }
        return res;
    }
    
    
    

    public void think_blocking() {
        Scanner in = new Scanner(System.in);
        int i = 0;
        main_loop:
        while (true) {
            i++;
            game.updateSimFrame();
            bases = game.getAllBases();
            planes = game.getMyPlanes();
            ennemy_planes = game.getEnnemyPlanes();
            Base.BasicView[] basestri = classement(bases);
            int j = 0;
            for (Plane.FullView p : planes.valuesView()) {
                if (p.militaryInHold() == 0) {
                    if (game.getCountry().planes().indexOf(p) != -1) {
                        double reserv = p.type.holdCapacity;
                        if (reserv == 20) {
                            load(p.id(), 10, 10, false);
                        } else {
                            load(p.id(), reserv, 0, false);
                        }
                    } else if (p.type == Plane.Type.COMMERCIAL) {
                        land(p.id(), game.getCountry().id());
                    } else {
                        if (p.canAttack() && !ennemy_planes.isEmpty()) {
                            game.sendCommand(new WaitCommand(p));
                        } else {
                            land(p.id(), basestri[(j + 1) % (basestri.length - 1)].id());
                        }
                    }
                } else {
                    if (p.type == Plane.Type.COMMERCIAL) {
                        if (p.state() == Plane.State.AT_AIRPORT  && !p.canSee(game.getCountry())) {
                            load(p.id(), -10, -10, false);
                        } else if (p.state() == Plane.State.GOING_TO || p.state() == Plane.State.LANDING) {
                        } else if (p.state() == Plane.State.IDLE) {
                            unloadMilitaries(p.id(), basestri[(j + 1) % (basestri.length - 1)].id(), p.militaryInHold());
                        } else {
                            land(p.id(), basestri[(j + 1) % (basestri.length - 1)].id());
                        }
                    } else {
                         unloadMilitaries(p.id(), basestri[(j + 1) % (basestri.length - 1)].id(), p.militaryInHold());
                    }
                    j++;
                }
                if (i % 7 == 0) {
                    buildPlane(1);
                } 
                if (i % 12 == 0) {
                    buildPlane(0);
                } 

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

        AbstractAI ai = new ConsoleAI(ip, port);
        ai.think();
    }
}
