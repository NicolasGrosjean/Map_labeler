package input;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

public class LandedTitle {
	private Map<Integer, String> stateCode;

	public LandedTitle(LinkedList<String> landedTitlesFileNames) {
		this.stateCode = new HashMap<Integer, String>();
		while (!landedTitlesFileNames.isEmpty()) {
			init(landedTitlesFileNames.removeFirst());
		}
	}

	private void init(String landedTitleFileName) {
		FileInputStream fichierLecture = null;
		try {
			fichierLecture = new FileInputStream(landedTitleFileName); 
			Scanner scanner=new Scanner(fichierLecture, "ISO-8859-1");
			scanner.useDelimiter(Pattern.compile("[ \n\t]"));
			// Searching empire, kingdoms, duchies and counties
			while (scanner.hasNext()) {
				String word = scanner.next();
				// Skipping comment
				if (word.contains("#")) {
					word = scanner.nextLine();
				} else if (word.regionMatches(0, "e_", 0, 2) ||
						word.regionMatches(0, "k_", 0, 2) ||
						word.regionMatches(0, "d_", 0, 2) ||
						word.regionMatches(0, "c_", 0, 2)) {
					// If it is not the beginning of a block ("= {") we must search again
					String afterWord;
					if (word.contains("=")) {
						afterWord = word;
						if (afterWord.length() > 1 && !afterWord.contains("{")) {
							while (afterWord.length() == 0 && scanner.hasNext()) {
								afterWord = scanner.next();
							}
							if (afterWord.charAt(0) != '{') {
								continue; // It is not a title definition
							}
						}
					} else if (scanner.hasNext()) {
						afterWord = scanner.next();
						while (afterWord.length() == 0 && scanner.hasNext()) {
							afterWord = scanner.next();
						}
						if (afterWord.charAt(0) != '=') {
							continue; // It is not a title definition
						}
						if (afterWord.length() > 1 && afterWord.charAt(1) != '{') {
							while (afterWord.length() == 0 && scanner.hasNext()) {
								afterWord = scanner.next();
							}
							if (afterWord.charAt(0) != '{') {
								continue; // It is not a title definition
							}
						}
					}
					// Counting number of blocks in which we are from this state
					// Because the States which open blocks doesn't necessary declare a color
					int nbBlock = 1;
					// Words finishing by line.separator are not that we need
					// A better thing is to see if the words is followed by "= {"
					// Searching its color
					while (scanner.hasNext()) {
						String color = scanner.next();
//						System.out.println(nbBlock +" : " + color);
						if (nbBlock == 0) {
							// We are not yet in the state
							break;
						}
						// Skipping comment
						if (color.contains("#")) {
							color = scanner.nextLine();
						} else if (color.regionMatches(0, "color", 0, 5)){
//							System.out.println("COLOR : " + color);
							// Color found
							// Searching integer (r code)
							while (!scanner.hasNextInt() && scanner.hasNext()) {
								scanner.next();
							}
							int r = scanner.nextInt();
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
								if (s.length() > 0 && s.charAt(s.length() - 2) == '}') {
									b = Integer.parseInt(s.substring(0, s.length() - 2));
								}
							}
							if (b == -1) {
								b = scanner.nextInt();
							}
							// Remove all characters after '=' in word if necessary
							if (word.contains("=")) {
								word = word.substring(0, word.indexOf("="));
							}
							// Storing state code with rgb code
							stateCode.put((r << 16)	+ (g << 8) + b, word);
							break;
						} else if (color.contains("{")) {
							 // Enter in a new block eventually except color
							// It is unimportant when we found a color
							nbBlock++;
						} else if (color.contains("}")) {
							nbBlock--; // Leave a block
						}
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

	public String getStateCode(int rgb) {
		return stateCode.get(rgb & 0xffffff);
	}

	/**
	 * USEFULL ONLY FOR TESTING
	 * List of States which are in double (or more) in the result of parsing
	 * @return
	 */
	public LinkedList<String> getDouble() {
		Collection<String> states = stateCode.values();
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
