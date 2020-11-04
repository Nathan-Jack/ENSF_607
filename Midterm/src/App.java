
public class App {
	public static void main (String [] args) {
		
		Keyboard key = new Keyboard ();
		Speaker sp = new Speaker ();
		
		Computer comp  = new Computer (new CurvedMonitor 
                                (), new WideMonitor());
		
		comp.print();
		//Point 1
	}

}
