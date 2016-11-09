package stateNames;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

//import org.yaml.snakeyaml.Yaml;

import Text.AbstractText;

/**
 * Parse the EUIV localisation files to map the tag to the game text
 * @author Nicolas
 *
 */
public class EU4Localisation {
	private Map<String, String> countryName;

	public EU4Localisation(LinkedList<String> localisationFileNames,
			AbstractText text) {
		this.countryName = new HashMap<String, String>();
		while (!localisationFileNames.isEmpty()) {
			init(localisationFileNames.removeFirst(), text);
		}
	}

	private void init(String localisationFileName, AbstractText text) {
		/** Usage of snakeyaml but doesn't work anymore because Paradox doesn't respect the YAML syntax
		try {
			Yaml yaml = new Yaml();
			InputStream input = new FileInputStream(new File(localisationFileName));
			Map<String, Object> object = (Map<String, Object>) yaml.load(input);
			if (text.isFrench() && object.containsKey("l_french")) {
				countryName.putAll((Map<? extends String, ? extends String>) object.get("l_french"));
			} else if (!text.isFrench() && object.containsKey("l_english")) {
				countryName.putAll((Map<? extends String, ? extends String>) object.get("l_english"));
			}
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} catch (Exception e) {
			System.out.println(e);
			//System.out.println("Error when reading " + localisationFileName);
		}
		**/
		InputStream input = null;
		Scanner line = null;
		try {
			input = new FileInputStream(new File(localisationFileName));
			line = new Scanner(input);
			line.useDelimiter(Pattern.compile("[\r\n]"));
			if (!line.hasNext()) { return; }
			String language = line.next();
			if (text.isFrench() && language.contains("l_french")) {
				parseFile(line);
			} else if (!text.isFrench() && language.contains("l_english")) {
				parseFile(line);
			}
		} catch (FileNotFoundException e) {
			System.out.println(text.fileNotFound(localisationFileName));
		} finally {
			try {
				if (input != null)
					input.close();
				if (line != null)
					line.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public String getStateName(String tag) {
		return countryName.get(tag);
	}

	private void parseFile(Scanner line) {
		while (line.hasNext()) {
			String[] arrLine = line.next().split(":");
			if (arrLine.length > 1) {
				String key = arrLine[0].replace(" ", "");
				// Concatenate the localisation when there is ':' (that's why the YAML structure is not correct)
				String localisation = "";
				if (arrLine[1].length() > 3) {
					localisation = arrLine[1].substring(3);
				} else {
					localisation = arrLine[1];
				}
				for (int i = 2; i < arrLine.length; i++) {
					localisation += arrLine[i];
				}
				countryName.put(key, localisation.replace("\"", ""));
			}				
		}
	}
}
