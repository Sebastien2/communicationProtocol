package messageHandlers;

import java.util.concurrent.ConcurrentLinkedQueue;

import message.ListMessage;
import message.Message;

public class LISTSender extends LISTHandler {
	
	private static boolean test=true;
	private static boolean testEnLocal=false;
	
	
	public LISTSender()
	{
		super();
	}
	
	
	public void run()
	{
		String peerID;
		int now=(int)(System.currentTimeMillis());
		int last=now;
		while(true)
		{
			now=(int)(System.currentTimeMillis());
			if(testEnLocal)
			{
				if(now-last>10*1000)
				{
					dm.addListRequest(this.model.getMyNetwork().getProprietaire().getPeerID());
					last=now;
				}
			}
			
			if(!this.dm.isEmptyListRequest())
			{
				peerID=this.dm.getListRequest();
				if(test)
				{
					System.out.println("LISTSender dealing with LIST request from "+peerID);
				}
				//Puis on s'occupe de la demande
				gererDemande(peerID);
			}
		}
	}
	
	

	private void gererDemande(String peerID)
	{
		String[] data=this.model.getMyLISTContent();
		ListMessage message;
		int len=data.length;
		for(int i=0; i<len; i++)
		{
			if(testEnLocal)
			{
				message=new ListMessage("baloo", peerID, this.model.getMyNetwork().getProprietaire().getSequence(), len, i, data[i]);
			}
			else
			{
				message=new ListMessage(this.model.getMyNetwork().getProprietaire().getPeerID(), peerID, this.model.getMyNetwork().getProprietaire().getSequence(), len, i, data[i]);
			}
			
			this.dm.send(message.getListMessageAsEncodedString());
		}
	}
}
