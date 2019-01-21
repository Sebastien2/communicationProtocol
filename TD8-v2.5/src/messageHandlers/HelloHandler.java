package messageHandlers;

import java.util.concurrent.ConcurrentLinkedQueue;

import heart.MuxDemuxSimple;
import message.Message;
import model.Model;

public class HelloHandler implements SimpleMessageHandler, Runnable {
	private static boolean test=true;
	private static boolean testDetail=false;
	
	
	protected MuxDemuxSimple dm;
	protected ConcurrentLinkedQueue<Message> incoming;
	protected Model model;
	
	
	public HelloHandler()
	{
		this.incoming=new ConcurrentLinkedQueue<Message>();
	}
	
	
	
	
	public void setModel(Model model)
	{
		this.model=model;
	}
	
	
	@Override
	public void handleMessage(String m, String IPAddress) {
		incoming.add(new Message(m, IPAddress));

	}

	@Override
	public void setMuxDemux(MuxDemuxSimple m) {
		this.dm=m;

	}

	@Override
	public void sendMessage(String m) {
		this.dm.send(m);

	}

	@Override
	public void run() {
		Message msg;
		while(true)
		{
			if(!this.incoming.isEmpty())
			{
				msg=incoming.poll();
				if(test)
				{
					System.out.println("HelloHandler reçoit le message "+msg.toString());
				}
			}
		}
		
	}
	
	
	
	
	protected boolean isHelloMessage(String msg)
	{
		
		
		
		String senderID;
		int sequence;
		int helloInterval;
		int numPeers;
		int maxPeers=255;
		String peer[]=new String[maxPeers];
		
		String[] parties=msg.split(";");
		//System.out.println("Message recu par le decoupeur: "+s);
		if(parties.length>=5)
		{
			//on est en mesure de decouper
			senderID=parties[1];
			try
			{
				sequence=Integer.parseInt(parties[2]);
				helloInterval=Integer.parseInt(parties[3]);
				numPeers=Integer.parseInt(parties[4]);
			}
			catch (NumberFormatException e)
			{
				if(testDetail)
				{
					System.out.println("Reception d'un Hello incorrect e1 -niveau: isHelloMessage dans HelloHandler");
				}
				return false;
			}
			peer=new String[maxPeers];
			if(parties.length>=5+numPeers)
			{
				for(int i=0; i<numPeers; i++)
				{
					peer[i]=parties[5+i];
				}
				return true;
			}
			else
			{
				if(testDetail)
				{
					System.out.println("Reception d'un Hello incorrect e2 -niveau: isHelloMessage dans HelloHandler");
				}
				return false;
			}
		}
		else
		{
			if(testDetail)
			{
				System.out.println("Reception d'un Hello incorrect e3 -niveau: isHelloMessage dans HelloHandler");
			}
			return false;
		}
		
		
		
	}
	
	
	
	
	
	

}
