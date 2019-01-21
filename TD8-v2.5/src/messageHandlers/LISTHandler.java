package messageHandlers;

import java.util.concurrent.ConcurrentLinkedQueue;

import heart.MuxDemuxSimple;
import message.Message;
import model.Model;

public class LISTHandler implements SimpleMessageHandler, Runnable {

	private static boolean test=true;
	private static boolean testDetail=false;
	
	
	protected MuxDemuxSimple dm;
	protected ConcurrentLinkedQueue<Message> incoming;
	protected Model model;
	
	public LISTHandler()
	{
		this.incoming=new ConcurrentLinkedQueue<Message>();
	}
	
	
	
	
	@Override
	public void handleMessage(String m, String IPAddress) {
		this.incoming.add(new Message(m, IPAddress));

	}

	@Override
	public void setMuxDemux(MuxDemuxSimple m) {
		this.dm=m;

	}

	@Override
	public void sendMessage(String m) {
		dm.send(m);

	}

	

	@Override
	public void setModel(Model model) {
		this.model=model;
		
	}
	
	
	@Override
	public void run() {
		while(true)
		{
			Message message;
			if(!incoming.isEmpty())
			{
				message=incoming.poll();
				if(isListMessage(message.msg))
				{
					if(test)
					{
						System.out.println("LISTHandler a reçu un ListMessage :"+message.toString());
					}
				}
				else
				{
					if(testDetail)
					{
						System.out.println("LISTHandler a reçu un message bizarre :"+message.toString());
					}
				}
			}
		}
		
	}
	
	
	
	public boolean isListMessage(String message)
	{
		
		String senderID;
		String destID;
		int sequence;
		int totalParts;
		int part;
		String data;
		
		
		String[] parties=message.split(";");
		//System.out.println("Message recu par le decoupeur: "+s);
		if(parties.length>=7 && parties[0].equals("LIST"))
		{
			//on est en mesure de decouper
			senderID=parties[1];
			destID=parties[2];
			
			try
			{
				sequence=Integer.parseInt(parties[3]);
				totalParts=Integer.parseInt(parties[4]);
				part=Integer.parseInt(parties[5]);
				
			}
			catch (NumberFormatException e)
			{
				if(testDetail)
				{
					System.out.println("Reception d'un LIST incorrect -niveau isListMesage in LISTHandler");
				}
				return false;
			}
			
			data=parties[6];
			if(destID.equals(this.model.getMyNetwork().getProprietaire().getPeerID()))
			{
				return true;
			}
			else
			{
				if(testDetail)
				{
					System.out.println("Reception d'un LIST destiné à quelqu'un d'autre -niveau isListMesage in LISTHandler");
				}
				return false;
			}
			
			
		}
		else
		{
			if(testDetail)
			{
				System.out.println("Reception d'un LIST incorrect -niveau isListMesage in LISTHandler");
			}
			return false;
		}
	}

}
