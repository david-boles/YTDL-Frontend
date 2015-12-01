package ytdlf.main;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * Tab containing all the settings
 * @author David Boles
 *
 */
public class TabSettings implements Tab {
	JPanel panelSettings;
	JTextField dF;
	JButton dFS;
	JTextField username;
	JTextField password;
	JCheckBox u_pE;
	JTextField script;
	JTextField options;
	JCheckBox optionsE;
	JButton scriptR;
	private JCheckBox logE;

	@Override
	public String getTitle() {
		return "Settings";
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
		panelSettings = new JPanel();
		GridBagLayout gridBagLayout = new GridBagLayout();
		panelSettings.setLayout(gridBagLayout);
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 0.0};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, 0.0};
		
		this.dF = new JTextField();
		this.dF.setEditable(false);
		this.dF.setText(((File)Main.sm.getSetting(0)).getAbsolutePath());
		this.dF.setFont(new Font("DejaVu Sans", Font.PLAIN, 14));
		GridBagConstraints gbc_dF = new GridBagConstraints();
		gbc_dF.insets = new Insets(0, 0, 5, 5);
		gbc_dF.gridwidth = 2;
		gbc_dF.fill = GridBagConstraints.HORIZONTAL;
		gbc_dF.gridx = 0;
		gbc_dF.gridy = 0;
		panelSettings.add(this.dF, gbc_dF);
		
		this.dFS = new JButton("Choose Folder");
		this.dFS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				fc.showSaveDialog(null);
				File f = fc.getSelectedFile();
				dF.setText(f.getAbsolutePath());
				Main.sm.setSetting(0, f);
			}
		});
		this.dFS.setFont(new Font("DejaVu Sans", Font.BOLD, 14));
		GridBagConstraints gbc_dFS = new GridBagConstraints();
		gbc_dFS.insets = new Insets(0, 0, 5, 0);
		gbc_dFS.gridx = 2;
		gbc_dFS.gridy = 0;
		panelSettings.add(this.dFS, gbc_dFS);
		
		this.username = new JTextField();
		Main.addJTFCL(this.username, new Runnable() {
			@Override
			public void run() {
				Main.sm.setSetting(1, username.getText());
			}
		});
		this.username.setText(Main.sm.getSetting(1));
		this.username.setEnabled(Main.sm.getSetting(3));
		this.username.setToolTipText("Username");
		this.username.setFont(new Font("DejaVu Sans", Font.PLAIN, 14));
		GridBagConstraints gbc_username = new GridBagConstraints();
		gbc_username.insets = new Insets(0, 0, 5, 5);
		gbc_username.fill = GridBagConstraints.HORIZONTAL;
		gbc_username.gridx = 0;
		gbc_username.gridy = 1;
		panelSettings.add(this.username, gbc_username);
		this.username.setColumns(10);
		
		this.password = new JTextField();
		Main.addJTFCL(this.password, new Runnable() {
			@Override
			public void run() {
				Main.sm.setSetting(2, password.getText());
			}
		});
		this.password.setText(Main.sm.getSetting(2));
		this.password.setEnabled(Main.sm.getSetting(3));
		this.password.setToolTipText("Password");
		this.password.setFont(new Font("DejaVu Sans", Font.PLAIN, 14));
		GridBagConstraints gbc_password = new GridBagConstraints();
		gbc_password.insets = new Insets(0, 0, 5, 5);
		gbc_password.fill = GridBagConstraints.HORIZONTAL;
		gbc_password.gridx = 1;
		gbc_password.gridy = 1;
		panelSettings.add(this.password, gbc_password);
		this.password.setColumns(10);
		
		this.u_pE = new JCheckBox("Username/ Password");
		this.u_pE.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(u_pE.isSelected())JOptionPane.showMessageDialog(null, "Yikes! Filling in these fields will save them in plaintext. For additional security, disable logging and leave the field blank. (you will be prompted for the information when you start a download)", "Potentially Unsecure Option Enabled", 0, null);//NOTE: Change text
				username.setEnabled(u_pE.isSelected());
				password.setEnabled(u_pE.isSelected());
				Main.sm.setSetting(3, u_pE.isSelected());
			}
		});
		this.u_pE.setSelected(Main.sm.getSetting(3));
		this.u_pE.setFont(new Font("DejaVu Sans", Font.BOLD, 14));
		GridBagConstraints gbc_u_pE = new GridBagConstraints();
		gbc_u_pE.insets = new Insets(0, 0, 5, 0);
		gbc_u_pE.gridx = 2;
		gbc_u_pE.gridy = 1;
		panelSettings.add(this.u_pE, gbc_u_pE);
		
		if(u_pE.isSelected())JOptionPane.showMessageDialog(null, "Yikes, Username/ Password is enabled! If your information is typed into the settings tab or logging is enabled it will be saved as plain text. You can leave the field blank to be prompted each time.", "Potentially Unsecure Option Enabled", 0, null);
		
		this.script = new JTextField();
		Main.addJTFCL(this.script, new Runnable() {
			@Override
			public void run() {
				Main.sm.setSetting(4, script.getText());
			}
		});
		this.script.setText(Main.sm.getSetting(4));
		this.script.setFont(new Font("DejaVu Sans", Font.PLAIN, 14));
		GridBagConstraints gbc_script = new GridBagConstraints();
		gbc_script.insets = new Insets(0, 0, 5, 5);
		gbc_script.gridwidth = 2;
		gbc_script.fill = GridBagConstraints.HORIZONTAL;
		gbc_script.gridx = 0;
		gbc_script.gridy = 2;
		panelSettings.add(this.script, gbc_script);
		this.script.setColumns(10);
		
		this.scriptR = new JButton("Revert Script");
		this.scriptR.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				script.setText("youtube-dl");
				Main.sm.setSetting(4, script.getText());
			}
		});
		this.scriptR.setFont(new Font("DejaVu Sans", Font.BOLD, 14));
		GridBagConstraints gbc_scriptR = new GridBagConstraints();
		gbc_scriptR.insets = new Insets(0, 0, 5, 0);
		gbc_scriptR.gridx = 2;
		gbc_scriptR.gridy = 2;
		panelSettings.add(this.scriptR, gbc_scriptR);
		//panelSettings.add(this.scriptS, gbc_scriptS);
		
		this.options = new JTextField();
		Main.addJTFCL(this.options, new Runnable() {
			@Override
			public void run() {
				Main.sm.setSetting(5, options.getText());
			}
		});
		this.options.setText(Main.sm.getSetting(5));
		this.options.setEnabled(Main.sm.getSetting(6));
		this.options.setFont(new Font("DejaVu Sans", Font.PLAIN, 14));
		GridBagConstraints gbc_options = new GridBagConstraints();
		gbc_options.insets = new Insets(0, 0, 5, 5);
		gbc_options.gridwidth = 2;
		gbc_options.fill = GridBagConstraints.HORIZONTAL;
		gbc_options.gridx = 0;
		gbc_options.gridy = 3;
		panelSettings.add(this.options, gbc_options);
		this.options.setColumns(10);
		
		this.optionsE = new JCheckBox("Additional Options");
		this.optionsE.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				options.setEnabled(optionsE.isSelected());
				Main.sm.setSetting(6, optionsE.isSelected());
			}
		});
		this.optionsE.setFont(new Font("DejaVu Sans", Font.BOLD, 14));
		this.optionsE.setSelected(Main.sm.getSetting(6));
		GridBagConstraints gbc_optionsE = new GridBagConstraints();
		gbc_optionsE.insets = new Insets(0, 0, 5, 0);
		gbc_optionsE.gridx = 2;
		gbc_optionsE.gridy = 3;
		panelSettings.add(this.optionsE, gbc_optionsE);
		
		logE = new JCheckBox("Logging Enabled");
		logE.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Main.sm.setSetting(8, logE.isSelected());
				Main.logger.setEnabled(logE.isSelected());
			}
		});
		logE.setSelected(Main.sm.getSetting(8));
		this.logE.setFont(new Font("DejaVu Sans", Font.BOLD, 14));
		GridBagConstraints gbc_logE = new GridBagConstraints();
		gbc_logE.gridwidth = 3;
		gbc_logE.insets = new Insets(0, 0, 0, 5);
		gbc_logE.gridx = 0;
		gbc_logE.gridy = 4;
		panelSettings.add(logE, gbc_logE);
		return panelSettings;
	}

	@Override
	public String getTip() {
		return "Configuration of youtube-dl...";
	}
	
	/**
	 * Uses the settings provided to create the beginning of the youtube-dl command. Opens username and password dialogs if they're enabled and blank.
	 * @return The beginning of the command, null if stopped by user.
	 */
	public String generateCommandStart() {
		if(this.dF.getText().contains(" ") || (this.u_pE.isSelected()&&(this.username.getText().contains(" ")||this.password.getText().contains(" ")))) JOptionPane.showMessageDialog(null, "One of the settings contain a space. This download will probably not work...");
		
		//Get password
		
		
		String out = this.script.getText() + " -o " + this.dF.getText() + File.separator + "%(title)s.%(ext)s ";
		if(this.u_pE.isSelected()) {
			String username = this.username.getText();
			String password = this.password.getText();
			
			if(username.equals("")) {
				username = JOptionPane.showInputDialog("Enter your username:");
			}
			
			if(password.equals("")) {//NOTE: Set frame size
				password = this.openPasswordDialog();
			}
			
			if(!username.equals("")) {
				out += "-u " + username + " ";
			}
			
			if(!password.equals("")) {//NOTE: Set frame size
				out += "-p " + password + " ";
			}
		}
		if(this.optionsE.isSelected()) out += this.options.getText() + " ";
		return out;
	}
	
	/**
	 * If Main.logger should output.
	 * @return this.logE.isSelected();
	 */
	public boolean shouldLog() {
		return this.logE.isSelected();
	}
	
	/**
	 * Opens a dialog asking for a password.
	 * @return What is typed in.
	 */
	String openPasswordDialog() {
		Box box = Box.createHorizontalBox();

		JLabel jl = new JLabel("Password: ");
		box.add(jl);

		JPasswordField jpf = new JPasswordField(15);
		//jpf.setEchoChar('*');
		box.add(jpf);

		int button = JOptionPane.showConfirmDialog(null, box, "Enter your password:", JOptionPane.OK_CANCEL_OPTION);

		if (button == JOptionPane.OK_OPTION) {
		    char[] input = jpf.getPassword();
		    return String.copyValueOf(input);
		}
		return "";
	}
	
}
