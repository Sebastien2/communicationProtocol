package model;

public class Peer {
	private static int delay=20;  //temps par défaut d'expiration
	
	private String peerID;
	private String IPAddress;
	private String state;
	private int sequence;
	private int expiration;
	private int lastUpdate;
	
	
	public Peer()
	{
		super();
		this.peerID="";
		this.IPAddress="";
		this.sequence=-1;
		this.expiration=0;
		this.lastUpdate=(int)(System.currentTimeMillis()/1000);
		this.state="";
	}
	
	
	public Peer(String peerID)
	{
		super();
		this.peerID=peerID;
		this.IPAddress="";
		this.sequence=-1;
		this.expiration=0;
		this.lastUpdate=(int)(System.currentTimeMillis()/1000);
		this.state="";
	}
	
	public Peer(String peerID, int sequence)
	{
		super();
		this.IPAddress="";
		this.peerID = peerID;
		this.sequence = sequence;
		this.expiration=(int)(System.currentTimeMillis()/1000)+delay;
		this.lastUpdate=(int)(System.currentTimeMillis()/1000);
		this.state="";
	}


	public Peer(String peerID, int sequence, int expiration, int lastUpdate) {
		super();
		this.IPAddress="";
		this.peerID = peerID;
		this.sequence = sequence;
		this.expiration = expiration;
		this.lastUpdate = lastUpdate;
		this.state="";
	}
	
	public Peer(String peerID, int sequence, String state, int expiration, int lastUpdate) {
		super();
		this.IPAddress="";
		this.peerID = peerID;
		this.sequence = sequence;
		this.expiration = expiration;
		this.lastUpdate = lastUpdate;
		this.state=state;
	}
	
	public Peer(String peerID, int sequence, int expiration) {
		super();
		this.IPAddress="";
		this.peerID = peerID;
		this.sequence = sequence;
		this.expiration = expiration;
		this.lastUpdate = (int)(System.currentTimeMillis()/1000);
		this.state="";
	}


	public Peer(String peerID, String iPAddress, int sequence, int expiration, int lastUpdate) {
		super();
		this.peerID = peerID;
		this.IPAddress = iPAddress;
		this.sequence = sequence;
		this.expiration = expiration;
		this.lastUpdate = lastUpdate;
		this.state="";
	}
	
	public Peer(String peerID, String iPAddress, int sequence, int expiration) {
		super();
		this.peerID = peerID;
		this.IPAddress = iPAddress;
		this.sequence = sequence;
		this.expiration = expiration;
		this.lastUpdate = (int)(System.currentTimeMillis()/1000);
		this.state="";
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
		update();
	}


	public String getPeerID() {
		return peerID;
	}


	public void setPeerID(String peerID) {
		this.peerID = peerID;
		update();
	}


	public String getIPAddress() {
		return IPAddress;
	}


	public void setIPAddress(String iPAddress) {
		IPAddress = iPAddress;
		update();
	}


	public int getSequence() {
		return sequence;
	}


	public void setSequence(int sequence) {
		this.sequence = sequence;
		update();
	}


	public int getExpiration() {
		return expiration;
	}


	public void setExpiration(int expiration) {
		this.expiration = expiration;
		update();
	}


	public int getLastUpdate() {
		return lastUpdate;
	}


	public void setLastUpdate(int lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	
	
	private void update()
	{
		this.lastUpdate=(int)(System.currentTimeMillis()/1000);
	}
	
	
	
	public String toString()
	{
		String result="";
		
		result+="Peer: ";
		result+=this.peerID+" ; "+this.IPAddress+" ; "+this.sequence+" ; "+this.state+" ; "+this.expiration+" ; "+this.lastUpdate;
		
		
		return result;
	}
	
	
	
}
