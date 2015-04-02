package input;

import java.util.LinkedList;

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
	private String landedTitleFileName = null;
	private LinkedList<String> localisationFiles = new LinkedList<String>();
	// Other parameters
	private String fontName = null;
	private int nbState = 0;
	private int maxTextSize = Integer.MAX_VALUE;
	private boolean proportional = false; // if true -> proportional to map size
	/* States text color (if true black and white -> harmonized
	 * else it is the opposite color of the state -> multicolor) */
	private boolean harmonize = true;
	/* Position of the date on the map */
	private boolean leftDate = true;

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
			case "-land" :
				i++;
				if (i < args.length) {
					landedTitleFileName = args[i];
				}
				break;
			case "-loc" :
				i++;
				while (i < args.length && args[i].charAt(0) != '-') {
					localisationFiles.addLast(args[i]);
					i++;
				}
				i--;
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
					try {
						nbState = Integer.parseInt(args[i]);
					} catch (NumberFormatException e) {
						if (text == null) {
							throw new IllegalArgumentException("ERROR : language not specified");
						}
						throw new IllegalArgumentException(text.invalidStateNumber());
					}
				}
				break;
			case "-size" :
				i++;
				if (i < args.length) {
					String sMaxTextSize = args[i];
					if (sMaxTextSize.charAt(0) == 'p') {
						// The max text size is proportional to the map
						proportional = true;
					} else {
						// The max text size is absolute
						try {
							maxTextSize = Integer.parseInt(sMaxTextSize);
						} catch (NumberFormatException e) {
							if (text == null) {
								throw new IllegalArgumentException("ERROR : language not specified");
							}
							throw new IllegalArgumentException(text.invalidMaxTextSize());
						}
					}
				}
				break;
			case "-multicolor" :
				harmonize = false;
				break;
			case "-rightDate" :
				leftDate = false;
				break;
			default :
				if (text == null) {
					throw new IllegalArgumentException("ERROR : language not specified");
				}
				throw new IllegalArgumentException(text.wrongArgument(args[i]));
			}
			i++;
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
		if (landedTitleFileName == null) {
			throw new IllegalArgumentException(text.missingLandedTitleFile());
		}
		if (localisationFiles.isEmpty()) {
			throw new IllegalArgumentException(text.missingLocalisationFiles());
		}
		if (fontName == null) {
			throw new IllegalArgumentException(text.missingFontName());
		}
		if (nbState == 0) {
			throw new IllegalArgumentException(text.missingStateNumber());
		} else if (nbState < 0) {
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

	public String getLandedTitleFileName() {
		return landedTitleFileName;
	}

	public LinkedList<String> getLocalisationFiles() {
		return localisationFiles;
	}

	public String getFontName() {
		return fontName;
	}

	public int getNbState() {
		return nbState;
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
}
