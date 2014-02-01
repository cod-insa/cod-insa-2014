package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

import model.Coord;
import model.Entity;
import model.Plane;
import model.Sim;

public class SimViewPanel extends JPanel {
	
    private static final long serialVersionUID = 1L;
    
	public final long refresh_period = 30;
	
	Sim sim;
	ViewTransform vtrans = new ViewTransform(getWidth());
	
	ArrayList<Plane> pls = new ArrayList<Plane>();// FIXME debug
	
    public SimViewPanel(Sim s) {
    	
        super();
        
        final SimViewPanel that = this;
        
    	sim = s;
    	
    	/********** DEV TEST: **********/
    	Random r = new Random();
    	int nb = 5;
    	for (int i = 0; i < nb; i++) {
    		Plane p = new Plane(new Coord(r.nextDouble(), r.nextDouble()).view);
	    	pls.add(p);
	    	sim._debug_backdoor().add(p);
	    	p.autoPilot.goTo(new Coord(r.nextDouble(), r.nextDouble()).view);
    	}
    	pls.get(0).autoPilot.goTo(pls.get(1));
    	pls.get(1).autoPilot.goTo(pls.get(0));
    	/*******************************/
    	

		new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
            	repaint();
            }
        }, refresh_period, refresh_period);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
            	
            	vtrans.setViewSize(getWidth());
            	
            	that.repaint();

            }
        });
    	

        addMouseListener(new MouseAdapter() {
        	
            @Override
            public void mouseReleased(MouseEvent e) {
            	
                switch (e.getButton()) {
                
                case MouseEvent.BUTTON1: // Left click
                	
                	/* TODO: do unit tests for this
                	System.out.println(e.getX());
                	System.out.println(vtrans.getCoord(new Pixel(e.getX(), e.getY())).x);
                	System.out.println(vtrans.getViewPos(vtrans.getCoord(new Pixel(e.getX(), e.getY())).view).x);
                	*/
                	
                	
                	/********** DEV TEST: **********/
                	//sim._debug_backdoor().add( new Plane( vtrans.getCoord(new Pixel(e.getX(), e.getY())).view ) );
                    
                	for (Plane p: pls)
                		p.autoPilot.goTo(vtrans.getCoord(new Pixel(e.getX(), e.getY())).view);

                	/*******************************/
                	
                    break;

                case MouseEvent.BUTTON3: // Right click
                	
                    break;

                }
                
            }

        });
        
        addMouseWheelListener(new MouseAdapter() {
        	
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
            	
            	double co = Math.pow(1.5, -e.getPreciseWheelRotation());
            	
            	vtrans.zoomIn(new Pixel(e.getX(), e.getY()), co);
            	
            }

        });
    	
	}


	@Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        
        Graphics2D g2d = (Graphics2D)g;

        RenderingHints rh = new RenderingHints (
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
            );

        rh.put(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_SPEED);

        g2d.setRenderingHints(rh);

        g2d.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_BEVEL));

        g2d.setColor(Color.lightGray);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        g2d.setColor(Color.blue);
        
        for (Entity e: sim.getEntities()) {
        	
        	e.getView().draw(g2d, vtrans);
        	
        }
        

        g2d.setStroke(new BasicStroke());

    }
	
	
}










