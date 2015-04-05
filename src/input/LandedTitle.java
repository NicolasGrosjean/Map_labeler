package input;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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
				if (word.regionMatches(0, "#", 0, 1)) {
					word = scanner.nextLine();
				} else if (word.regionMatches(0, "e_", 0, 2) ||
						word.regionMatches(0, "k_", 0, 2) ||
						word.regionMatches(0, "d_", 0, 2) ||
						word.regionMatches(0, "c_", 0, 2)) {
					// Searching its color
					while (scanner.hasNext()) {
						String color = scanner.next();
						// Skipping comment
						if (color.regionMatches(0, "#", 0, 1)) {
							color = scanner.nextLine();
						} else if (color.regionMatches(0, "color", 0, 5)){
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
							while (!scanner.hasNextInt() && scanner.hasNext()) {
								scanner.next();
							}
							int b = scanner.nextInt();

							// Storing state code with rgb code
							stateCode.put((r << 16)	+ (g << 8) + b, word);
							break;
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
}
