package biggest_states;
import graphics.ImageNotFoundException;
import graphics.Window;
import input.MainArguments;

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
		MainArguments mainArgs = new MainArguments();
		try {
			// Input gestion
			mainArgs = new MainArguments(args);
			// Input : BMP map file
			File mapFile = new File(mainArgs.getMapFileName());

			// Progression information
			JProgressBar bar = new JProgressBar();
			new Window(mainArgs.getImageFileName(), "Carte des plus grands Etats",
					600, 400, bar);

			// Date
			String date = DateWriting.readDate(mainArgs.getMapFileName(),
					mainArgs.getText());

			// Algorithm
			new Biggest_Pixel(mapFile, mainArgs.getOutFileName(), bar,
					mainArgs.getText(), mainArgs.getNbState(), date,
					mainArgs.isHarmonize(), mainArgs.getMaxTextSize(),
					mainArgs.isProportional(), mainArgs.isLeftDate(),
					mainArgs.getLocalisationFiles(), mainArgs.getFontName(),
					mainArgs.getLandedTitleFileName());
		} catch (IOException e) {
			System.out.println(mainArgs.getText().fileNotFound(
					mainArgs.getMapFileName()));
			if (!TESTMOD) {
				System.exit(1);
			}
		} catch (ImageNotFoundException e) {
			System.out.println(mainArgs.getText().fileNotFound(
					mainArgs.getImageFileName()));
			if (!TESTMOD)
				System.exit(1);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getLocalizedMessage());
		}
	}
}
