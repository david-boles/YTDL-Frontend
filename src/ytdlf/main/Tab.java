package ytdlf.main;

import java.awt.Component;

import javax.swing.Icon;

/**
 * Interface to more easily construct and add new tabs to the parentPane.
 * @author David Boles
 *
 */
public interface Tab {
	public String getTitle();
	public Icon getIcon();
	public Component getComponent();
	public String getTip();
}
