package biggest_states;
import graphics.ImageNotFoundException;
import graphics.Window;

import java.io.File;
import java.io.IOException;

import javax.swing.JProgressBar;

import textWriting.DateWriting;
import Text.AbstractText;
import Text.TextEnglish;
import Text.TextFrancais;

/* OPTIONS A FAIRE :
 * - fichiers de localisation
 * - si le fichier résultat existe déjà faire une mini-IG pour dire si l'écraser ou donner un autre nom
 * - nombre d'Etats à afficher
 * - harmonisation
 * - taille max du texte
 * - position de la date (gauche ou droite)
 */
/* Pour la taille max du texte prévoir 3 options
 * - Aucune : Integer.MAX_VALUE
 * - Absolu : ex 100 alors c'est celle valeur
 * - Proportionnel : calculé à partir de la taille de l'image
 */
public class Main {
	static public boolean TESTMOD = false;

	/**
	 * The main of the software
	 * @param args {0: language, 1 : BMP map file, 2 : PNG output file,
	 *  3 : illustration file}
	 */
	public static void main(String[] args) {
		// Language
		AbstractText text = null;
		// Files
		String mapFileName = null;
		String outFileName = null;
		String imageFileName = null;

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
				default :
					if (text == null) {
						throw new IllegalArgumentException("ERROR : language not specified");
					}
					throw new IllegalArgumentException(text.wrongArgument(args[i]));
				}
				i++;
			}

			// Check the needed parameters are here
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

			// Input : BMP map file
			File mapFile = new File(mapFileName);

			// Progression information
			JProgressBar bar = new JProgressBar();
			new Window(imageFileName, "Carte des plus grands Etats", 600, 400, bar);

			// Date
			String date = DateWriting.readDate(mapFileName);

			// Algorithm
			new Biggest_Pixel(mapFile, outFileName, bar, text, 10, date, true, 100,
					true);
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
