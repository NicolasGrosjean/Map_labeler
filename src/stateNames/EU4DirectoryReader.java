package stateNames;

import java.io.File;
import java.util.PriorityQueue;

import Text.AbstractText;

public class EU4DirectoryReader {
	public static void readAndSortDirectoryFiles(String directoryName, AbstractText text,
			PriorityQueue<File> countryFiles) {
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
	}
}
