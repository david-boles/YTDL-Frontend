package ytdlf.main;

import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;

import javax.swing.JPanel;

/**
 * Manages, stores, and displays individual downloads.
 * @author David Boles
 *
 */
public class DownloadManager extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -355571129222870974L;
	
	/**
	 * ArrayList of downloads
	 */
	ArrayList<Download> downloads = new ArrayList<>();

	/**
	 * Create the panel.
	 */
	public DownloadManager() {

		initialize();
	}
	
	/**
	 * Initialize the JPanel
	 */
	private void initialize() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{};
		gridBagLayout.rowHeights = new int[]{};
		gridBagLayout.columnWeights = new double[]{1};
		gridBagLayout.rowWeights = new double[]{};
		setLayout(gridBagLayout);
	}
	
	/**
	 * Add a new download to the JPanel and downloads ArrayList
	 * @param command The command to be passed to the new Download instance
	 */
	public void addDownload(String command) {
		Download d = new Download(command);
		this.downloads.add(d);
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = this.downloads.size();
		gbc.anchor = GridBagConstraints.BELOW_BASELINE_LEADING;
		add(d, gbc);
		
		Main.parentFrame.repaint();
	}
	
	/**
	 * Override of paintComponent:
	 * -Removes all components
	 * -Adds those remaining in the downloads ArrayList
	 * -Calls super.paintComponent();
	 */
	@Override
	public void paintComponent(Graphics g){
		this.removeAll();
		
		for(int i = 0; i < this.downloads.size(); i++) {
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.gridx = 0;
			gbc.gridy = i;
			gbc.anchor = GridBagConstraints.BELOW_BASELINE_LEADING;
			add(downloads.get(i), gbc);
		}
		
        super.paintComponent(g);
    }
	
	/**
	 * Removes a Download instance:
	 * -Removes it from the downloads ArrayList
	 * -Repaints parentFrame
	 * @param d The instance of Download to remove.
	 */
	public void removeDownload(Download d) {
		for(int i = 0; i < this.downloads.size(); i++) {
			if(this.downloads.get(i) == d) {
				this.downloads.remove(i);
				break;
			}
		}
		
		Main.parentFrame.repaint();
	}

}
