package heart;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.ConcurrentLinkedQueue;

import message.SynMessage;

public class SenderThread implements Runnable {
	private static int taille=2000;
	private static boolean test=false;
	private static int myPort=4242;
	
	
	private ConcurrentLinkedQueue<String> outgoing;
	private DatagramSocket mySocket;
	private byte[] buffer;
	
	
	public SenderThread(ConcurrentLinkedQueue<String> outgoing, DatagramSocket socket)
	{
		this.outgoing=outgoing;
		this.mySocket=socket;
		this.buffer=new byte[taille];
		
	}
	
	@Override
	public void run() {
		String m;
		DatagramPacket packet=new DatagramPacket(buffer, buffer.length);
		while(true)
		{
			
			if(!this.outgoing.isEmpty())
			{
				//on envoie un message
				m=this.outgoing.poll();
				try {
					packet=new DatagramPacket(m.getBytes(), m.length(), InetAddress.getByName("255.255.255.255"), myPort);
					mySocket.send(packet);
					if(test)
					{
						System.out.println("Message envoyé: "+m);
					}
				} catch (UnknownHostException e) {
					System.out.println("Error lors de l'envoi d'un paquet");
					e.printStackTrace();
				}catch(IOException e)
				{
					System.out.println("Error lors de l'envoi d'un paquet");
					e.printStackTrace();
				}
				
				//this.outgoing.add("Hello world");
			}
			
			
			
				
		}

	}

}
