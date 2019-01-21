package messageHandlers;



import java.util.concurrent.ConcurrentLinkedQueue;

import heart.MuxDemuxSimple;
import message.Message;
import model.Model;

public class DebugHandler implements SimpleMessageHandler, Runnable {

	private static boolean test=false;
	
	protected MuxDemuxSimple dm;
	protected ConcurrentLinkedQueue<Message> incoming;
	protected Model model;
	
	
	public DebugHandler()
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
		this.dm.send(m);

	}

	@Override
	public void setModel(Model model) {
		this.model=model;
		
	}

	@Override
	public void run() {
		while(true)
		{
			Message mes;
			if(!this.incoming.isEmpty())
			{
				mes=this.incoming.poll();
				if(test)
				{
					System.out.println("Reception d'un message par DebugHandler: "+mes.toString());
				}
			}
		}
		
	}

}
