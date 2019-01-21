package message;

public class Message {

	public String msg;
	public String IPAddress;
	
	public Message(String msg, String IPAddress)
	{
		this.msg=msg;
		this.IPAddress=IPAddress;
	}
	
	public Message()
	{
		this.msg="";
		this.IPAddress="";
	}	
	
	
	public String toString()
	{
		String result="";
		result="Message de "+this.IPAddress+": "+this.msg;
		return result;
	}
}
