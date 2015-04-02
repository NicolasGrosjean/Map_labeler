package biggest_states;
import graphics.ImageNotFoundException;
import graphics.Window;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.swing.JProgressBar;

import textWriting.DateWriting;
import Text.AbstractText;
import Text.TextEnglish;
import Text.TextFrancais;

/* OPTIONS A FAIRE :
 * - si le fichier résultat existe déjà faire une mini-IG pour dire si l'écraser ou donner un autre nom
 */
public class Main {
	static public boolean TESTMOD = false;

	public static void main(String[] args) {
		// Language
		AbstractText text = null;
		// Files
		String mapFileName = null;
		String outFileName = null;
		String imageFileName = null;
		String landedTitleFileName = null;
		LinkedList<String> localisationFiles = new LinkedList<String>();
		// Other parameters
		String fontName = null;
		int nbState = 0;
		int maxTextSize = Integer.MAX_VALUE;
		boolean proportional = false; // if true -> proportional to map size
		/* States text color (if true black and white -> harmonized
		 * else it is the opposite color of the state -> multicolor) */
		boolean harmonize = true;
		/* Position of the date on the map */
		boolean leftDate = true;

		try {
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
			if (maxTextSize <= 0) {
				throw new IllegalArgumentException(text.invalidMaxTextSize());
			}

			// Input : BMP map file
			File mapFile = new File(mapFileName);

			// Progression information
			JProgressBar bar = new JProgressBar();
			new Window(imageFileName, "Carte des plus grands Etats", 600, 400, bar);

			// Date
			String date = DateWriting.readDate(mapFileName, text);

			// Algorithm
			new Biggest_Pixel(mapFile, outFileName, bar, text, nbState, date,
					harmonize, maxTextSize,	proportional, leftDate,
					localisationFiles, fontName, landedTitleFileName);
		} catch (IOException e) {
			System.out.println(text.fileNotFound(mapFileName));
			if (!TESTMOD) {
				System.exit(1);
			}
		} catch (ImageNotFoundException e) {
			System.out.println(text.fileNotFound(imageFileName));
			if (!TESTMOD)
				System.exit(1);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getLocalizedMessage());
		}
	}
}
