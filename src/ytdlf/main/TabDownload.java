package ytdlf.main;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.Icon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

/**
 * A tab containing a DownloadManager and a JTextField to input new URLs.
 * @author David Boles
 *
 */
public class TabDownload implements Tab {
	JPanel panelDownload;
	JTextField url;
	JSeparator separator;
	DownloadManager dM;
	JScrollPane scrollPane;
	JPanel panel;

	@Override
	public String getTitle() {
		return "Download";
	}

	@Override
	public Icon getIcon() {
		return null;
	}

	/**
	 * Initializes the JPanel and returns it.
	 * @wbp.parser.entryPoint
	 */
	@Override
	public Component getComponent() {
		panelDownload = new JPanel();
		GridBagLayout gbl_panelDownload = new GridBagLayout();
		gbl_panelDownload.columnWidths = new int[]{0};
		gbl_panelDownload.rowHeights = new int[]{0, 0, 0};
		gbl_panelDownload.columnWeights = new double[]{1.0};
		gbl_panelDownload.rowWeights = new double[]{0.0, 0.0, 1.0};
		panelDownload.setLayout(gbl_panelDownload);
		
		this.url = new JTextField();
		this.url.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new File(Main.tS.dF.getText()).mkdirs();
				String cmdStart = Main.tS.generateCommandStart();
				if(cmdStart != null) {
					dM.addDownload(cmdStart+url.getText());
					url.setText("");
				}else {
					Main.logger.log("Download stating failed due to generateCommandStart() returning null.");
				}
			}
		});
		this.url.setFont(new Font("DejaVu Sans", Font.PLAIN, 14));
		GridBagConstraints gbc_url = new GridBagConstraints();
		gbc_url.fill = GridBagConstraints.HORIZONTAL;
		gbc_url.gridx = 0;
		gbc_url.gridy = 0;
		panelDownload.add(this.url, gbc_url);
		this.url.setColumns(10);
		
		this.separator = new JSeparator();
		GridBagConstraints gbc_separator = new GridBagConstraints();
		gbc_separator.gridx = 0;
		gbc_separator.gridy = 1;
		panelDownload.add(this.separator, gbc_separator);
		
		this.scrollPane = new JScrollPane();
		this.scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 2;
		panelDownload.add(this.scrollPane, gbc_scrollPane);
		
		this.dM = new DownloadManager();
		this.scrollPane.setViewportView(this.dM);
		
		return panelDownload;
	}

	@Override
	public String getTip() {
		return "Download the things!";
	}

	

}
