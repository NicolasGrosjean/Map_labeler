package input;

import java.io.File;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;

import Text.AbstractText;
import Text.TextEnglish;
import Text.TextFrancais;

/**
 * Management of the main arguments
 * @author Mouchi
 *
 */
public class MainArguments {
	// Language
	private AbstractText text = null;
	// Files
	private String mapFileName = null;
	private String outFileName = null;
	private String imageFileName = null;
	private PriorityQueue<File> landedTitlesFiles;
	private PriorityQueue<File> localisationFiles;
	private LinkedList<String> landedTitlesFilesNames = new LinkedList<String>();
	private LinkedList<String> localisationFilesNames = new LinkedList<String>();
	private String gameDirectory = null;
	private LinkedList<String> modDirectories = new LinkedList<String>();
	// Other parameters
	private String fontName = null;
	private int nbState = 0;
	private boolean allStates = false;
	private int maxTextSize = Integer.MAX_VALUE;
	private boolean proportional = true; // if true -> proportional to map size
	/* States text color (if true black and white -> harmonized
	 * else it is the opposite color of the state -> multicolor) */
	private boolean harmonize = true;
	// Position of the date on the map
	private boolean leftDate = true;
	// Formatage de la date
	private boolean yearOnly = false;
	private boolean textualDate = false;

	public MainArguments() {}

	public MainArguments(String[] args) {

		int i = 0;
		while (i < args.length) {
			switch (args[i]) {
			case "-fr" :
				// French language asked
				text = new TextFrancais();
				break;
			case "-en" :
				// English language asked
				text = new TextEnglish();
				break;
			case "-map" :
				i++;
				if (i < args.length) {
					mapFileName = args[i];
				}
				break;
			case "-out" :
				i++;
				if (i < args.length) {
					outFileName = args[i];
				}
				break;
			case "-img" :
				i++;
				if (i < args.length) {
					imageFileName = args[i];
				}
				break;
			case "-mod" :
				i++;
				while (i < args.length && args[i].charAt(0) != '-') {
					modDirectories.addLast(args[i]);
					i++;
				}
				i--;
				break;
			case "-game" :
				i++;
				if (i < args.length) {
					gameDirectory = args[i];
				}
				break;
			case "-pol" :
				i++;
				if (i < args.length) {
					fontName = args[i];
				}
				break;
			case "-sta" :
				i++;
				if (i < args.length) {
					String sNbState = args[i];
					if (sNbState.charAt(0) == 'a') {
						allStates = true;
					} else {
						try {
							nbState = Integer.parseInt(sNbState);
						} catch (NumberFormatException e) {
							if (text == null) {
								throw new IllegalArgumentException("ERROR : language not specified");
							}
							throw new IllegalArgumentException(text.invalidStateNumber());
						}
					}
				}
				break;
			case "-size" :
				i++;
				if (i < args.length) {
					// The max text size is absolute
					try {
						maxTextSize = Integer.parseInt(args[i]);
						proportional = false;
					} catch (NumberFormatException e) {
						if (text == null) {
							throw new IllegalArgumentException("ERROR : language not specified");
						}
						throw new IllegalArgumentException(text.invalidMaxTextSize());
					}
				}
				break;
			case "-multicolor" :
				harmonize = false;
				break;
			case "-rightDate" :
				leftDate = false;
				break;
			case "-yearOnly" :
				yearOnly = true;
				break;
			case "-textualDate" :
				textualDate = true;
				break;
			default :
				if (text == null) {
					throw new IllegalArgumentException("ERROR : language not specified");
				}
				throw new IllegalArgumentException(text.wrongArgument(args[i]));
			}
			i++;
		}
		// Read the directories and sort the files
		landedTitlesFiles = new PriorityQueue<File>(20, new TimeFileComparator());
		localisationFiles = new PriorityQueue<File>(20, new LexicalFileComparator());
		DirectoryReader.readAndSortDirectoryFiles(gameDirectory, text,
				landedTitlesFiles, localisationFiles);
		while (!modDirectories.isEmpty()) {
			DirectoryReader.readAndSortDirectoryFiles(modDirectories.removeFirst(),
					text, landedTitlesFiles, localisationFiles);
		}
		// Transform Files priority queues list into String list
		while (!landedTitlesFiles.isEmpty()) {
			landedTitlesFilesNames.addLast(landedTitlesFiles.remove().toString());
		}
		while (!localisationFiles.isEmpty()) {
			localisationFilesNames.addLast(localisationFiles.remove().toString());
		}
		// Check the needed parameters are here and correct
		checkArgs();
	}

	private void checkArgs() {
		if (text == null) {
			throw new IllegalArgumentException("ERROR : language not specified");
		}
		if (mapFileName == null) {
			throw new IllegalArgumentException(text.missingMapFile());
		}
		if (outFileName == null) {
			throw new IllegalArgumentException(text.missingOutMapFile());
		}
		if (imageFileName == null) {
			throw new IllegalArgumentException(text.missingWaitingImageFile());
		}
		if (landedTitlesFilesNames == null) {
			throw new IllegalArgumentException(text.missingLandedTitleFile());
		}
		if (localisationFilesNames.isEmpty()) {
			throw new IllegalArgumentException(text.missingLocalisationFiles());
		}
		if (fontName == null) {
			throw new IllegalArgumentException(text.missingFontName());
		}
		if (!allStates && nbState == 0) {
			throw new IllegalArgumentException(text.missingStateNumber());
		} else if (!allStates && nbState < 0) {
			throw new IllegalArgumentException(text.invalidStateNumber());
		}
		if (maxTextSize < 20) {
			throw new IllegalArgumentException(text.invalidMaxTextSize());
		}
	}

	public AbstractText getText() {
		return text;
	}

	public String getMapFileName() {
		return mapFileName;
	}

	public String getOutFileName() {
		return outFileName;
	}

	public String getImageFileName() {
		return imageFileName;
	}

	public LinkedList<String> getLandedTitlesFileNames() {
		return landedTitlesFilesNames;
	}

	public LinkedList<String> getLocalisationFilesNames() {
		return localisationFilesNames;
	}

	public String getFontName() {
		return fontName;
	}

	public int getNbState() {
		return nbState;
	}

	public boolean isAllStates() {
		return allStates;
	}

	public int getMaxTextSize() {
		return maxTextSize;
	}

	public boolean isProportional() {
		return proportional;
	}

	public boolean isHarmonize() {
		return harmonize;
	}

	public boolean isLeftDate() {
		return leftDate;
	}

	public boolean isYearOnly() {
		return yearOnly;
	}

	public boolean isTextualDate() {
		return textualDate;
	}

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
