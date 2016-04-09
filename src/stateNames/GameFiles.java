package stateNames;

import java.io.File;
import java.util.Comparator;

public abstract class GameFiles {
	/**
	 * Return the name of the state or null if it is not found
	 * @param stateRGB : RGB code of the state
	 * @return
	 */
	public abstract String getStateName(int stateRGB);

	/**
	 * Order File from the older to the younger
	 *
	 */
	public class TimeFileComparator implements Comparator<File>{
		@Override
		public int compare(File f1, File f2) {
			return (int)(f2.lastModified() - f1.lastModified());
		}
	}

	/**
	 * Order File with the lexical order
	 *
	 */
	public class LexicalFileComparator implements Comparator<File>{
		@Override
		public int compare(File f1, File f2) {
			return f1.getName().compareTo(f2.getName());
		}
	}
}
