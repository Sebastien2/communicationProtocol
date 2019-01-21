package model;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

import heart.MuxDemuxSimple;
import message.ListMessage;

public class Network {
	private static boolean test=true;
	
	private Peer proprietaire;
	private HashMap<String, Peer> table;
	private MuxDemuxSimple dm;
	
	
	
	
	

	public Network() {
		this.proprietaire=new Peer();
		this.table=new HashMap<String, Peer>();
	}


	public Network(Peer proprietaire, HashMap<String, Peer> table) {
		
		this.proprietaire = proprietaire;
		this.table = table;
	}


	public Network(Peer proprietaire) {
		this.table=new HashMap<String, Peer>();
		this.proprietaire = proprietaire;
	}
	
	
	
	public synchronized void increment()
	{
		this.proprietaire.setSequence(this.proprietaire.getSequence()+1);
	}

	
	
	
	synchronized public void updatePeer(String peerID, int seq, int expiration, String IP)
	{
		Peer p;
		if(this.table.containsKey(peerID))
		{
			p=this.table.get(peerID);
			String state=p.getState();
			switch(state)
			{
			case "heard":
				if(p.getSequence()==seq)
				{
					p.setState("inconsistent");
					//ajouter à la liste des synchronizations à demander
					this.dm.addSynRequest(peerID);
				}
				else
				{
					if(p.getSequence()>seq)
					{
						p.setState("inconsistent");
						if(test)
						{
							System.out.println("Network updatePeer(peerID, seq, expiration, IP): situaion bizarre car sequence number superieur a celui recu...");
							p.setSequence(seq);
						}
					}
					if(p.getSequence()<seq)
					{
						p.setState("inconsistent");
						//ajouter à la liste des synchronizations à demander
						this.dm.addSynRequest(peerID);
					}
				}
				break;
			case "inexistent":
				if(p.getSequence()==seq)
				{
					p.setState("heard");
				}
				else
				{
					if(p.getSequence()>seq)
					{
						p.setState("inconsistent");
						if(test)
						{
							System.out.println("Network updatePeer(peerID, seq, expiration, IP): situaion bizarre car sequence number superieur a celui recu...");
							p.setSequence(seq);
						}
					}
					if(p.getSequence()<seq)
					{
						p.setState("inconsistent");
						//ajouter à la liste des synchronizations à demander
						this.dm.addSynRequest(peerID);
					}
				}
				break;
			case "synchronized":
				if(p.getSequence()==seq)
				{
					p.setState("synchronized");
				}
				else
				{
					if(p.getSequence()>seq)
					{
						p.setState("inconsistent");
						if(test)
						{
							System.out.println("Network updatePeer(peerID, seq, expiration, IP): situaion bizarre car sequence number superieur a celui recu...");
							p.setSequence(seq);
						}
					}
					if(p.getSequence()<seq)
					{
						p.setState("inconsistent");
						//ajouter à la liste des synchronizations à demander
						this.dm.addSynRequest(peerID);
					}
				}
				break;
			case "inconsistent":
				if(p.getSequence()==seq)
				{
					p.setState("synchronized");
				}
				else
				{
					if(p.getSequence()>seq)
					{
						p.setState("inconsistent");
						if(test)
						{
							System.out.println("Network updatePeer(peerID, seq, expiration, IP): situaion bizarre car sequence number superieur a celui recu...");
							p.setSequence(seq);
						}
					}
					if(p.getSequence()<seq)
					{
						p.setState("inconsistent");
						//ajouter à la liste des synchronizations à demander
						this.dm.addSynRequest(peerID);
					}
				}
				break;
			default:
				p.setState("heard");
				break;
			}
			
			//on met a jour l'expiration
			p.setExpiration(expiration);
		}
		else
		{
			p=new Peer();
			p.setPeerID(peerID);
			p.setExpiration(expiration);
			p.setIPAddress(IP);
			p.setSequence(seq);
			p.setState("heard");
			
		}
		
		//et on le met dasn la table
		this.table.put(peerID, p);
	}
	
	
	public synchronized void checkAllExpiration()
	{
		if(test)
		{
			System.out.println("Network checkAllExpiration() appele");
		}
		int now=(int)(System.currentTimeMillis()/1000);
		Collection<Peer> peersCollection=(Collection<Peer>)(this.table.values());
		Object[] peers=(Object[])peersCollection.toArray().clone();
		Peer current;
		for(int i=0; i<peers.length; i++)
		{
			current=(Peer)peers[i];
			if(current.getExpiration()<now)
			{
				this.table.remove(current.getPeerID());
			}
		}
	}
	
	
	
	
	

	public Peer getProprietaire() {
		return proprietaire;
	}


	synchronized void setProprietaire(Peer proprietaire) {
		this.proprietaire = proprietaire;
	}


	public HashMap<String, Peer> getTable() {
		return table;
	}


	synchronized void setTable(HashMap<String, Peer> table) {
		this.table = table;
	}
	
	
	
	public MuxDemuxSimple getDm() {
		return dm;
	}


	public void setDm(MuxDemuxSimple dm) {
		this.dm = dm;
	}

	
	
	
	public String toString()
	{
		String result="myDatabase:"+this.proprietaire.getPeerID()+", "+this.proprietaire.getSequence()+"\n";
		Collection<Peer> peersCollection=this.table.values();
		Object[] peers=(Object[])(peersCollection.toArray());
		
		Peer current;
		
		for(int i=0; i<peers.length; i++)
		{
			current=(Peer)(peers[i]);
			result+=current.toString()+"\n";
			
			
			
		}
		
		return result;
	}
	
	
}
