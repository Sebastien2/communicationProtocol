package model;

import java.util.HashMap;
import java.util.LinkedList;

import message.ListMessage;

public class Database {
	private static boolean test=true;
	
	private HashMap<String, HashMap<Integer, ListMessage>> table;
	
	public Database()
	{
		this.table=new HashMap<String, HashMap<Integer, ListMessage>>();
	}

	public HashMap<String, HashMap<Integer, ListMessage>> getTable() {
		return table;
	}

	public void setTable(HashMap<String, HashMap<Integer, ListMessage>> table) {
		this.table = table;
	}
	
	
	
	
	
	
	public HashMap<Integer, ListMessage> getDataOnePeer(String peerID)
	{
		HashMap<Integer, ListMessage> result;
		if(this.table.containsKey(peerID))
		{
			result=this.table.get(peerID);
		}
		else
		{
			result=new HashMap<Integer, ListMessage>();
		}
		return result;
	}
	
	
	
	synchronized public void addData(String peerID, ListMessage msg, int part)
	{
		HashMap<Integer, ListMessage> content;
		if(this.table.containsKey(peerID))
		{
			content=this.table.get(peerID);
		}
		else
		{
			content=new HashMap<Integer, ListMessage>();
		}
		content.put(part, msg);
		this.table.put(peerID, content);
		
	}
	
	
	synchronized public void removeOnePeer(String peerID)
	{
		this.table.remove(peerID);
	}
	
}
