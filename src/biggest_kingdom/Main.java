package biggest_kingdom;
import java.io.File;
import java.io.IOException;

import javax.swing.JProgressBar;

import Text.AbstractText;
import Text.TextEnglish;
import Text.TextFrancais;


public class Main {
	public static void main(String[] args) {
		// Language (English by default)
		AbstractText text = new TextEnglish();
				
		try {
			if (args.length != 3) {
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
			
			// Algorithm
			new Biggest_Pixel(mapFile, args[2], bar, text, 10);
		} catch (IOException e) {
			System.out.println(text.fileNotFound(args[1]));
		} catch (IllegalArgumentException e) {
			System.out.println(text.badNumberArguments());
		}
	}
}
