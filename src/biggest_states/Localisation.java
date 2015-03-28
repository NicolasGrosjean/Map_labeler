package biggest_states;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Localisation {
	private Map<String, String> stateName;

	public Localisation() {
		stateName = new HashMap<String, String>();
		init("text1.csv");
		init("text2.csv");
		init("v1_06.csv");
		init("India.csv");
		init("DuchiesKingdomsandEmpires de jure.csv");
		init("DuchiesKingdomsandEmpires titular.csv");
	}

	private void init(String nomFichierLecture) {
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

	public String getStateName(String stateCode) {
		return stateName.get(stateCode);
	}
}
