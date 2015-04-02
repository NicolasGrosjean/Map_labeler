package biggest_states;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

import Text.AbstractText;

public class Localisation {
	private Map<String, String> stateName;
	private AbstractText text;

	public Localisation(LinkedList<String> localisationFiles, AbstractText text) {
		this.text = text;
		stateName = new HashMap<String, String>();
		while (!localisationFiles.isEmpty()) {
			init(localisationFiles.removeFirst());
		}
	}

	private void init(String nomFichierLecture) {
		boolean error = false;

		FileInputStream fichierLecture = null;
		try {
			fichierLecture = new FileInputStream(nomFichierLecture);
			Scanner scanner=new Scanner(fichierLecture, "ISO-8859-1");
			scanner.useDelimiter(Pattern.compile("[;\n]"));
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
					stateName.put(word, scanner.next());
					// French localisation erase English localisation if it exists
					String frenchLocalisation = scanner.next();
					if (!frenchLocalisation.equals("")) {
						stateName.put(word, frenchLocalisation);
					}
				}
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			error = true;
		} finally {
			try {
				if (fichierLecture != null)
					fichierLecture.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (error) {
				throw new IllegalArgumentException(
						text.fileNotFound(nomFichierLecture));
			}
		}
	}

	public String getStateName(String stateCode) {
		return stateName.get(stateCode);
	}
}
