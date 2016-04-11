package stateNames;

import java.io.File;
import java.util.LinkedList;
import java.util.PriorityQueue;

import Text.AbstractText;

public class EU4Files extends GameFiles {
	private EU4Countries countries;

	public EU4Files(String gameDirectory, LinkedList<String> modDirectories,
			AbstractText text) {
		// Read the directories and sort the files
		PriorityQueue<File> countryFiles = new PriorityQueue<File>(20, new TimeFileComparator());
		EU4DirectoryReader.readAndSortDirectoryFiles(gameDirectory, text,
				countryFiles);
		while (!modDirectories.isEmpty()) {
			EU4DirectoryReader.readAndSortDirectoryFiles(modDirectories.removeFirst(),
					text, countryFiles);
		}
		
		// Transform Files priority queues list into String list
		LinkedList<String> countryFileNames = new LinkedList<String>();
		while (!countryFiles.isEmpty()) {
			countryFileNames.addLast(countryFiles.remove().toString());
		}
		
		// Check that all is good
		if (countryFileNames.isEmpty()) {
			throw new IllegalArgumentException(text.missingCountryFiles());
		}
		
		// Final storage of the useful information
		countries = new EU4Countries(countryFileNames);
	}

	@Override
	public String getStateName(int stateRGB) {
		return countries.getStateName(stateRGB);
	}

}
