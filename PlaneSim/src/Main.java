import model.Sim;
import view.MainWindow;
import control.Controller;


public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//System.out.println("Hello World!");
		
		Sim s = new Sim();
		
		Controller c = new Controller();
		
		//UI ui = new UI(s, c);
		
		new MainWindow(s, c);
		
	}

}

