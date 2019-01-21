package messageHandlers;

import java.util.concurrent.ConcurrentLinkedQueue;

import heart.MuxDemuxSimple;
import message.Message;
import model.Model;

public class SYNHandler implements SimpleMessageHandler, Runnable {

	private static boolean test=false;
	
	
	protected MuxDemuxSimple dm;
	protected ConcurrentLinkedQueue<Message> incoming;
	protected Model model;
	
	public SYNHandler()
	{
		this.incoming=new ConcurrentLinkedQueue<Message>();
	}
	
	
	
	@Override
	public void setModel(Model model) {
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
					System.out.println("SYNHandler reçoit le message "+msg.toString());
				}
			}
		}
		
	}
	
	
	
	
	protected boolean isSynMessage(String msg)
	{
		
		String senderID;
		String destID;
		int sequence;
		
		
		String[] parties=msg.split(";");
		if(parties.length>=4 && parties[0].equals("SYN"))
		{
			
			senderID=parties[1];
			destID=parties[2];
			try
			{
				sequence=Integer.parseInt(parties[3]);
				
			}
			catch (NumberFormatException e)
			{
				if(test)
				{
					System.out.println("Reception d'un SYN incorrect -niveau isSynMessage in SYNHandler");
				}
				return false;
			}
			if(destID.equals(this.model.getMyNetwork().getProprietaire().getPeerID()))
			{
				return true;
			}
			else
			{
				if(test)
				{
					System.out.println("Reception d'un SYN destiné à quelqu'un d'autre -niveau isSynMessage in SYNHandler");
				}
				return false;
			}
			
			
		}
		else
		{
			if(test)
			{
				System.out.println("Reception d'un SYN incorrect -niveau isSynMessage in SYNHandler");
			}
			return false;
		}
	}

	

}
