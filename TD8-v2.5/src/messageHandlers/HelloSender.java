package messageHandlers;

import message.HelloMessage;

public class HelloSender extends HelloHandler {
	private static boolean test=true;
	private static int timeLapse=5;
	private static int timeExpiration=10;
	
	public HelloSender()
	{
		super();
	}
	
	
	public void run()
	{
		String msg;
		HelloMessage hello;
		while (true)
		{
			hello=new HelloMessage(this.model.getMyNetwork().getProprietaire().getPeerID(),this.model.getMyNetwork().getProprietaire().getSequence() , timeExpiration);
			String[] peers=this.model.getListPeers();
        	hello.setPeer(peers);
        	
        	msg=hello.getHelloMessageAsEncodedString();
			if(test)
        	{
        		System.out.println("HelloSender envoit :"+msg);
        	}
			sendMessage(msg);
			try {
				Thread.sleep(timeLapse*1000);
			} catch (InterruptedException e) {
				
				System.out.println("Erreur lors de l'attente dans la boucle de HelloSender");
				e.printStackTrace();
			}
			if(test)
			{
				System.out.println(this.model.getMyNetwork().toString());
			}
			
			System.out.println("\n\n\n");
            
        }
	}
}
