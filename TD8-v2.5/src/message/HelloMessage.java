package message;

import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import model.Peer;

public class HelloMessage extends Message {
	private static boolean test=true;
	
	
	private String senderID;
	private int sequence;
	private int helloInterval;
	private int numPeers;
	private String[] peer;
	
	private static int maxPeers=255;
	
	
	
	//constructeurs
	public HelloMessage()
	{
		this.senderID="";
		this.sequence=-1;
		this.helloInterval=-1;
		this.numPeers=0;
		this.peer=new String[0];
	}
	
	
	
	public HelloMessage(String message)
	{
		String[] parts=message.split(";");
		int longueur=parts.length;
		if(longueur>=5)
		{
			if(parts[0].equals("HELLO"))
			{
				try
				{
					this.senderID=parts[1];
					this.sequence=Integer.parseInt(parts[2]);
					this.helloInterval=Integer.parseInt(parts[3]);
					this.numPeers=Integer.parseInt(parts[4]);
				}catch(NumberFormatException e)
				{
					this.senderID="";
					this.sequence=-1;
					this.helloInterval=-1;
					this.numPeers=0;
					this.peer=new String[0];
				}
				if(this.numPeers>0)
				{
					this.peer=new String[this.numPeers];
					for(int i=0; i>this.numPeers; i++)
					{
						this.peer[i]="";
					}
					for(int i=0; i<min(parts.length-5, this.numPeers); i++)
					{
						this.peer[i]=parts[i+5];
					}
				}
				
			}
			else
			{
				if(test)
				{
					System.out.println("Réception d'un HelloMessage incorrect e2 -niceau HelloMessage(String)  in HelloMessage");
				}
			}
		}
		else
		{
			if(test)
			{
				System.out.println("Réception d'un HelloMessage incorrect e1 -niceau HelloMessage(String)  in HelloMessage, longueur: "+longueur+"; message: "+message);
			}
		}
	}
	
	
	
	public HelloMessage(String senderID, int sequenceNo, int HelloInterval)
	{
		this.setSenderID(senderID);
		this.sequence=sequenceNo;
		this.helloInterval=HelloInterval;
		this.numPeers=0;
		this.peer=new String[maxPeers];
	}
	
	
	public HelloMessage(String senderID, int sequenceNo, int HelloInterval, ConcurrentLinkedQueue<Peer> peers)
	{
		this.setSenderID(senderID);
		this.sequence=sequenceNo;
		this.helloInterval=HelloInterval;
		this.numPeers=peers.size();
		this.peer=new String[maxPeers];
		for(int i=0; i<peers.size(); i++)
		{
			this.peer[i]=peers.poll().getPeerID();
		}
	}
	
	
	public HelloMessage(String senderID, int sequenceNo, int HelloInterval, HashMap<String, Peer> peers)
	{
		this.setSenderID(senderID);
		this.sequence=sequenceNo;
		this.helloInterval=HelloInterval;
		this.numPeers=peers.size();
		this.peer=new String[maxPeers];
		Collection<Peer> collection=peers.values();
		Peer[] listePeers=(Peer[])collection.toArray();
		for(int i=0; i<listePeers.length; i++)
		{
			this.peer[i]=listePeers[i].getPeerID();
		}
	}
	
	
	//toString
	public String toString()
	{
		String message="";
		message+="Message HELLO d'attributs:\n";
		message+="senderId: "+this.senderID+"\n";
		message+="sequence: "+this.sequence+"\n";
		message+="HelloInterval: "+this.helloInterval+"\n";
		message+="NumPeers; "+this.numPeers+"\n";
		message+="List of peers: ";
		for(int i=0; i<this.numPeers;i++)
		{
			message+=this.peer[i]+";";
		}
		return message;
	}
	
	public String getHelloMessageAsEncodedString()
	{
		String message="";
		message="HELLO;"+this.senderID+";"+this.sequence+";"+this.helloInterval+";"+this.numPeers;
		for(int i=0; i<this.numPeers; i++)
		{
			message+=";"+this.peer[i];
		}
		message+=";";
		return message;
	}
	
	
	
	
	//outils
	private int min(int a, int b)
	{
		if(a>b)
		{
			return b;
		}
		else
		{
			return a;
		}
	}


	//getters, setters
	
	public String getSenderID() {
		return senderID;
	}



	public void setSenderID(String senderID) {
		this.senderID = senderID;
	}



	public int getSequence() {
		return sequence;
	}



	public void setSequence(int sequence) {
		this.sequence = sequence;
	}



	public int getHelloInterval() {
		return helloInterval;
	}



	public void setHelloInterval(int helloInterval) {
		this.helloInterval = helloInterval;
	}



	public int getNumPeers() {
		return numPeers;
	}



	public void setNumPeers(int numPeers) {
		this.numPeers = numPeers;
	}



	public String[] getPeer() {
		return peer;
	}



	public void setPeer(String[] peer) {
		this.numPeers=peer.length;
		this.peer = peer;
	}



	public static int getMaxPeers() {
		return maxPeers;
	}



	public static void setMaxPeers(int maxPeers) {
		HelloMessage.maxPeers = maxPeers;
	}
	
}
