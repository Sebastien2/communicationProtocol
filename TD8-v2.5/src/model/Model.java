package model;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;

import heart.MuxDemuxSimple;
import message.HelloMessage;
import message.ListMessage;

public class Model {
	
	private static boolean test=true;
	
	
	private Database database;
	private Network myNetwork;
	private MuxDemuxSimple dm;
	
	
	
	
	public Model() {
		if(test)
		{
			System.out.println("Model: utilisation de Model()");
		}
		this.database=new Database();
		this.myNetwork=new Network();
	}
	
	public Model(String myID)
	{
		if(test)
		{
			System.out.println("Model: utilisation de Model(myID)");
		}
		Peer prop=new Peer(myID);
		prop.setSequence(0);
		this.database=new Database();
		this.myNetwork=new Network(prop);
		this.database.addData(myID, new ListMessage(myID, myID, 0, 1, 0, "Grifrigredinmenufrottin vint réclamer ce qui lui a été promis"), 0);
	}



	public Model(Database database, Network myNetwork) 
	{
		if(test)
		{
			System.out.println("Model: utilisation de Model(databsae, network)");
		}
		this.database=database;
		this.myNetwork=myNetwork;
	}



	public Model(Database database)
	{
		if(test)
		{
			System.out.println("Model: utilisation de Model(myDatabase)");
		}
		this.database=database;
		this.myNetwork=new Network();
	}
	
	
	public Model(Network myNetwork)
	{
		if(test)
		{
			System.out.println("Model: utilisation de Model(myDatabase)");
		}
		this.database=new Database();
		this.myNetwork=myNetwork;
	}



	public Database getDatabase() 
	{
		return database;
	}



	public void setDatabase(Database database) 
	{
		this.database = database;
	}

	
	public Network getMyNetwork() {
		return myNetwork;
	}

	public void setMyNetwork(Network myNetwork) {
		this.myNetwork = myNetwork;
	}

	public MuxDemuxSimple getDm() 
	{
		return dm;
	}

	public void setDm(MuxDemuxSimple dm) 
	{
		this.dm = dm;
		this.myNetwork.setDm(dm);
	}

	
	
	synchronized public void updatePeer(String peerID, int seq, int expiration, String IP)
	{
		this.myNetwork.updatePeer(peerID, seq, expiration, IP);
	}
	
	
	synchronized public void checkAllExpiration()
	{
		this.myNetwork.checkAllExpiration();
		//puis on nettoie la database
		Set<String> peersCollection=this.myNetwork.getTable().keySet();
		Object[] peers=(Object[])peersCollection.toArray().clone();
		for(int i=0; i<peers.length; i++)
		{
			if(!this.myNetwork.getTable().containsKey((String)peers[i]))
			{
				this.database.removeOnePeer((String)peers[i]);
			}
		}
	}
	
	
	
	
	synchronized public String[] getListPeers()
	{
		String[] result=new String[this.myNetwork.getTable().size()];
		Set<String> peersCollection=this.myNetwork.getTable().keySet();
		Object[] peers=(Object[])peersCollection.toArray().clone();
		for(int i=0; i<peers.length; i++)
		{
			result[i]=(String)peers[i];
		}
		return result;
	}
	
	
	
	synchronized public String[] getMyLISTContent()
	{
		HashMap<Integer, ListMessage> contenu=this.database.getDataOnePeer(this.myNetwork.getProprietaire().getPeerID());
		String[] result=new String[contenu.size()];
		Collection<ListMessage> collection=(Collection<ListMessage>)(contenu.values());
		Object[] messages=collection.toArray().clone();
		for(int i=0; i<messages.length; i++)
		{
			result[i]=((ListMessage)(messages[i])).getData();
		}
		
		return result;
	}
	
	
	public synchronized void addOneListMessage(ListMessage msg)
	{
		this.database.addData(msg.getSenderID(), msg, msg.getPart());
	}
	
	
	
	
	
}
