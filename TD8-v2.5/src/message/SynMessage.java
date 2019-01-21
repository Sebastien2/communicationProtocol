package message;

public class SynMessage {
	private String senderID;
	private String peerID;
	private int sequence;
	
	public SynMessage(String s)
	{
		String result=s;
		String[] parties=s.split(";");
		if(parties.length>=4)
		{
			//on est en mesure de decouper
			this.senderID=parties[1];
			this.sequence=Integer.parseInt(parties[3]);
			this.peerID=parties[2];
			
		}
		else
		{
			this.senderID="";
			this.sequence=-1;
			this.peerID="";
			System.out.println("Reception d'un SYN incorrect");
		}
	}
	
	public SynMessage(String senderID, String peerID, int sequenceNo)
	{
		this.senderID=senderID;
		this.peerID=peerID;
		this.sequence=sequenceNo;
	}
	
	
	
	
	
	public String getSYNMessageAsEncodedString()
	{
		String message="";
		message="SYN;"+this.senderID+";"+this.peerID+";"+this.sequence+";";
		
		return message;
	}
	
	
	
	public String toString()
	{
		String message="";
		message+="Message SYN d'attributs:\n";
		message+="senderId: "+this.senderID+"\n";
		message+="peerID: "+this.peerID+"\n";
		message+="sequence: "+this.sequence+"\n";
		
		return message;
	}
	
	
	
	
	
	
	
	
	
	
	public String getSenderID() {
		return senderID;
	}

	public void setSenderID(String senderID) {
		this.senderID = senderID;
	}

	public String getPeerID() {
		return peerID;
	}

	public void setPeerID(String peerID) {
		this.peerID = peerID;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
}
