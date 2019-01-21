package messageHandlers;

import message.Message;
import message.SynMessage;

public class SYNReceiver extends SYNHandler {
	private static boolean test=true;
	private static boolean testDetail=false;
	
	public SYNReceiver()
	{
		super();
	}
	
	
	
	public void run()
	{
		Message msg;
		while(true)
		{
			if(!this.incoming.isEmpty())
			{
				msg=this.incoming.poll();
				if(isSynMessage(msg.msg))
				{
					if(test)
					{
						System.out.println("Réception d'un SynMessage par SynReceiver: "+msg);
					}
					//Puis on le traite: demander l'envoi d'un LIST
					this.dm.addListRequest((new SynMessage(msg.msg)).getSenderID());
					
					
				}
				else
				{
					if(testDetail)
					{
						System.out.println("Réception d'un message non reconnu par SynReceiver");
					}
				}
			}
		}
	}
}
