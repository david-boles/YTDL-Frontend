package ytdlf.main;

import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.deb.lib.program.BasicSettingManager;
import com.deb.lib.program.Logger;
import com.deb.lib.program.ProgramFs;

/**
 * Outer program frame and start function. Contains all high-level components, including an instance of itself, in static variables.
 * @author David Boles
 *
 */
public class Main extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7840397581409395462L;
	public static BasicSettingManager sm = new BasicSettingManager();
	public static Main parentFrame;
	public static JTabbedPane parentPane;
	public static TabSettings tS;
	public static TabDownload tD;
	public static Logger logger;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		ProgramFs.setProgramOut(ProgramFs.getProgramFile("Log.txt"));
		ProgramFs.setProgramErr(ProgramFs.getProgramFile("Log_Errors.txt"));
		logger = new Logger(ProgramFs.getProgramFile("Log.txt"), ProgramFs.getProgramFile("Log_Errors.txt"));
		Logger.uLogger.log("Setting initialization return", sm.initialize(Settings.getInitial()));
		logger.setEnabled(sm.getSetting(8));
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			logger.log("UI LaF changed to system's successfully.");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e1) {
			logger.exception("UI LaF changing errored", e1);
		}
		
		parentFrame = new Main();
		parentFrame.setVisible(true);
		logger.log("Frame created and displayed");
		
		new Thread(new Runnable(){
			@Override
			public void run() {
				while(true) {
					
					if(sm.hasUpdates()) {
						sm.writeSettings();
						logger.log("Updated settings...");
					}
					
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						logger.exception("Setting writing Thread interrupted", e);
					}
				}
			}
		}).start();
	}

	/**
	 * Create the frame.
	 */
	public Main() {
		initialize();
	}
	private void initialize() {
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentMoved(ComponentEvent e) {
				try {sm.setSetting(7, new Rectangle((int)parentFrame.getLocation().getX(), (int)parentFrame.getLocation().getY(), parentFrame.getWidth(), parentFrame.getHeight()));}catch(Exception ee) {logger.exception("Component moved error " + e.getComponent().getName(), ee);}
			}
			@Override
			public void componentResized(ComponentEvent e) {
				try {sm.setSetting(7, new Rectangle((int)parentFrame.getLocation().getX(), (int)parentFrame.getLocation().getY(), parentFrame.getWidth(), parentFrame.getHeight()));}catch(Exception ee) {logger.exception("Component resized error " + e.getComponent().getName(), ee);}
			}
		});
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Rectangle pFR = sm.getSetting(7);
		setBounds((int)pFR.getMinX(), (int)pFR.getMinY(), (int)pFR.getWidth(), (int)pFR.getHeight());
		Main.parentPane = new JTabbedPane(JTabbedPane.TOP);
		Main.parentPane.setFont(new Font("DejaVu Sans", Font.BOLD, 14));
		setContentPane(Main.parentPane);
		this.setTitle("YTDL Frontend Version 0b");
		
		tD = new TabDownload();
		this.addTab(tD);
		
		tS = new TabSettings();
		this.addTab(tS);
	}
	
	/**
	 * Adds and instance of a class that implements Tab to the Parent Pane.
	 * @param t The tab to add.
	 */
	void addTab(Tab t) {
		Main.parentPane.addTab(t.getTitle(), t.getIcon(), t.getComponent(), t.getTip());
	}
	
	/**
	 * Causes a Runnable's run() to be executed when a JTextFields internal Document is changed in any way.
	 * @param f The JTextField.
	 * @param r The Runnable.
	 */
	public static void addJTFCL(JTextField f, Runnable r) {
		f.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {r.run();}
			@Override
			public void removeUpdate(DocumentEvent e) {r.run();}
			@Override
			public void changedUpdate(DocumentEvent e) {r.run();}
		});
	}
}
