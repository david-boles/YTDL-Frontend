package ytdlf.main;

import java.awt.Rectangle;
import java.util.ArrayList;

import com.deb.lib.program.ProgramFs;

/**
 * Holds and initializes an ArrayList of the default settings.
 * @author David Boles
 *
 */
public class Settings {
	
	/**
	 * Constructs a new ArrayList with the default settings.
	 * @return An ArrayList of the default settings.
	 */
	public static ArrayList<Object> getInitial() {
		ArrayList<Object> i = new ArrayList<Object>();
		
		//0 - Downloads File
		i.add(ProgramFs.getProgramFile("Downloads"));
		//1 - Username
		i.add("");
		//2 - Password
		i.add("");
		//3 - U/ P Enabled
		i.add(false);
		//4 - Script string
		i.add("youtube-dl");
		//5 - Options string
		i.add("");
		//6 - Options Enabled
		i.add(false);
		//7 - Parent Frame representative Rectangle
		i.add(new Rectangle(100, 100, 600, 300));
		//8 - Logging enabled
		i.add(true);
		
		return i;
	}
}
