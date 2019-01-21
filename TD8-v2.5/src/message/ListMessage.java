package message;

public class ListMessage {
	private String senderID;
	private String peerID;
	private int sequence;
	private int totalParts;
	private int part;
	private String data;
	
	
	public ListMessage(String senderID, String peerID, int sequence, int totalParts, int part, String data)
	{
		this.senderID=senderID;
		this.peerID=peerID;
		this.sequence=sequence;
		this.totalParts=totalParts;
		this.part=part;
		this.data=data;
	}
	
	public ListMessage(String msg)
	{
		String result=msg;
		
		
		
		String[] parties=result.split(";");
		System.out.println("Message recu par le decoupeur - niveau ListMessage(String) in ListMessage: "+msg+" ; longueur des morceaux: "+parties.length);
		/*
		for(int i=0; i<parties.length; i++)
		{
			System.out.println(parties[i]);
		}
		*/
		if(parties.length>=7 && parties[0].equals("LIST"))
		{
			//on est en mesure de decouper
			senderID=parties[1];
			peerID=parties[2];
			
			try
			{
				sequence=Integer.parseInt(parties[3]);
				totalParts=Integer.parseInt(parties[4]);
				part=Integer.parseInt(parties[5]);
				
			}
			catch (NumberFormatException e)
			{
				System.out.println("Reception d'un LIST incorrect");
				sequence=-1;
				totalParts=0;
				part=0;
			}
			
			data=msg.substring((parties[0].length()+parties[1].length()+parties[2].length()+parties[3].length()+parties[4].length()+parties[5].length()+5)); //TODO: vérifier le bon indice
			System.out.println("data: "+data);
		}
		else
		{
			System.out.println("Reception d'un LIST incorrect");
			sequence=-1;
			totalParts=0;
			part=0;
			data="";
		}
	}
	
	public String getListMessageAsEncodedString()
	{
		String message="";
		message="LIST;"+this.senderID+";"+this.peerID+";"+this.sequence+";"+this.totalParts+";"+this.part+";"+this.data;
		
		return message;
	}
	
	
	
	public String toString()
	{
		String message="";
		message+="Message LIST d'attributs:\n";
		message+="senderId: "+this.senderID+"\n";
		message+="peerID: "+this.peerID+"\n";
		message+="sequence: "+this.sequence+"\n";
		message+="totalParts: "+this.totalParts+"\n";
		message+="part: "+this.part+"\n";
		message+="data: "+this.data+"\n";
		
		return message;
	}
	
	
	public String toStringCondense()
	{
		String message="";
		/*
		message+=this.senderID+";";
		message+=this.peerID+";";
		message+=this.sequence+";";
		message+=this.totalParts+";";
		message+=+this.part+";";
		*/
		//seule chose qui nous interesse
		message+=this.data;
		
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
	public int getTotalParts() {
		return totalParts;
	}
	public void setTotalParts(int totalParts) {
		this.totalParts = totalParts;
	}
	public int getPart() {
		return part;
	}
	public void setPart(int part) {
		this.part = part;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
}
