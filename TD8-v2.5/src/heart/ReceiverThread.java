package heart;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import messageHandlers.SimpleMessageHandler;

public class ReceiverThread implements Runnable {
	private static int taille=2000;
	private static boolean test=false;
	
	
	private DatagramPacket dp;
	private DatagramSocket mySocket;
	private SimpleMessageHandler[] myMessageHandlers;
	private byte[] buffer;
	
	
	public ReceiverThread(SimpleMessageHandler[] myMessageHandlers, DatagramSocket socket)
	{
		this.mySocket=socket;
		this.myMessageHandlers=myMessageHandlers;
		this.buffer=new byte[taille];
		
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		String request;
		while(true)
		{
			if(test)
			{
				System.out.println("Une boucle sur le receiver");
			}
			request="";
            try
            {
				
            	dp = new DatagramPacket(buffer, buffer.length);
            	mySocket.receive(dp);
            	String IPAddress=dp.getAddress().getHostAddress();
            	
            	request=new String(dp.getData(), 0, dp.getLength());
            	if(test)
            	{
            		System.out.println("Reception d'un message:" + dp.getAddress()+ " "+ dp.getPort() +" "+request);
            	}
            	
            	//System.out.println("Envoi des messages aux handlers");
				for(int i=0; i<this.myMessageHandlers.length; i++)
				{
					myMessageHandlers[i].handleMessage(request, IPAddress);
					//System.out.println(i);
				}
			} catch (IOException e) {
				System.out.println("Unable to get stream from clients");
				e.printStackTrace();
			}
		}

	}

}


