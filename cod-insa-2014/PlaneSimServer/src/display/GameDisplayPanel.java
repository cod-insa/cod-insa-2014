package display;

import game.AutoPilot.Mode;
import game.Game;
import game.GameBase;
import game.GameEntity;
import game.GamePlane;
import game.World;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import model.Coord;
import model.Plane.State;
import model.Plane.Type;


public class GameDisplayPanel extends JPanel {
	
    private static final long serialVersionUID = 1L;
    
    
    private static final int SCREEN_MOVE_MARGIN = 130;
    private static final double SCREEN_MOVE_COEFF = .001, VIEW_INERTIA_DAMPING = .9;
    
    private static final double GAME_SHIFT_MARGIN = .4; //.2;

	private static final boolean RESTRICT_ZONE = false;
    
	public final long refresh_period = 30;
	
	Game sim;
	ViewTransform vtrans = new ViewTransform(getWidth());
	Displayer disp = new Displayer();
	
	ArrayList<GamePlane> pls = new ArrayList<GamePlane>();// FIXME debug
	
	final Timer repaint_timer = new Timer();
	
	private boolean rightBtnPressed = false, middleBtnPressed = false;
	Pixel lastMousePosition;
	final Coord lastShift = Coord.origin.copied();
	
	
    public GameDisplayPanel(Displayer disp, Game s) {
    	
        super();
        
        //s.setDisplayer(disp);
        this.disp = disp;
        
        vtrans.zoomIn(1./2);
        
        final GameDisplayPanel that = this;
        
    	sim = s;

    	/********** FIXME DEV TEST: **********/
    	
    	Random r = new Random();
//    	int nb = 5;
    	int nb = 2;
    	for (int i = 0; i < nb; i++) {
    		GamePlane p = new GamePlane(sim, new Coord.Unique(r.nextDouble(), r.nextDouble()), (i<nb/2?1:2), Type.MILITARY);
	    	pls.add(p);
	    	//sim._debug_backdoor().add(p);
	    	p.autoPilot.goTo(new Coord(r.nextDouble(), r.nextDouble()).view(), Mode.ATTACK_ON_SIGHT);
    	}
    	if (pls.size() > 1) {
	    	pls.get(0).autoPilot.goTo(pls.get(1), Mode.ATTACK_ON_SIGHT);
	    	pls.get(1).autoPilot.goTo(pls.get(0), Mode.ATTACK_ON_SIGHT);
    	}
    	
		//new Base(sim, new Coord(.3,.6));
    	
    	/*************************************/
    	
    	final Coord viewInertia = new Coord(0,0);
    	
    	repaint_timer.schedule(new TimerTask() {
            @Override
            public void run() {
            	
            	
            	repaint();
            	
            	
            	//if (mouse.x > 10)
            	//System.out.println(mouse.x);
            	
            	//if (mouse.x > 10)
            	
            	Point mouse = MouseInfo.getPointerInfo().getLocation();
            	
            	SwingUtilities.convertPointFromScreen(mouse, GameDisplayPanel.this);
            	
            	//System.out.println(mouse.x);
            	//SCREEN_MOVE_MARGIN = 25, SCREEN_MOVE_COEFF = 1
            	
            	if (rightBtnPressed) {
					if (RESTRICT_ZONE) {
						if (0 < mouse.x && mouse.x < getWidth() && 0 < mouse.y && mouse.y < getHeight()) {

							if (0 < mouse.x && mouse.x < SCREEN_MOVE_MARGIN) {
								vtrans._shift.x += (viewInertia.x = -(SCREEN_MOVE_MARGIN - mouse.x) * SCREEN_MOVE_COEFF);
							} else if (getWidth() - SCREEN_MOVE_MARGIN < mouse.x && mouse.x < getWidth()) {
								vtrans._shift.x += (viewInertia.x = (mouse.x - getWidth() + SCREEN_MOVE_MARGIN) * SCREEN_MOVE_COEFF);
							}
							if (0 < mouse.y && mouse.y < SCREEN_MOVE_MARGIN) {
								vtrans._shift.y += (viewInertia.y = -(SCREEN_MOVE_MARGIN - mouse.y) * SCREEN_MOVE_COEFF);
							} else if (getHeight() - SCREEN_MOVE_MARGIN < mouse.y && mouse.y < getHeight()) {
								vtrans._shift.y += (viewInertia.y = (mouse.y - getHeight() + SCREEN_MOVE_MARGIN) * SCREEN_MOVE_COEFF);
							}

						} else {
							viewInertia.mult(VIEW_INERTIA_DAMPING);
							vtrans._shift.add(viewInertia.view());
						}
					} else {

						if (mouse.x < SCREEN_MOVE_MARGIN) {
							vtrans._shift.x += (viewInertia.x = -(SCREEN_MOVE_MARGIN - mouse.x) * SCREEN_MOVE_COEFF);
						} else if (getWidth() - SCREEN_MOVE_MARGIN < mouse.x) {
							vtrans._shift.x += (viewInertia.x = (mouse.x - getWidth() + SCREEN_MOVE_MARGIN) * SCREEN_MOVE_COEFF);
						}
						if (mouse.y < SCREEN_MOVE_MARGIN) {
							vtrans._shift.y += (viewInertia.y = -(SCREEN_MOVE_MARGIN - mouse.y) * SCREEN_MOVE_COEFF);
						} else if (getHeight() - SCREEN_MOVE_MARGIN < mouse.y) {
							vtrans._shift.y += (viewInertia.y = (mouse.y - getHeight() + SCREEN_MOVE_MARGIN) * SCREEN_MOVE_COEFF);
						}

					}
				} else {
					viewInertia.set(Coord.origin);
					
					if (middleBtnPressed) {
						//vtrans.getCoord(new Pixel(mouse.x - lastMousePosition.x, mouse.y - lastMousePosition.y))
						
//						vtrans._shift.set(lastShift.addedTo(
//								vtrans.getCoord(new Pixel(
//										mouse.x - lastMousePosition.x,
//										mouse.y - lastMousePosition.y
//								)).view()
//						).view());
//
//						System.out.println("m  "+mouse);
//						System.out.println("lm "+lastMousePosition);
//
//						System.out.println("++ "+vtrans.getCoord(new Pixel(
//								mouse.x - lastMousePosition.x,
//								mouse.y - lastMousePosition.y
//						)));
////						System.out.println(">> "+vtrans._shift);
						
						
//						Coord offset = vtrans.getCoord(new Pixel(mouse.x, mouse.y));
//						offset.add(vtrans.getCoord(lastMousePosition).view(), -1);
						
						Coord offset = vtrans.getCoord(lastMousePosition);
						offset.add(vtrans.getCoord(new Pixel(mouse.x, mouse.y)).view(), -1);

						vtrans._shift.set(lastShift.addedTo(offset.view()).view());
						
						
					}
					
				}
				
            	
            	Coord bottom_right = vtrans.getCoord(new Pixel(getWidth(), getHeight()));
            	bottom_right.sub(vtrans.shift);
            	
				World w = sim.getWorld();
				
            	if (vtrans._shift.x > w.width + GAME_SHIFT_MARGIN - bottom_right.x)
            		vtrans._shift.x = w.width + GAME_SHIFT_MARGIN - bottom_right.x;
            	
            	if (vtrans._shift.x < -GAME_SHIFT_MARGIN)
            		vtrans._shift.x = -GAME_SHIFT_MARGIN;
            	
            	if (vtrans._shift.y > w.height + GAME_SHIFT_MARGIN - bottom_right.y)
            		vtrans._shift.y = w.height + GAME_SHIFT_MARGIN - bottom_right.y;
            	
            	if (vtrans._shift.y < -GAME_SHIFT_MARGIN)
            		vtrans._shift.y = -GAME_SHIFT_MARGIN;
            	
            	
            	
            }
        }, refresh_period, refresh_period);
		
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
            	
            	vtrans.setViewSize(getWidth());
            	
            	that.repaint();

            }
        });
        
//        
//        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
//        		KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), "Undo");
//        getActionMap().put("Undo", new AbstractAction("Undo") {
//            private static final long serialVersionUID = 1L;
//
//            public void actionPerformed(ActionEvent evt) {
//            	System.err.println("LOL!!");
//            }
//        });
    	
        
        addMouseListener(new MouseAdapter() {

//            @Override
//        	public void mouseEntered(MouseEvent e) {
//        		
//        	}
//            
//            @Override
//        	public void mouseExited(MouseEvent e) {
//        		
//        	}
        	
            @Override
            public void mouseReleased(MouseEvent e) {
            	
                switch (e.getButton()) {
                
                case MouseEvent.BUTTON1: // Left click
                	
                	/* TODO: do unit tests for this
                	System.out.println(e.getX());
                	System.out.println(vtrans.getCoord(new Pixel(e.getX(), e.getY())).x);
                	System.out.println(vtrans.getViewPos(vtrans.getCoord(new Pixel(e.getX(), e.getY())).view).x);
                	*/
                	
                	
                	/********** FIXME DEV TEST: **********/
                	
                	//sim._debug_backdoor().add( new Plane( vtrans.getCoord(new Pixel(e.getX(), e.getY())).view ) );
                    
                	/*
                	for (Plane p: pls)
                		p.autoPilot.goTo(vtrans.getCoord(new Pixel(e.getX(), e.getY())).view(), Mode.IGNORE);
					*/
                	
                	Coord.View mousePos = vtrans.getCoord(new Pixel(e.getX(), e.getY())).view();
                	GameBase b = null;
                	for (GameBase bb : sim.bases) {
                		if (bb.modelView().position.distanceTo(mousePos) < bb.radius())
                			b = bb;
                	}
                	GamePlane p = pls.get(0);
                	if (b == null) {
                		p.autoPilot.goTo(mousePos, Mode.IGNORE);
//                		p.autoPilot.goTo(new Base(sim, new Coord.Unique(mousePos.x(), mousePos.y())), Mode.IGNORE);
                		
                		
//           		 		p.autoPilot.goTo(mousePos, Mode.ATTACK_ON_SIGHT);
                	//else if (p.model().state() == State.AT_AIRPORT && b.model().planes().contains(p.modelView))
                	}
                    else if (p.modelView().state() == State.AT_AIRPORT && b.modelView().planes().size() > 0 && b.modelView().planes().get(0).id() == p.modelView().id()) // ugly hack
                		{ p.autoPilot.takeOff(); p.autoPilot.mode = Mode.ATTACK_ON_SIGHT; }
                	else pls.get(0).autoPilot.landAt(b);
                	
                	
                	/*for (Plane p: pls)
                		((NetworkPlayer)DataUpdater.get().getPlayers().get(0)).addCommand(
                				new MoveCommand(p.id, vtrans.getCoord(new Pixel(e.getX(), e.getY())).view));*/
                	
                	/*************************************/
                	
                    break;

				case MouseEvent.BUTTON2: // Middle click
					
					middleBtnPressed = false;
					
					break;
				
                case MouseEvent.BUTTON3: // Right click

					
					////////////////////////////
					// Bad dirty copy & paste
					mousePos = vtrans.getCoord(new Pixel(e.getX(), e.getY())).view();
					b = null;
					for (GameBase bb : sim.bases) {
						if (bb.modelView().position.distanceTo(mousePos) < bb.radius())
							b = bb;
					}
					p = pls.get(1);
					if (b == null) {
						p.autoPilot.goTo(mousePos, Mode.IGNORE);
					}
					else if (p.modelView().state() == State.AT_AIRPORT && b.modelView().planes().size() > 0 && b.modelView().planes().get(0).id() == p.modelView().id()) // ugly hack
					{ p.autoPilot.takeOff(); p.autoPilot.mode = Mode.ATTACK_ON_SIGHT; }
					else pls.get(1).autoPilot.landAt(b);
					////////////////////////////
					
					
					
					

					rightBtnPressed = false;
					
                    break;

                }
                
            }

			@Override
			public void mousePressed(MouseEvent e) {

				switch (e.getButton()) {

					case MouseEvent.BUTTON2: // Middle click

						if (!middleBtnPressed) {
							lastMousePosition = new Pixel(e.getX(), e.getY());
							lastShift.set(vtrans._shift.view());
//							System.out.println(lastShift);
//							System.out.println(lastMousePosition);
						}
						
						middleBtnPressed = true;

						break;
					
					case MouseEvent.BUTTON3: // Right click
						
						rightBtnPressed = true;

						break;
				
				}

			}
		});
        
        addMouseWheelListener(new MouseAdapter() {
        	
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
            	
            	double co = Math.pow(1.5, -e.getWheelRotation());
            	
            	vtrans.zoomIn(new Pixel(e.getX(), e.getY()), co);
            	
            }

        });
    	
	}
    
    public void dispose() {
    	repaint_timer.cancel();
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
        
//        g2d.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));
        g2d.setStroke(new BasicStroke(4*(float)vtrans._scale.x, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));
        
        
        g2d.setColor(Color.lightGray);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        
        //g2d.setColor(Color.blue);
        
        
        //for (List<Entity<?>> els : disp.entities) System.out.println(els.size());
        
        //for (Entity e: sim.getEntities()) {
        //for (Entity e: sim.entities.get()) {
        
        
        synchronized (disp) {
			for (List<GameEntity> els : disp.entitiesByAltitude)
				for (GameEntity e: els) {
					//if (e instanceof Base) System.out.println("ok");
					
					e.getDisplay().draw(g2d, vtrans);
					
				}

			for (GameEntity e: sim.entities) {
				e.getDisplay().drawOverlay(g2d, vtrans);
			}
			
        }
        
        g2d.setStroke(new BasicStroke());
        
        g2d.setColor(Color.black);
        Pixel top_left = vtrans.getViewPos(new Coord(0,0).view());
        Pixel bottom_right = vtrans.getViewPos(new Coord(sim.getWorld().width, sim.getWorld().height).view());
        g2d.drawRect(top_left.x, top_left.y, bottom_right.x-top_left.x, bottom_right.y-top_left.y);
        
        
        g2d.setColor(Color.white);
        g2d.drawString(sim.getInfoString(), 0, 10);
        


    }
	
	
}


















