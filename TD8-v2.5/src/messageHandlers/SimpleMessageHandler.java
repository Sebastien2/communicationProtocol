package messageHandlers;

import java.util.concurrent.ConcurrentLinkedQueue;

import heart.MuxDemuxSimple;
import model.Model;

public interface SimpleMessageHandler {
	public void handleMessage(String m, String IPAddress);
	public void setMuxDemux(MuxDemuxSimple m);
	public void sendMessage(String m);
	public void setModel(Model model);

}
