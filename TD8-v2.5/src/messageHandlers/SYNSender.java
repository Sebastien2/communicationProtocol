package messageHandlers;

import java.util.Collection;
import java.util.HashMap;

import message.SynMessage;
import model.Peer;

public class SYNSender extends SYNHandler {
	
	private static boolean test=true;
	private static int delay=7;
	
	
	private HashMap<String, Integer> dejaVus;
	
	public SYNSender()
	{
		super();
		this.dejaVus=new HashMap<String, Integer>();
		
	}
	
	public void run()
	{
		
		
		
		String peerID;
		int limitValidity;
		SynMessage demandeSYN;
		String msg;
		int now=(int)(System.currentTimeMillis()/1000);
		int last=now;
		while(true)
		{
			if(now-last>delay)
			{
				checkAllRequired();
				last=now;
			}
			
			now=(int)(System.currentTimeMillis()/1000);
			if(!this.dm.isEmptySynRequest())
			{
				
				peerID=this.dm.getSynRequest();
				if(test)
				{
					System.out.println("SynSender run(): extraction d'une demande de SYN a "+peerID);
				}
				boolean valid;
				if(this.dejaVus.containsKey(peerID))
				{
					limitValidity=this.dejaVus.get(peerID);
					if(limitValidity<now)
					{
						valid=true;
						//et on le retire de la base
						this.dejaVus.remove(peerID);
						this.dejaVus.put(peerID, now+delay);  //on va le traiter
					}
					else
					{
						valid=false;
					}
				}
				else
				{
					//Pas deja vus
					valid=true;
					this.dejaVus.put(peerID, now+delay);
				}
				
				if(valid)
				{
					//on traite la demande
					if(test)
					{
						System.out.println("SYNSender run(): envoi d'un SYN valide car pas de dejaVus");
					}
					demandeSYN=new SynMessage(this.model.getMyNetwork().getProprietaire().getPeerID(), peerID, this.model.getMyNetwork().getProprietaire().getSequence());
					msg=demandeSYN.getSYNMessageAsEncodedString();
					if(test)
		        	{
		        		System.out.println("SYNSender envoit :"+msg);
		        	}
					sendMessage(msg);
				}
			}
		}
	}
	
	
	private void checkAllRequired()
	{
		HashMap<String, Peer> table=(HashMap<String, Peer>)this.model.getMyNetwork().getTable().clone();
		int sequence=this.model.getMyNetwork().getProprietaire().getSequence();
		
		Collection<Peer> peersCollection=table.values();
		Object[] peers=(Object[])(peersCollection.toArray());
		Peer current;
		
		for(int i=0; i<peers.length;i++)
		{
			current=(Peer)peers[i];
			if(current.getState().equals("inconsistent"))
			{
				if(current.getSequence()>sequence)
				{
					this.dm.addSynRequest(current.getPeerID());
				}
			}
		}
		
		
	}
	
	
	
	
	
	
}
