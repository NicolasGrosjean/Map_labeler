package biggest_states;
import graphics.Window;

import java.io.File;
import java.io.IOException;

import javax.swing.JProgressBar;

import textWriting.DateWriting;
import Text.AbstractText;
import Text.TextEnglish;
import Text.TextFrancais;

/* Pour la taille max du texte prévoir 3 options
 * - Aucune : Integer.MAX_VALUE
 * - Absolu : ex 100 alors c'est celle valeur
 * - Proportionnel : calculé à partir de la taille de l'image
 */
public class Main {

	/**
	 * The main of the software
	 * @param args {0: language, 1 : BMP map file, 2 : PNG output file,
	 *  3 : illustration file}
	 */
	public static void main(String[] args) {
		// Language (English by default)
		AbstractText text = new TextEnglish();

		try {
			if (args.length != 4) {
				throw new IllegalArgumentException("Bad number of arguments");
			}

			// French language if it is asked
			if (args[0].equals("-fr")) {
				text = new TextFrancais();
			}

			// Input : BMP map file
			File mapFile = new File(args[1]);

			// Progression information
			JProgressBar bar = new JProgressBar();
			new Window(args[3], "Carte des plus grands Etats", 600, 400, bar);

			// Date
			String date = DateWriting.readDate(args[1]);

			// Algorithm
			new Biggest_Pixel(mapFile, args[2], bar, text, 10, date, true, 100);
		} catch (IOException e) {
			System.out.println(text.fileNotFound(args[1]));
			System.exit(1);
		} catch (IllegalArgumentException e) {
			System.out.println(text.badNumberArguments());
			System.exit(1);
		}
	}
}
