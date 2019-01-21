package heart;

import java.net.DatagramSocket;
import java.net.SocketException;

import messageHandlers.DebugReceiver;
import messageHandlers.HelloHandler;
import messageHandlers.HelloReceiver;
import messageHandlers.HelloSender;
import messageHandlers.LISTHandler;
import messageHandlers.LISTReceiver;
import messageHandlers.LISTSender;
import messageHandlers.SYNHandler;
import messageHandlers.SYNReceiver;
import messageHandlers.SYNSender;
import messageHandlers.SimpleMessageHandler;
import model.Database;

public class mainTest {

	public static void main(String[] args) {
		int myPort=4242;
		DatagramSocket mySocket;
		try {
			mySocket = new DatagramSocket(myPort);
			mySocket.setBroadcast(true);
			SimpleMessageHandler[] handlers = new SimpleMessageHandler[8];
			handlers[0]= new HelloHandler();
			handlers[1]=new HelloReceiver();
			handlers[2]=new HelloSender();
			handlers[3]=new DebugReceiver();
			handlers[4]=new LISTReceiver();
			handlers[5]=new LISTSender();
			handlers[6]=new SYNReceiver();
			handlers[7]=new SYNSender();
			
			
			
			
			
			
			
			
			MuxDemuxSimple dm = new MuxDemuxSimple(handlers, mySocket);
			
			handlers[0].setMuxDemux(dm);
			handlers[1].setMuxDemux(dm);
			handlers[2].setMuxDemux(dm);
			handlers[3].setMuxDemux(dm);
			handlers[4].setMuxDemux(dm);
			handlers[5].setMuxDemux(dm);
			handlers[6].setMuxDemux(dm);
			handlers[7].setMuxDemux(dm);

			
			
			
			
			new Thread((HelloHandler)(handlers[0]), "HelloHandlerThread").start();
			new Thread((HelloHandler)(handlers[1]), "HelloReceiverThread").start();
			new Thread((HelloHandler)(handlers[2]), "HelloSenderThread").start();
			new Thread((DebugReceiver)(handlers[3]), "DebugReceiverThread").start();
			new Thread((LISTHandler)(handlers[4]), "ListReceiverThread").start();
			new Thread((LISTHandler)(handlers[5]), "ListSenderThread").start();
			new Thread((SYNHandler)(handlers[6]), "SynReceiverThread").start();
			new Thread((SYNHandler)(handlers[7]), "SynSenderThread").start();

			
			
			new Thread(dm, "Multiplexor").start();
			
		} catch (SocketException e) {
			System.out.println("Error durant la creation de la datagramSocket");
			e.printStackTrace();
		}
	}

}
