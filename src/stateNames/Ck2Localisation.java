package stateNames;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

import Text.AbstractText;

public class Ck2Localisation {
	private static final int ENGLISH_COLUMN = 1;
	private static final int FRENCH_COLUMN = 2;

	private Map<String, String> stateName;
	private AbstractText text;

	public Ck2Localisation(LinkedList<String> localisationFiles, AbstractText text) {
		this.text = text;
		stateName = new HashMap<String, String>();
		while (!localisationFiles.isEmpty()) {
			init(localisationFiles.removeFirst(), text.isFrench());
		}
	}

	private void init(String nomFichierLecture, boolean french) {
		boolean error = false;

		FileInputStream fichierLecture = null;
		try {
			fichierLecture = new FileInputStream(nomFichierLecture);
			Scanner line = new Scanner(fichierLecture, "ISO-8859-1");
			line.useDelimiter(Pattern.compile("[\r\n]"));
			// Searching empire, kingdoms, duchies and counties
			while (line.hasNext()) {
				String sLine = line.next();
				if (sLine.split(";").length > 1) {
					String localisationKey = sLine.split(";")[0];
					// Skipping comment
					if (localisationKey.regionMatches(0, "#", 0, 1)) {
						if (line.hasNext()) {
							sLine = line.next();
						} else {
							break;
						}
					} else if (localisationKey.regionMatches(0, "e_", 0, 2) ||
							localisationKey.regionMatches(0, "k_", 0, 2) ||
							localisationKey.regionMatches(0, "d_", 0, 2) ||
							localisationKey.regionMatches(0, "c_", 0, 2)) {
						stateName.put(localisationKey, sLine.split(";")[ENGLISH_COLUMN]);
						if (french && (sLine.split(";").length > 2)) {
							// French localisation erase English localisation if it exists
							String frenchLocalisation = sLine.split(";")[FRENCH_COLUMN];
							if (!frenchLocalisation.equals("")) {
								stateName.put(localisationKey, frenchLocalisation);
							}
						}
					}
				}
			}
			line.close();
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
