package messageHandlers;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

import message.ListMessage;
import message.Message;

public class LISTReceiver extends LISTHandler {
	private static boolean testReception=true;
	private static boolean testBackground=false;
	
	//private HashMap<String, Collection<String>> listManager; //ne sert a rien dasn cette version
	
	
	public LISTReceiver()
	{
		super();
		//this.listManager=new HashMap<String, Collection<String>>();
	}
	
	
	public void run()
	{
		String msg;
		Message message;
		String peerID;
		ListMessage listMsg;
		Collection<String> reserve;
		while (true){
            message = incoming.poll();
            if(message!=null)
            {
            	msg=message.msg;
                if(msg!=null)
                {
                	//on regarde si cela nous concerne
                	if(isListMessage(msg))
            		{
                		listMsg=new ListMessage(msg);
            			if(testReception)
            			{
            				System.out.println("ListReceiver has received: "+msg+ " ; " +listMsg.toString());
            			}
            			
            			peerID=listMsg.getPeerID();
            			
            			this.model.addOneListMessage(listMsg);
            			
            		}
                }
            }
		}
	}
	
	
	
	
	
	
	
	
	
	
}
