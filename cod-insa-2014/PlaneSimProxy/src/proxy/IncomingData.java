package proxy;

import genbridge.Base;
import genbridge.Bridge;

import java.util.ArrayList;

import org.apache.thrift.TException;

public class IncomingData implements Runnable {

	private Proxy proxy;
	Bridge.Client client;
	
	public IncomingData(Bridge.Client c, Proxy p)
	{
		client = c;
		proxy = p;
	}
	
	@Override
	public void run()
	{
		initDatas();
		updateDatas();
	}
	
	public void initDatas()
	{
		// Rien pour le moment
		// Peut-être les pays, les sous de départ, la taille de la carte ce genre de choses qui peut varier selon les maps.
	}
	
	public void updateDatas()
	{

		// ArrayList<Base> b = client.getMyBases(); Notworking there

		// Appel aux différentes requêtes de manière asynchrones vers le serveur pour récupérer les données.
		
		// Attente de toutes les réponses. bloquant
		// Ce sera bloquant ici. Tant qu'il n'y a pas eu la première itération, rien ne bouge sur le client. Il est prêt à partir.
		updateDatas();
	}

}
