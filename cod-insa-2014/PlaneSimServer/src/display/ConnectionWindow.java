package display;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import players.NetworkPlayer;
import players.NetworkPlayerManager;

import common.Event;

public class ConnectionWindow {
	
	JFrame frmPlayersCon;
	private Map<Integer,JLabel> labels = new HashMap<>();
	
	JButton startBeforeBtn, cancelBtn;
	
	static final int imgSize = 30;
	
	static Image pendingImage = Toolkit.getDefaultToolkit().getImage(MainWindow.class.getResource(Resources.WAITING_ICON));
	static Image connectedImage = Toolkit.getDefaultToolkit().getImage(MainWindow.class.getResource(Resources.CONNECTED_ICON));
	static Image disconnectedImage = Toolkit.getDefaultToolkit().getImage(MainWindow.class.getResource(Resources.DISCONNECTED_ICON));
	
	static {
		pendingImage = pendingImage.getScaledInstance(imgSize, imgSize, Image.SCALE_SMOOTH);
		connectedImage = connectedImage.getScaledInstance(imgSize, imgSize, Image.SCALE_SMOOTH);
		disconnectedImage = disconnectedImage.getScaledInstance(imgSize, imgSize, Image.SCALE_SMOOTH);
	}
	
	public ConnectionWindow(final NetworkPlayerManager npm, final Event onClose) {
		
		//        EventQueue.invokeLater(new Runnable() {
		//            @Override
		//            public void run() {
		//                initialize(npm, onClose);
		//                frmPlayersCon.setVisible(true);
		//            }
		//        });
		initialize(npm, onClose);
		frmPlayersCon.setVisible(true);
		
	}
	
	public void initialize(final NetworkPlayerManager npm, final Event onCancel) {
		
		frmPlayersCon = new JFrame();
		frmPlayersCon.setTitle("Player connections overview");
		frmPlayersCon.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				close();
				onCancel.call();
			}
		});
		
		//frmPlayersCon.setIconImage(Toolkit.getDefaultToolkit().getImage(MainWindow.class.getResource("/com/sun/java/swing/plaf/motif/icons/DesktopIcon.gif")));
		//frmPlayersCon.setIconImage(Toolkit.getDefaultToolkit().getImage(MainWindow.class.getResource("rsc/icons/Pelfusion-Folded-Flat-Air-Plane.ico")));
		//frmPlayersCon.setIconImage(Toolkit.getDefaultToolkit().getImage(MainWindow.class.getResource("rsc/icons/Air-Plane-icon.png")));
		
		//        java.net.URL imgURL = getClass().getResource("/rsc/icons/Air-Plane-icon.png");
		//        //java.net.URL imgURL = getClass().getResource("/rsc/icons/Pelfusion-Folded-Flat-Air-Plane.ico");
		//        //java.net.URL imgURL = getClass().getResource("http://icons.iconarchive.com/icons/pelfusion/folded-flat/512/Air-Plane-icon.png");
		//        ImageIcon image = new ImageIcon(imgURL);
		//        frmPlayersCon.setIconImage(image.getImage());
		
		//frmPlayersCon.setIconImage(Toolkit.getDefaultToolkit().getImage(MainWindow.class.getResource(Resources.CONNECTION_ICON)));
		
		ImageIcon image = new ImageIcon(getClass().getResource(Resources.CONNECTION_ICON));
		frmPlayersCon.setIconImage(image.getImage());
		
		
		//frmPlayersCon.setBounds(100, 100, 728, 599);
		//frmPlayersCon.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		//frmPlayersCon.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		//frmPlayersCon.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		/*
        IconListPanelModel model = new IconListPanelModelImpl();
        Icon icon = new ImageIcon("icon.gif");
        for (int i = 0 ; i < 201 ; i++) {
                model.getItems().add(new IconListItemImpl(icon, "item string "+i));
        }
        
        IconListPanel iconListPanel = new IconListPanel();
        iconListPanel.setModel(model);
        
        frmPlayersCon.getContentPane().add(new JScrollPane(iconListPanel, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
		 */
		
//		int imgSize = 40;
		
		//frmPlayersCon.add(new JLabel(s, new ImageIcon(, SwingConstants.LEFT));
		//Image pendingImage = Toolkit.getDefaultToolkit().getImage(MainWindow.class.getResource("/com/sun/java/swing/plaf/motif/icons/DesktopIcon.gif"));
//		Image pendingImage = Toolkit.getDefaultToolkit().getImage(MainWindow.class.getResource(Resources.WAITING_ICON));
//		pendingImage = pendingImage.getScaledInstance(imgSize, imgSize, Image.SCALE_SMOOTH);
		
//		Box verticalBox = Box.createVerticalBox();
		
//		frmPlayersCon.setLayout(new GridBagLayout());
//		frmPlayersCon.setLayout(verticalBox);
//		frmPlayersCon.setSize(400, 400);
		
		//JPanel panel = new JPanel();
		Container cont = frmPlayersCon.getContentPane();
		
//		frmPlayersCon.setLayout(new GridLayout(0, 1));
		
		GridLayout gl = new GridLayout(0,1);
		//gl.setColumns(1);
		int margin = 5;
		gl.setHgap(margin); //Cinq pixels d'espace entre les colonnes (H comme Horizontal)
		gl.setVgap(margin);
		
		//frmPlayersCon.setLayout(gl);

        JPanel main_panel = new JPanel();
        main_panel.setLayout(new FlowLayout(FlowLayout.CENTER, margin, margin));
//        main_panel.setLayout(new BoxLayout(main_panel, BoxLayout.Y_AXIS));
        
        
        frmPlayersCon.add(main_panel);
        
        JPanel panel = new JPanel();
        //panel.setLayout(gl);
        
        BoxLayout bl = new BoxLayout(panel, BoxLayout.Y_AXIS);
        
        panel.setLayout(bl);
        
        //panel.setPreferredSize(new Dimension(1000,1));
        //panel.setMinimumSize(new Dimension(1000,2000));
        
        main_panel.add(panel);
		
//		frmPlayersCon.setLayout(new GridBagLayout());
//		GridBagConstraints gbc = new GridBagConstraints();
		
//		frmPlayersCon.add(panel, new GridBagConstraints());
//		panel.setBorder(new LineBorder(Color.BLACK));
		
		
		for (NetworkPlayer p: npm.getPlayers()) {
			//frmPlayersCon.add(new JLabel("[pending] " + p.name+" ("+p.getNickname()+")", new ImageIcon(i), SwingConstants.LEFT));
			JLabel lbl = new JLabel("" + p.name+ "...", new ImageIcon(pendingImage), SwingConstants.LEFT);
			panel.add(lbl); // , gbc
			labels .put(p.connectionId, lbl);
			panel.add(Box.createRigidArea(new Dimension(0,margin)));
			
		}
		
		
		panel.add(Box.createRigidArea(new Dimension(0,margin*2)));
		panel.add(new JSeparator(JSeparator.HORIZONTAL),
		          BorderLayout.LINE_START);
		panel.add(Box.createRigidArea(new Dimension(0,margin)));
		
		panel.add(startBeforeBtn = new JButton("Start without waiting", UIManager.getIcon("OptionPane.warningIcon")));
		
		panel.add(Box.createRigidArea(new Dimension(0,margin)));
		
//		JButton cancelBtn = new JButton("Close this connection window", UIManager.getIcon("OptionPane.errorIcon"));
//		panel.add(cancelBtn);
		panel.add(cancelBtn = new JButton("Cancel and exit the server", UIManager.getIcon("OptionPane.errorIcon")));
		
		startBeforeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				npm.startWithoutWaiting();
				startBeforeBtn.setText("Started without waiting!");
			}
		});
		
		cancelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { close(); onCancel.call(); }
		});

		frmPlayersCon.pack();
		//frmPlayersCon.setSize(-1,100);
		
		//frmPlayersCon.setLocationRelativeTo( null );
		
	}
	
	public void notifyGameStarted() {
		startBeforeBtn.setEnabled(false);
		cancelBtn.setText("Close this window");
		ImageIcon image = new ImageIcon(getClass().getResource(Resources.CONNECTIONS_ICON));
		frmPlayersCon.setIconImage(image.getImage());
	}
	public void notifyConnect(NetworkPlayer p) {
		labels.get(p.connectionId).setIcon(new ImageIcon(connectedImage)); //
		labels.get(p.connectionId).setText(p.name+" ("+p.getNickname()+")");
	}
    public void notifyDisconnect(NetworkPlayer p) {
        labels.get(p.connectionId).setIcon(new ImageIcon(disconnectedImage)); // throw new Error();
    }
    public void notifyTimeoutStatus(NetworkPlayer p, boolean timeouting) {
        labels.get(p.connectionId).setIcon(UIManager.getIcon("OptionPane.warningIcon")); // throw new Error();
    }
	
	public void close() {
		//System.out.println("CLOS");
		frmPlayersCon.dispose();
		//System.out.println(frmPlayersCon.isActive());
	}
	
}











