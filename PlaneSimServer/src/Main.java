import game.Sim;
import control.Controller;
import display.Displayer;
import display.MainWindow;


public class Main {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//System.out.println("Hello World!");
		
		Displayer d = new Displayer();
		
		Sim s = new Sim(d);
		
		Controller c = new Controller();
		
		new MainWindow(d, s, c);
		
	}

}

