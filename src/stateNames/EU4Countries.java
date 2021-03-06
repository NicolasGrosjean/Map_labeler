package stateNames;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Parse country files to map the color to an intermediate country name
 * @author NicolasGrosjean
 *
 */
public class EU4Countries {
	private Map<Integer, String> intermediateCountryName;

	public EU4Countries(LinkedList<String> countryFileNames) {
		this.intermediateCountryName = new HashMap<Integer, String>();
		while (!countryFileNames.isEmpty()) {
			init(countryFileNames.removeFirst());
		}
	}

	private void init(String countryFileName) {
		// Name of the country is the filename
		File countryFile = new File(countryFileName);
		String nameAndExtension = countryFile.getName();
		String name = nameAndExtension.split(Pattern.quote("."))[0];
				
		FileInputStream fichierLecture = null;		
		try {
			fichierLecture = new FileInputStream(countryFileName); 
			Scanner scanner=new Scanner(fichierLecture, "ISO-8859-1");
			scanner.useDelimiter(Pattern.compile("[ \n\t]"));
			while (scanner.hasNext()) {
				while (scanner.hasNext()) {
					String color = scanner.next();
					// Skipping comment
					if (color.contains("#") && scanner.hasNextLine()) {
						color = scanner.nextLine();
					} else if (color.regionMatches(0, "color", 0, 5)) {
						// Color found
						// Searching integer (r code)
						int r = -1;
						while (r == -1 && !scanner.hasNextInt() && scanner.hasNext()) {
							String s = scanner.next();
							// If there is non space between '{' and the int
							// So the string must contains { and a int, its length > 1
							if (s.length() > 1 && s.charAt(0) == '{') {
								r = Integer.parseInt(s.substring(1, s.length()));
							}
						}
						// r didn't read yet
						if (r == -1) {
							r = scanner.nextInt();
						}
						// Searching integer (g code)
						while (!scanner.hasNextInt() && scanner.hasNext()) {
							scanner.next();
						}
						int g = scanner.nextInt();
						// Searching integer (b code)
						/* Put b to -1 (impossible value) in order to see
						 * if b is affected in the "while" boucle */
						int b = -1;
						while (b == - 1 && !scanner.hasNextInt() && scanner.hasNext()) {
							String s = scanner.next();
							// If there is non space between the int and '}'
							if (s.length() > 0) {
								if (s.charAt(s.length() - 2) == '}') {
									b = Integer.parseInt(s.substring(0, s.length() - 2));
								} else if (s.charAt(s.length() - 1) == '}') {
									b = Integer.parseInt(s.substring(0, s.length() - 1));
								}
							}
						}
						// b didn't read yet
						if (b == -1) {
							b = scanner.nextInt();
						}
						// Storing state name with rgb code
						intermediateCountryName.put((r << 16)	+ (g << 8) + b, name);
						break;
					}
				}
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} finally {
			try {
				if (fichierLecture != null)
					fichierLecture.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Get the intermediate country name associated to a RGB code
	 * @param rgb
	 * @return
	 */
	public String getIntermediateCountryName(int rgb) {
		return intermediateCountryName.get(rgb & 0xffffff);
	}

	/**
	 * USEFULL ONLY FOR TESTING
	 * List of States which are in double (or more) in the result of parsing
	 * @return
	 */
	public LinkedList<String> getDouble() {
		Collection<String> states = intermediateCountryName.values();
		LinkedList<String> seenStates = new LinkedList<String>();
		LinkedList<String> doubleStates = new LinkedList<String>();
		for (String s : states) {
			if (seenStates.contains(s)) {
				doubleStates.add(s);
			}
			seenStates.add(s);
		}
		return doubleStates;
	}
}
