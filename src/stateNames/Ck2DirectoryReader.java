package stateNames;

import java.io.File;
import java.util.PriorityQueue;

import Text.AbstractText;

public class Ck2DirectoryReader {

	public static void readAndSortDirectoryFiles(String directoryName, AbstractText text,
			PriorityQueue<File> landedTitlesFiles,
			PriorityQueue<File> localisationFiles) {
		if (directoryName != null) {
			File mainDirectory = new File(directoryName);
			if (!mainDirectory.isDirectory()) {
				throw new IllegalArgumentException(text.invalidDirectoryName(directoryName));
			}
		} else {
			throw new IllegalArgumentException(text.missingDirectoryName());
		}
		File landedTitleDirectory = new File(directoryName + "/common/landed_titles");
		if (!landedTitleDirectory.isDirectory()) {
			throw new IllegalArgumentException(text.invalidDirectoryName(directoryName + "/common/landed_titles"));
		}
		for (File f : landedTitleDirectory.listFiles()) {
			if (f.isFile()) {
				landedTitlesFiles.offer(f);
			}
		}
		File localisationDirectory = new File(directoryName + "/localisation");
		if (!localisationDirectory.isDirectory()) {
			throw new IllegalArgumentException(text.invalidDirectoryName(directoryName + "/localisation"));
		}
		for (File f : localisationDirectory.listFiles()) {
			if (f.isFile()) {
				localisationFiles.offer(f);
			}
		}
	}
}
