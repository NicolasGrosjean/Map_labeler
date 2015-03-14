package biggest_kingdom;
import graphics.Window;

import java.io.File;
import java.io.IOException;

import javax.swing.JProgressBar;

import Text.AbstractText;
import Text.TextEnglish;
import Text.TextFrancais;


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
			new Window(args[3], "Délimiteur de provinces", 600, 400, bar);
			
			// Algorithm
			new Biggest_Pixel(mapFile, args[2], bar, text, 10);
		} catch (IOException e) {
			System.out.println(text.fileNotFound(args[1]));
		} catch (IllegalArgumentException e) {
			System.out.println(text.badNumberArguments());
		}
	}
}
