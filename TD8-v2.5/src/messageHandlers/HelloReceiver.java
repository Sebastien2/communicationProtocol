package messageHandlers;

import message.HelloMessage;
import message.Message;
import model.Peer;

public class HelloReceiver extends HelloHandler {
	private static boolean testReception=false;
	private static boolean testBackground=true;
	private static int delay=3;
	
	
	
	public HelloReceiver()
	{
		super();
	}
	
	
	public void run()
	{
		int now=(int)System.currentTimeMillis();
		int last=now;
		Message msg;
		while(true)
		{
			if(!this.incoming.isEmpty())
			{
				msg=this.incoming.poll();
				if(isHelloMessage(msg.msg))
				{
					if(testReception)
					{
						System.out.println("Réception d'un HelloMessage par HelloReceiver: "+msg);
					}
					
					//Puis on le traite: changer d'état
					updateModel(msg);
					now=(int)System.currentTimeMillis();
					last=now;
					
				}
				else
				{
					if(testReception)
					{
						System.out.println("Réception d'un message non reconnu par HelloReceiver");
					}
				}
			}
			if(now-last>delay*1000)
			{
				this.model.checkAllExpiration();
				now=(int)System.currentTimeMillis();
				last=now;
			}
			now=(int)System.currentTimeMillis();
		}
	}
	
	
	
	
	private void updateModel(Message msg)
	{
		HelloMessage message=new HelloMessage(msg.msg);
		if(!message.getSenderID().equals(this.model.getMyNetwork().getProprietaire().getPeerID()))
		{
			if(testBackground)
			{
				System.out.println("Pris en en compte dans le modele de la reception d'un HelloMessage, mise a jour du modele - niveau updateModel dans HelloReceiver");
			}
			//on regarde le seq obtenu
			int seq=message.getSequence();
			int expiration=(int)(System.currentTimeMillis()/1000)+message.getHelloInterval();
			this.model.updatePeer(message.getSenderID(), seq, expiration, msg.IPAddress);
		}
		
		
	}
	
	
	
	
	
}
