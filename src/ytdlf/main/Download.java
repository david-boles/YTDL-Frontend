package ytdlf.main;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 * Display pane and accompanying Processes, Threads, etc... and managing functions for an individual download.
 * @author David Boles
 *
 */
public class Download extends JPanel {
	private static final long serialVersionUID = -5801090657214895052L;
	
	/**
	 * Local variables.
	 */
	private Download vThis = this;
	Process p;
	BufferedReader bR;
	String output = "";
	String id = "";
	Thread processHandlerThread;
	
	/**
	 * Swing components
	 */
	String command;
	JButton stopB;
	JLabel state;
	JProgressBar progressBar;
	
	/**
	 * Initialize the JPanel and start the processHandler thread.
	 * @param command The command to start the youtube-dl process.
	 */
	public Download(String command) {
		this.command = command;
		Main.logger.log("Starting download", command);
		initialize();
		this.processHandlerThread = new Thread(this.processHandler);
		this.processHandlerThread.start();
	}
	
	/**
	 * Initialize the JPanel.
	 */
	private void initialize() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0};
		gridBagLayout.rowHeights = new int[]{25, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 1.0};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0};
		setLayout(gridBagLayout);
		
		this.stopB = new JButton("Stop");
		this.stopB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stopDownload();
			}
		});
		this.stopB.setFont(new Font("DejaVu Sans", Font.BOLD, 14));
		GridBagConstraints gbc_stopB = new GridBagConstraints();
		gbc_stopB.gridx = 0;
		gbc_stopB.gridy = 0;
		add(this.stopB, gbc_stopB);
		
		this.state = new JLabel();
		this.state.setHorizontalAlignment(SwingConstants.CENTER);
		this.state.setText("Starting...");
		this.state.setBorder(new EmptyBorder(0, 0, 0, 0));
		//this.state.setEditable(false);
		this.state.setFont(new Font("DejaVu Sans", Font.BOLD, 14));
		GridBagConstraints gbc_state = new GridBagConstraints();
		gbc_state.fill = GridBagConstraints.HORIZONTAL;
		gbc_state.gridx = 1;
		gbc_state.gridy = 0;
		add(this.state, gbc_state);
		//this.state.setColumns(16);
		
		this.progressBar = new JProgressBar();
		this.progressBar.setIndeterminate(true);
		this.progressBar.setFont(new Font("DejaVu Sans", Font.BOLD, 14));
		GridBagConstraints gbc_progressBar = new GridBagConstraints();
		gbc_progressBar.fill = GridBagConstraints.BOTH;
		gbc_progressBar.gridx = 2;
		gbc_progressBar.gridy = 0;
		add(this.progressBar, gbc_progressBar);
	}
	
	/**
	 * Declaration and initialization of the processHandler Runnable.
	 */
	Runnable processHandler = new Runnable() {
		@Override
		public void run() {
			try {
				p = Runtime.getRuntime().exec(command);
				bR = new BufferedReader(new InputStreamReader(p.getInputStream()));
				
				while(!Thread.interrupted()) {
					String line = bR.readLine();
					output += line + "\n";
					
					//ProgramFs.outPL(line);
					
					//Remove if complete
					if(line == null) stopDownload();
					
					if(!Thread.interrupted()) {
						String temp;
						
						//Getting video id
						if(id.equals("")) {
							temp = getStringBackwards(line, ':', ' ');
							if(temp != null) id = temp;
						}
						
						//Get percentage
						try{
							String sP = getStringBackwards(line, '%', ' ');
							float fP = Float.parseFloat(sP);
							int iP = (int)fP;
							progressBar.setIndeterminate(false);
							progressBar.setStringPainted(true);
							progressBar.setValue(iP);
							progressBar.setToolTipText("Downloading" + getStringBackwards(line, 'f'));
							
						}catch(Exception e) {Main.logger.exception(id + " progressBar update failed", e);}
						
						//Setting state text
						if(id != "" && progressBar.isIndeterminate()) state.setText("Starting: " + id);
						if(id != "" && !progressBar.isIndeterminate()) state.setText("Downloading: " + id);
						
						
						//Sleep
						try {
							Thread.sleep(500);
						} catch (InterruptedException e) { }
					}
				}
				
			} catch (IOException e) {
				Main.logger.exception(id + " processHandler Thread failed", e);
				stopDownload();
			}
			
		}
	};
	
	/**
	 * Gets a substring of s between the last instance of start, and the last instance of end before start.
	 * @param s Initial string.
	 * @param start Last char.
	 * @param end First char.
	 * @return The substring, exclusive, "" if start not found.
	 */
	String getStringBackwards(String s, char start, char end) {
		if(!s.contains(String.valueOf(start))) return null;
		if(!s.contains(String.valueOf(end))) return null;
		
		int startPos = 0;
		
		for(int pos = 0; pos < s.length(); pos++) {
			if(s.charAt(pos) == start) {
				startPos = pos;
				break;
			}
		}
		
		String out = "";
		
		for(int pos = startPos-1; pos >= 0; pos--) {
			if (s.charAt(pos)==end) break;
			out = s.charAt(pos) + out;
		}
		
		return out;
	}
	
	/**
	 * Gets the entire string after the last instance of end, exclusive.
	 * @param s Initial String.
	 * @param end First char.
	 * @return The entire string after the last instance of end, exclusive.
	 */
	String getStringBackwards(String s, char end) {
		String out = "";
		
		for(int pos = s.length()-1; pos >= 0; pos--) {
			if (s.charAt(pos)==end) break;
			out = s.charAt(pos) + out;
		}
		
		return out;
	}
	
	/**
	 * Stops the download:
	 * -Interrupts process handler
	 * -Sets state text to "Stopping"
	 * -Repaints parentFrame
	 * -Closes BufferedReader
	 * -Destroys process
	 * -Removes download from downloadManager
	 */
	void stopDownload() {
		Runnable r = new Runnable() {
			@Override
			public void run() {
				try {
					state.setText("Stopping");
					Main.parentFrame.repaint();
				}catch(Exception e) {Main.logger.exception(id + " stopDownload failed", e);}
				
				/*try {
					Thread.sleep(500);
				}catch(Exception e) {Main.logger.exception(id + " stopDownload failed", e);}*/
				
				try {
					processHandlerThread.interrupt();
				}catch(Exception e) {Main.logger.exception(id + " stopDownload failed", e);}

				try {
					bR.close();
				}catch(Exception e) {Main.logger.exception(id + " stopDownload failed", e);}

				try {
					p.destroyForcibly();
				}catch(Exception e) {Main.logger.exception(id + " stopDownload failed", e);}
				
				try {
					Main.tD.dM.removeDownload(vThis);
				}catch(Exception e) {Main.logger.exception(id + " stopDownload failed", e);}
			}};
		new Thread(r).run();
	}

}
