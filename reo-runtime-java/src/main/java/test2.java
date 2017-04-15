/**
 * Generated from ../example/test.rba.treo by Reo 1.0.
 */

import nl.cwi.reo.runtime.java.*;

public class test2 {

  public static void main(String[] args) {

	    Port<String> a = new PortWaitNotify<String>();
	    Port<String> b = new PortWaitNotify<String>();
	    Port<String> c = new PortWaitNotify<String>();

	    Component Protocol0 = new Protocol0(a, b, c);
	    Component Comp0 = new WindowConsumer("c",c);
	    Component Comp1 = new WindowProducer0("a",a);
	    Component Comp2 = new WindowProducer1("b",b);
	    
	 	Thread thread_Protocol0 = new Thread(Protocol0); 
	 	Thread thread_Comp0 = new Thread(Comp0);
	 	Thread thread_Comp1 = new Thread(Comp1);
	 	Thread thread_Comp2 = new Thread(Comp2);
	 	
		thread_Protocol0.start();
		thread_Comp0.start();
		thread_Comp1.start();
		thread_Comp2.start();

	    try {
	      thread_Protocol0.join();
	      thread_Comp0.join();
	      thread_Comp1.join();
	      thread_Comp2.join();
	    } catch (InterruptedException e) {
	      e.printStackTrace();
	    }

	  }

  public static class WindowConsumer implements Component {
		private static Port<String> a;
		private static String name;
		
		public WindowConsumer(String name, Port<String> a) {
			a.setConsumer(this);
			WindowConsumer.name=name;
			WindowConsumer.a=a;
		}
		
		@Override
		public void activate() { 
			synchronized (this) {
				notify();	
			} 
		}

		@Override
		public void run() {	
			Windows.consumer(name,a);
		}
  }
  public static class WindowProducer0 implements Component {
		private static Port<String> a;
		private static String name;
		
		public WindowProducer0(String name, Port<String> a) {
			a.setProducer(this);
			WindowProducer0.name=name;
			WindowProducer0.a=a;
		}
		
		@Override
		public void activate() { 
			synchronized (this) {
				notify();	
			} 
		}

		@Override
		public void run() {	
			Windows.producer(name,a);
		}
  }
  public static class WindowProducer1 implements Component {
		private static Port<String> a;
		private static String name;
		
		public WindowProducer1(String name, Port<String> a) {
			a.setProducer(this);
			WindowProducer1.name=name;
			WindowProducer1.a=a;
		}
		
		@Override
		public void activate() { 
			synchronized (this) {
				notify();	
			} 
		}

		@Override
		public void run() {	
			Windows.producer(name,a);
		}
  }
  
  private static class Protocol0 implements Component {

  	private volatile Port<String> a;

  	private volatile Port<String> b;

  	private volatile Port<String> c;

  	private String q1;

  	public Protocol0(Port<String> a, Port<String> b, Port<String> c) {
  		a.setConsumer(this);
  		b.setConsumer(this);
  		c.setProducer(this);
  		this.a = a;
  		this.b = b;
  		this.c = c;
  		activate();
  	}

  	public synchronized void activate() {
  		notify();
  	}

  	public void run() {
  		int k = 0;
  		while (true) {
  			k++;
  			if (((q1 == null)) && c.hasGet() && a.hasPut() && b.hasPut()) {
  				c.put(a.get()); 
  				q1 = b.get(); 
  				k = 0;
  			}
  			if ((!((q1 == null))) && c.hasGet()) {
  				c.put(q1); 
  				q1 = null; 
  				k = 0;
  			}
  			if (((q1 == null)) && c.hasGet() && a.hasPut() && b.hasPut()) {
  				c.put(a.get()); 
  				q1 = b.get(); 
  				k = 0;
  			}
  			if (((q1 == null)) && c.hasGet() && a.hasPut() && b.hasPut()) {
  				c.put(a.get()); 
  				q1 = b.get(); 
  				k = 0;
  			}
  			if (((q1 == null)) && c.hasGet() && a.hasPut() && b.hasPut()) {
  				c.put(a.get()); 
  				q1 = b.get(); 
  				k = 0;
  			}
  			if ((!((q1 == null))) && c.hasGet()) {
  				c.put(q1); 
  				q1 = null; 
  				k = 0;
  			}
  			if (k > 3) {
  				k = 0;
  				synchronized (this) {
  					try { 
  						wait(); 
  					} catch (InterruptedException e) { }
  				}	
  			}
  		}
  	}
  }


}
