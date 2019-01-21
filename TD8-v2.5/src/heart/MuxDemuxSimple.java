package heart;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.concurrent.ConcurrentLinkedQueue;

import messageHandlers.SimpleMessageHandler;
import model.Database;
import model.Model;

public class MuxDemuxSimple implements Runnable{

	private static String myID="Fawkes";
	
	
    private DatagramSocket mySocket = null;
    private String myIPAddress;
    private BufferedReader in;  //pour pouvoir envoyer des messages depuis la console
    private SimpleMessageHandler[] myMessageHandlers;
    private ConcurrentLinkedQueue<String> outgoing = new ConcurrentLinkedQueue<String>();
    private ConcurrentLinkedQueue<String> synRequests=new ConcurrentLinkedQueue<String>();  //Contient les SYN que l'on doit envoyer
    private ConcurrentLinkedQueue<String> listRequests=new ConcurrentLinkedQueue<String>();  //contient les LIST que l'on doit envoyer
    
    private Model model;
    private Thread receiver;
    private Thread sender;
    
    private boolean test=true;
    
	

    MuxDemuxSimple (SimpleMessageHandler[] h, DatagramSocket s){
    	
    	this.model=new Model(myID);
    	model.setDm(this);
        this.mySocket = s;
        this.myIPAddress="";
        this.in=new BufferedReader(new InputStreamReader(System.in));
        this.myMessageHandlers = h;
        this.shareModel();
        
        this.receiver=new Thread(new ReceiverThread(this.myMessageHandlers, this.mySocket), "Receiver");
        this.sender=new Thread(new SenderThread(this.outgoing, this.mySocket), "Sender");
        
    }
    
    public void addSynRequest(String peerID)
    {
    	this.synRequests.add(peerID);
    }
    
    public boolean isEmptySynRequest()
    {
    	return this.synRequests.isEmpty();
    }
    
    public String getSynRequest()
    {
    	return this.synRequests.poll();
    }
    
    public void addListRequest(String peerID)
    {
    	this.listRequests.add(peerID);
    }
    
    public boolean isEmptyListRequest()
    {
    	return this.listRequests.isEmpty();
    }
    
    public String getListRequest()
    {
    	return this.listRequests.poll();
    }
    
    
    
    
    public void shareModel()
    {
    	for(int i=0; i<this.myMessageHandlers.length; i++)
    	{
    		this.myMessageHandlers[i].setModel(this.model);
    	}
    }
    
    
    
    

    public void run(){
    	
        for (int i=0; i<myMessageHandlers.length; i++)
        {
            myMessageHandlers[i].setMuxDemux(this);
            myMessageHandlers[i].setModel(this.model);
        }
        
        this.receiver.start();
        this.sender.start();
        
        String line;
        boolean continuer=true;
        while(true && continuer)
        {
        	try {
				if((line=in.readLine())!=null)
				{
					this.outgoing.add(line);
				}
			} catch (IOException e) {
				
				e.printStackTrace();
			}
        }
        
        mySocket.close();
        
    }

    public void send(String s){
        outgoing.add(s);	
    }
    
    
}