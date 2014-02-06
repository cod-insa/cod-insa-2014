import model.Sim;
import control.Controller;
import display.MainWindow;


public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//System.out.println("Hello World!");
		
		Sim s = new Sim();
		
		Controller c = new Controller();
		
		new MainWindow(s, c);
		
	}

}

