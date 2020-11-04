public class Computer {
	
	private Monitor monitorA, monitorB;
	private Keyboard keyboard;
	
	public Computer (Monitor a, Monitor b) {
		monitorA = a;
		monitorB = b;
	}
	public void setKeyboard(Keyboard key) {
		this.keyboard = key;
	}
	public void print () {
		Printer p = new Printer ();
		p.print();
	}
}
