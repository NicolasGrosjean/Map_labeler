package stateNames;

import java.io.File;
import java.util.LinkedList;
import java.util.PriorityQueue;

import Text.AbstractText;

public class Ck2Files extends GameFiles {
	private Ck2LandedTitle landedTitles;
	private Ck2Localisation localisation;
	
	
	public Ck2Files(String gameDirectory, LinkedList<String> modDirectories,
			AbstractText text) {
		// Read the directories and sort the files
		PriorityQueue<File> landedTitlesFiles = new PriorityQueue<File>(20, new TimeFileComparator());
		PriorityQueue<File> localisationFiles = new PriorityQueue<File>(20, new LexicalFileComparator());
		Ck2DirectoryReader.readAndSortDirectoryFiles(gameDirectory, text,
				landedTitlesFiles, localisationFiles);
		while (!modDirectories.isEmpty()) {
			Ck2DirectoryReader.readAndSortDirectoryFiles(modDirectories.removeFirst(),
					text, landedTitlesFiles, localisationFiles);
		}
		
		// Transform Files priority queues list into String list
		LinkedList<String> landedTitlesFilesNames = new LinkedList<String>();
		LinkedList<String> localisationFilesNames = new LinkedList<String>();
		while (!landedTitlesFiles.isEmpty()) {
			landedTitlesFilesNames.addLast(landedTitlesFiles.remove().toString());
		}
		while (!localisationFiles.isEmpty()) {
			localisationFilesNames.addFirst(localisationFiles.remove().toString());
		}
		
		// Check that all is good
		if (landedTitlesFilesNames.isEmpty()) {
			throw new IllegalArgumentException(text.missingLandedTitleFile());
		}
		if (localisationFilesNames.isEmpty()) {
			throw new IllegalArgumentException(text.missingLocalisationFiles());
		}
		
		// Final storage of the useful information
		landedTitles = new Ck2LandedTitle(landedTitlesFilesNames); 
		localisation = new Ck2Localisation(localisationFilesNames, text);
	}

	@Override
	public String getStateName(int stateRGB) {
		String stateCode = landedTitles.getStateCode(stateRGB);
		if (stateCode == null) {
			return null; // State not found
		}
		// Search state name
		String stateName = localisation.getStateName(stateCode);
		
		String res;
		if (stateName != null) {
			res = stateName;
		} else {
			/* If not found in the localization files, use the name
			 * of the title code with upper case letter */
			String maj = stateCode.substring(2, 3).toUpperCase();						
			res = maj;
			int i = 3;
			int j = 3;
			while (i < stateCode.length() - 1) {
				while (i < stateCode.length() - 1 && stateCode.charAt(i) != '_') {
					i++;
				}
				// Copy of the text until this end or '_'
				if (stateCode.charAt(i) != '_') {
					res = res + stateCode.substring(j, i + 1);
				} else {
					res = res + stateCode.substring(j, i);
				}
				i++;
				j = i + 1;
				if (i < stateCode.length() - 1) {
					// '_' found
					// The next letter is in upper case and '_' replace by ' '
					maj = stateCode.substring(i, i + 1).toUpperCase();
					res = res + " " + maj;
				}
			}
		}
		return res;
	}

}
