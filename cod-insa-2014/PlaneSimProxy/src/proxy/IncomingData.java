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
		// Peut-�tre les pays, les sous de d�part, la taille de la carte ce genre de choses qui peut varier selon les maps.
	}
	
	public void updateDatas()
	{

		int id;
		try {
			id = client.connect("Banane");
			client.retrieveData(id);
		} catch (TException e) {
			e.printStackTrace();
		}
		
		
		// ArrayList<Base> b = client.getMyBases(); Notworking there

		// Appel aux diff�rentes requ�tes de mani�re asynchrones vers le serveur pour r�cup�rer les donn�es.
		
		// Attente de toutes les r�ponses. bloquant
		// Ce sera bloquant ici. Tant qu'il n'y a pas eu la premi�re it�ration, rien ne bouge sur le client. Il est pr�t � partir.
		updateDatas();
	}

}
