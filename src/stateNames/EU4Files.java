package stateNames;

import java.io.File;
import java.util.LinkedList;
import java.util.PriorityQueue;

import Text.AbstractText;

public class EU4Files extends GameFiles {
	/**
	 * Country names for the games that we can match with color and tag
	 */
	private EU4Countries countries;

	/**
	 * Tag countries that we can match with internal country name and localisation
	 */
	private EU4Tags tags;

	/**
	 * Localisation of the tag countries
	 */
	private EU4Localisation localisation;

	public EU4Files(String gameDirectory, LinkedList<String> modDirectories,
			AbstractText text) {
		// Read the directories and sort the files
		PriorityQueue<File> countryFiles = new PriorityQueue<File>(20, new TimeFileComparator());
		PriorityQueue<File> tagFiles = new PriorityQueue<File>(20, new TimeFileComparator());
		PriorityQueue<File> localisationFiles = new PriorityQueue<File>(20, new TimeFileComparator());
		EU4DirectoryReader.readAndSortDirectoryFiles(gameDirectory, text,
				countryFiles, tagFiles, localisationFiles);
		while (!modDirectories.isEmpty()) {
			EU4DirectoryReader.readAndSortDirectoryFiles(modDirectories.removeFirst(),
					text, countryFiles, tagFiles, localisationFiles);
		}
		
		// Transform Files priority queues list into String list
		LinkedList<String> countryFileNames = new LinkedList<String>();
		while (!countryFiles.isEmpty()) {
			countryFileNames.addLast(countryFiles.remove().toString());
		}
		LinkedList<String> tagFileNames = new LinkedList<String>();
		while (!tagFiles.isEmpty()) {
			tagFileNames.addLast(tagFiles.remove().toString());
		}
		LinkedList<String> localisationFileNames = new LinkedList<String>();
		while (!localisationFiles.isEmpty()) {
			localisationFileNames.addLast(localisationFiles.remove().toString());
		}
		
		// Check that all is good
		if (countryFileNames.isEmpty()) {
			throw new IllegalArgumentException(text.missingCountryFiles());
		}
		if (tagFileNames.isEmpty()) {
			throw new IllegalArgumentException(text.missingTagFiles());
		}
		if (localisationFileNames.isEmpty()) {
			throw new IllegalArgumentException(text.missingLocalisationFiles());
		}
		
		// Final storage of the useful information
		countries = new EU4Countries(countryFileNames);
		tags = new EU4Tags(tagFileNames);
		localisation = new EU4Localisation(localisationFileNames);
	}

	@Override
	public String getStateName(int stateRGB) {
		// Internal/intermediate country name
		String countryName = countries.getIntermediateCountryName(stateRGB);
		
		// Tag used internally in game
		String tag = tags.getTag(countryName);
		
		// Localisation displayed in game
		return localisation.getStateName(tag);
	}

}
