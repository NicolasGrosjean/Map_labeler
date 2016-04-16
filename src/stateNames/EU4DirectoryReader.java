package stateNames;

import java.io.File;
import java.util.PriorityQueue;

import Text.AbstractText;

public class EU4DirectoryReader {
	/**
	 * Test the directories are valid.
	 * Read the files names by directory and set them in the ProrityQueues
	 * 
	 * @param directoryName Main directory name (game or mod)
	 * @param text
	 * @param countryFiles OUTPUT : priority queue of country files
	 * @param tagFiles OUTPUT : priority queue of tag files
	 */
	public static void readAndSortDirectoryFiles(String directoryName, AbstractText text,
			PriorityQueue<File> countryFiles, PriorityQueue<File> tagFiles) {
		if (directoryName != null) {
			File mainDirectory = new File(directoryName);
			if (!mainDirectory.isDirectory()) {
				throw new IllegalArgumentException(text.invalidDirectoryName(directoryName));
			}
		} else {
			throw new IllegalArgumentException(text.missingDirectoryName());
		}
		File countryDirectory = new File(directoryName + "/common/countries");
		if (!countryDirectory.isDirectory()) {
			throw new IllegalArgumentException(text.invalidDirectoryName(directoryName + "/common/countries"));
		}
		for (File f : countryDirectory.listFiles()) {
			if (f.isFile()) {
				countryFiles.offer(f);
			}
		}
		File countryTagDirectory = new File(directoryName + "/common/country_tags");
		if (!countryTagDirectory.isDirectory()) {
			throw new IllegalArgumentException(text.invalidDirectoryName(directoryName + "/common/country_tags"));
		}
		for (File f : countryTagDirectory.listFiles()) {
			if (f.isFile()) {
				tagFiles.offer(f);
			}
		}
	}
}
