package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.AbstractAction;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
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

import model.Sim;
import control.Controller;

public class MainWindow {
	
	JFrame frmPowerLivreur;
	public final JComboBox<?> time_slots_comboBox;
	protected final SimViewPanel map_panel;
	//Component panel_top_btns;
	
	Sim sim;
	Controller contr;

    /**
     * Create the application.
     */
    @SuppressWarnings({ "rawtypes" })
    public MainWindow(Sim s, Controller c) {
    	sim = s;
    	contr = c;
        /*
    	controller = c;
        model = m;
        List<Object> timeSlots = new ArrayList<Object>(model.getTimeSlots());
        timeSlots.add(0,"Toutes les livraisons");
        time_slots_comboBox = new JComboBox(timeSlots.toArray());
        map_panel = new MapPanel(this, controller);
        */
    	map_panel = new SimViewPanel(s);
        //btnComputePath = new JButton();
        time_slots_comboBox = new JComboBox();
        initialize();
        frmPowerLivreur.setVisible(true);
    }

	public void initialize() {
		
		
        frmPowerLivreur = new JFrame();
        frmPowerLivreur.setTitle("Cod'INSA 2014 Final Round - Server");
        frmPowerLivreur.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                quit();
            }
        });

        //TODO frmPowerLivreur.setIconImage(Toolkit.getDefaultToolkit().getImage(MainWindow.class.getResource("/com/sun/java/swing/plaf/windows/icons/HomeFolder.gif")));
        //frmPowerLivreur.setIconImage(Toolkit.getDefaultToolkit().getImage(UI.class.getResource("com/sun/java/swing/plaf/motif/icons/DesktopIcon.gi")));
        frmPowerLivreur.setBounds(100, 100, 728, 599);
        frmPowerLivreur.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        JScrollPane scrollPane_1 = new JScrollPane();

        final JPanel main_panel = new JPanel();
        scrollPane_1.setViewportView(main_panel);


        // Catching keyboard: Ctrl-Z / Ctrl-Y for undo/redo

        main_panel.getActionMap().put("Undo", new AbstractAction("Undo") {
            private static final long serialVersionUID = 1L;
            
            public void actionPerformed(ActionEvent evt) {
            	//TODO if (!controller.undo()) System.err.println("Undo action has failed (nothing to undo).");
            }
        });
        main_panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()), "Undo");

        main_panel.getActionMap().put("Redo", new AbstractAction("Redo") {
            private static final long serialVersionUID = 1L;

            public void actionPerformed(ActionEvent evt) {
            	//TODO if (!controller.redo()) System.err.println("Redo action has failed (nothing to redo).");
            }
        });
        main_panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_Y, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()), "Redo");

        JScrollPane scrollPane = new JScrollPane();

        //TODO
        /**
        delivery_list = new JList<String>();
		
        scrollPane.setViewportView(delivery_list);
		*/
        JPanel panel_top_btns = new JPanel();
        panel_top_btns.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        final JButton btnPrint = new JButton("Imprimer");
        btnPrint.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
            	/**
                PrintRoadMap prm = new PrintRoadMap();
                try {
                    controller.execute(prm);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(new JFrame(), "Impossible de créer le fichier!", "Erreur", JOptionPane.ERROR_MESSAGE);
                    throw e;
                }
                JOptionPane.showMessageDialog(new JFrame(), "Le fichier a bien été imprimé : "+prm.getFilePath(), "Succès", JOptionPane.INFORMATION_MESSAGE);
                */
            }
        });
        
        //TODO xml_dependent_btns.add(btnPrint);
        
        
        frmPowerLivreur.getContentPane().setLayout(new BorderLayout(0, 0));

        //TODO
        /**
        btnComputePath.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if (controller.execute(new Optimize())) {
                    never_computed_path = false;
                    updateComputePathBtn(true);
                    if (!model.deliveriesOnTime())
                        JOptionPane.showMessageDialog(new JFrame(), "Certaines livraisons sont impossibles dans ces tranches horaires !", "Itinéraire impossible", JOptionPane.WARNING_MESSAGE);
                } else
                    JOptionPane.showMessageDialog(new JFrame(), "L'itinéraire n'a pas pu être calculé", "Temps dépassé", JOptionPane.ERROR_MESSAGE);
                updateTimeSlotDeliveryList();
            }
        });
        xml_dependent_btns.add(btnComputePath);
        */
        
        JButton btnChargerZoneDepuis = new JButton("Charger zone depuis XML");
        btnChargerZoneDepuis.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
            	//TODO loadZonePrompt();
            }
        });

        /**
        panel_top_btns.add(btnChargerZoneDepuis);
        panel_top_btns.add(btnComputePath);
        panel_top_btns.add(btnPrint);
        */

        GroupLayout gl_panel_1 = new GroupLayout(main_panel);
        gl_panel_1.setHorizontalGroup(
            gl_panel_1.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_panel_1.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
                        .addComponent(time_slots_comboBox, GroupLayout.PREFERRED_SIZE, 170, GroupLayout.PREFERRED_SIZE)
                        .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 170, GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING)
                        .addComponent(map_panel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(panel_top_btns, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addContainerGap())
        );
        gl_panel_1.setVerticalGroup (
            gl_panel_1.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_panel_1.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
                        .addComponent(time_slots_comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(panel_top_btns, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
                        .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(map_panel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addContainerGap())
        );

        main_panel.setLayout(gl_panel_1);
        frmPowerLivreur.getContentPane().add(scrollPane_1);

        JMenuBar menuBar = new JMenuBar();
        frmPowerLivreur.setJMenuBar(menuBar);

        JMenu mnFile = new JMenu("Application");
        menuBar.add(mnFile);

        JMenuItem mntmQuit = new JMenuItem("Quitter");
        mntmQuit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                quit();
            }
        });

        mnFile.add(mntmQuit);

        JMenu mnEdit = new JMenu("Edition");
        menuBar.add(mnEdit);

        final JMenuItem mntmUndo = new JMenuItem("Annuler");
        mntmUndo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                //TODO controller.undo();
            }
        });
        mnEdit.add(mntmUndo);

        final JMenuItem mntmRedo = new JMenuItem("Refaire");
        mntmRedo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
            	//TODO controller.redo();
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

        JMenu mnZone = new JMenu("Zone");
        menuBar.add(mnZone);

        JMenuItem mntmChargerUneZone = new JMenuItem("Charger une zone depuis XML");
        mntmChargerUneZone.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
            	//TODO loadZonePrompt();
            }
        });
        mnZone.add(mntmChargerUneZone);

        JSeparator separator = new JSeparator();
        mnZone.add(separator);

        final JMenuItem mntmZoomP = new JMenuItem("Zoom +");
        mnZone.add(mntmZoomP);

        final JMenuItem mntmZoomM = new JMenuItem("Zoom -");
        mnZone.add(mntmZoomM);

        final JMenuItem mntmZoomNormal = new JMenuItem("Zoom normal");
        mnZone.add(mntmZoomNormal);

		
	}
	

    /**
     * Exit the application.
     */
    void quit() {
        System.exit(0);
    }
	

}
