package stateNames;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

import Text.AbstractText;

/**
 * Override the names found with the game files
 * @author Nicolas
 *
 */
public class OverrideNames {
	/**
	 * Map a RGB code with a state name
	 */
	private Map<Integer, String> overrideNames;
	
	public OverrideNames(String overrideFileName, AbstractText text) {
		overrideNames = new HashMap<Integer, String>();
		
		boolean error = false;
		FileInputStream file = null;
		try {
			file = new FileInputStream(overrideFileName);
			Scanner line = new Scanner(file, "ISO-8859-1");
			line.useDelimiter(Pattern.compile("[\r\n]"));
			while (line.hasNext()) {
				String sLine = line.next();
				if (sLine.split(";").length > 3) {
					int r = Integer.parseInt(sLine.split(";")[0]);
					int g = Integer.parseInt(sLine.split(";")[1]);
					int b = Integer.parseInt(sLine.split(";")[2]);
					overrideNames.put((r << 16) + (g << 8) + b, sLine.split(";")[3]);
				}
			}
			line.close();
		} catch (FileNotFoundException e) {
			error = true;
		} finally {
			try {
				if (file != null)
					file.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (error) {
				throw new IllegalArgumentException(
						text.fileNotFound(overrideFileName));
			}
		}
	}

	public boolean contains(int rgb) {
		return overrideNames.containsKey(rgb);
	}

	public boolean contains(int r, int g, int b) {
		return contains((r << 16) + (g << 8) + b);
	}

	public String get(int rgb) {
		return overrideNames.get(rgb);
	}

	public String get(int r, int g, int b) {
		return get((r << 16) + (g << 8) + b);
	}
}
