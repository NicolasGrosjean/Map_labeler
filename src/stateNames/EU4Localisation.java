package stateNames;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

/**
 * Parse the EUIV localisation files to map the tag to the game text
 * @author Nicolas
 *
 */
public class EU4Localisation {
	private Map<String, String> countryName;

	public EU4Localisation(LinkedList<String> localisationFileNames) {
		this.countryName = new HashMap<String, String>();
		while (!localisationFileNames.isEmpty()) {
			init(localisationFileNames.removeFirst());
		}
	}

	private void init(String localisationFileName) {
		try {
			Yaml yaml = new Yaml();
			InputStream input = new FileInputStream(new File(localisationFileName));
			Map<String, Object> object = (Map<String, Object>) yaml.load(input);
			if (object.containsKey("l_french")) {
				countryName.putAll((Map<? extends String, ? extends String>) object.get("l_french"));
			}
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		} catch (Exception e) {
			System.out.println("Error when reading " + localisationFileName);
		}
	}

	public String getStateName(String tag) {
		return countryName.get(tag);
	}
}
