package messageHandlers;

import heart.MuxDemuxSimple;
import message.Message;
import model.Model;

public class DebugReceiver extends DebugHandler {
	private static boolean test=false;
	
	public DebugReceiver()
	{
		super();
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
