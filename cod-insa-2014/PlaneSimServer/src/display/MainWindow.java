package display;

import game.Sim;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import common.Event;

public class MainWindow {
	
	JFrame frmPlaneSim;
	public final JComboBox some_comboBox;
	protected final SimDisplayPanel sim_panel;
	
    protected final JButton btnBar = new JButton();
    public JList some_list;
	
	private Sim sim;
	//private Controller contr;
	
//	public interface Exiter {
//		public void exit();
//	}
//	final Exiter exiter;
	final Event onExit;
	
    /**
     * Create the application.
     */
    @SuppressWarnings({ })
    public MainWindow(Displayer disp, Sim s, Event onExit) {
    	sim = s;
    	//contr = s.getSetpUpdater();
        this.onExit = onExit;
        
    	sim_panel = new SimDisplayPanel(disp, s);
    	
    	List<Object> slots = new ArrayList<Object>();
        slots.add(0,"All");
        some_comboBox = new JComboBox(slots.toArray());
        
        btnBar.setText("Bar");

        //initialize();
        //frmPlaneSim.setVisible(true);
        
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                initialize();
                frmPlaneSim.setVisible(true);
            }
        });
        
//		initialize();
//		frmPlaneSim.setVisible(true);
		
    }

	public void initialize() {
		
		
        frmPlaneSim = new JFrame();
        frmPlaneSim.setTitle("Cod'INSA 2014 Final Round - Server");
        frmPlaneSim.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                quit();
            }
        });
        
        frmPlaneSim.setIconImage(Toolkit.getDefaultToolkit().getImage(MainWindow.class.getResource("/com/sun/java/swing/plaf/motif/icons/DesktopIcon.gif")));
        frmPlaneSim.setBounds(100, 100, 728, 599);
        frmPlaneSim.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        JScrollPane scrollPane_1 = new JScrollPane();

        final JPanel main_panel = new JPanel();
        scrollPane_1.setViewportView(main_panel);
        

        // Catching keyboard: Ctrl-Z / Ctrl-Y for undo/redo

        main_panel.getActionMap().put("Undo", new AbstractAction("Undo") {
            private static final long serialVersionUID = 1L;
            
            public void actionPerformed(ActionEvent evt) {
            	//TODO
            }
        });
        main_panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()), "Undo");

        main_panel.getActionMap().put("Redo", new AbstractAction("Redo") {
            private static final long serialVersionUID = 1L;

            public void actionPerformed(ActionEvent evt) {
            	//TODO
            }
        });
        main_panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_Y, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()), "Redo");

        JScrollPane scrollPane = new JScrollPane();

        //TODO
        
        some_list = new JList();
		
        scrollPane.setViewportView(some_list);
		
        JPanel panel_top_btns = new JPanel();
        panel_top_btns.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));
        final JButton btnBaz = new JButton("Baz");
        btnBaz.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
            	// TODO
            }
        });
        
        frmPlaneSim.getContentPane().setLayout(new BorderLayout(0, 0));
        
        btnBar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                // TODO
            }
        });
        
        JButton btnFoo = new JButton("Foo");
        btnFoo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
            	//TODO
            }
        });
        
        JButton btnExit = new JButton("<<  Exit  >>");
        btnExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
            	quit();
            }
        });
        
        
        panel_top_btns.add(btnFoo);
        panel_top_btns.add(btnBar);
        panel_top_btns.add(btnBaz);
        panel_top_btns.add(btnExit);
        

        GroupLayout gl_panel_1 = new GroupLayout(main_panel);
        gl_panel_1.setHorizontalGroup(
            gl_panel_1.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_panel_1.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
                        .addComponent(some_comboBox, GroupLayout.PREFERRED_SIZE, 170, GroupLayout.PREFERRED_SIZE)
                        .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 170, GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING)
                        .addComponent(sim_panel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(panel_top_btns, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addContainerGap())
        );
        gl_panel_1.setVerticalGroup (
            gl_panel_1.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_panel_1.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
                        .addComponent(some_comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(panel_top_btns, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
                        .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(sim_panel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addContainerGap())
        );

        main_panel.setLayout(gl_panel_1);
        frmPlaneSim.getContentPane().add(scrollPane_1);

        JMenuBar menuBar = new JMenuBar();
        frmPlaneSim.setJMenuBar(menuBar);

        JMenu mnFile = new JMenu("Application");
        menuBar.add(mnFile);

        JMenuItem mntmExit = new JMenuItem("Exit");
        mntmExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                quit();
            }
        });

        mnFile.add(mntmExit);

        JMenu mnEdit = new JMenu("Edition");
        menuBar.add(mnEdit);

        final JMenuItem mntmUndo = new JMenuItem("Undo");
        mntmUndo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                //TODO
            }
        });
        mnEdit.add(mntmUndo);

        final JMenuItem mntmRedo = new JMenuItem("Redo");
        mntmRedo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
            	//TODO
            }
        });
        mnEdit.add(mntmRedo);

        mnEdit.addMenuListener(new MenuListener() {
            public void menuCanceled(MenuEvent arg0) { }
            public void menuDeselected(MenuEvent arg0) { }
            public void menuSelected(MenuEvent arg0) {
            	//TODO mntmUndo.setEnabled(controller.canUndo());
            	//TODO mntmRedo.setEnabled(controller.canRedo());
            }
        });
        
        JMenu mnDisplay = new JMenu("Display");
        menuBar.add(mnDisplay);
        
        JMenuItem mntmChargerUneZone = new JMenuItem("Some Action");
        mntmChargerUneZone.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
            	//TODO
            }
        });
        mnDisplay.add(mntmChargerUneZone);
        
        JSeparator separator = new JSeparator();
        mnDisplay.add(separator);
        
        final double zoomRatio = 1.3;
        
        final JMenuItem mntmZoomP = new JMenuItem("Zoom +");
        mntmZoomP.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
            	sim_panel.vtrans.zoomIn(new Pixel(sim_panel.getWidth()/2, sim_panel.getHeight()/2), zoomRatio);
            }
        });
        mnDisplay.add(mntmZoomP);
        
        final JMenuItem mntmZoomM = new JMenuItem("Zoom -");
        mntmZoomM.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
            	sim_panel.vtrans.zoomIn(new Pixel(sim_panel.getWidth()/2, sim_panel.getHeight()/2), 1/zoomRatio);
            }
        });
        mnDisplay.add(mntmZoomM);
        
        final JMenuItem mntmZoomNormal = new JMenuItem("Zoom normal");
        mntmZoomNormal.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
            	sim_panel.vtrans.zoomReset();
            }
        });
        mnDisplay.add(mntmZoomNormal);
        
	}
	
    /**
     * Exit the application.
     */
    void quit() {
    	this.frmPlaneSim.dispose();
    	sim.stop();
        //System.exit(0);
    	//System.out.println("HMI has been closed. Server still be running!"); // FIXME try exiting the server
    	onExit.call();
    }
	

}










