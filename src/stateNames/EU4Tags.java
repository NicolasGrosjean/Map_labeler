package stateNames;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

public class EU4Tags {
	private Map<String, String> countryTags;
	
	public EU4Tags(LinkedList<String> tagFileNames) {
		this.countryTags = new HashMap<String, String>();
		while (!tagFileNames.isEmpty()) {
			init(tagFileNames.removeFirst());
		}
	}
	
	private void init(String tagFileName) {
		FileInputStream fichierLecture = null;		
		try {
			fichierLecture = new FileInputStream(tagFileName); 
			Scanner scanner=new Scanner(fichierLecture, "ISO-8859-1");
			scanner.useDelimiter(Pattern.compile("[ =\n\t]"));
			while (scanner.hasNext()) {
				while (scanner.hasNext()) {
					String word = scanner.next();
					// Skipping comment
					if (word.contains("#")) {
						word = scanner.nextLine();
					} else if (word.length() == 3) {
						// Tag found
						String tag = word;
						String fileName = "";
						String stateName = null;
						while (fileName.equals("") &&  scanner.hasNext()) {
							fileName = scanner.next();
							if (fileName.length() > 0 && fileName.charAt(0) == '"') {
								stateName = fileName.split(Pattern.quote("/"))[1].split(Pattern.quote("."))[0];
							}
						}	
						if (stateName != null) {
							// Storing tag with the intermediate state name
							countryTags.put(stateName, tag);
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

	/**
	 * Get the tag associated to a intermediate country name
	 * @param intermediateCountryName
	 * @return
	 */
	public String getTag(String intermediateCountryName) {
		return countryTags.get(intermediateCountryName);
	}
}
