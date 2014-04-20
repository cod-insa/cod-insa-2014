package Interfaces;

import model.Coord;
import Enums.Priority;
import Global.Order;

public interface fromPlane {
	public Coord getPosition();
	public double getTroop();
	public double getEmbeddedFuel();
	public Order getOrder();
	public Priority getOrderPriority();
	public model.Plane.Type getType();
}
