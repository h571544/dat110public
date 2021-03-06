package no.hvl.dat110.network;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import no.hvl.dat110.application.Stopable;

public class Channel extends Stopable {

	private LinkedBlockingQueue<Datagram> datagramqueue;

	private Adversary observer;
	
	public Channel(String name, Adversary observer) {
		super(name);
		datagramqueue = new LinkedBlockingQueue<Datagram>();
		this.observer = observer;
	}

	public void send(Datagram datagram) {

		try {
			
			System.out.print("[Network:"+super.name+ "] transmit: " + datagram.toString());
			
			datagramqueue.put(observer.process(datagram));
			
		} catch (InterruptedException ex) {

			System.out.println("Channel send " + ex.getMessage());
			ex.printStackTrace();
		}

	}

	public Datagram receive() {

		Datagram data = null;

		try {
			data = datagramqueue.poll(2, TimeUnit.SECONDS);
		} catch (InterruptedException ex) {

			System.out.println("Channel receive " + ex.getMessage());
			ex.printStackTrace();

		}

		return data;
	}

	public void doProcess() {

		try {
			Thread.sleep(1000);
		} catch (InterruptedException ex) {

			System.out.println("Channel thread " + ex.getMessage());
			ex.printStackTrace();
		}
	}
}
