package biggest_states;
import graphics.ImageNotFoundException;
import graphics.Window;
import input.MainArguments;

import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

import textWriting.DateWriting;

/* TODO
 * - Traduction de la date
 * - Traduction dans le landed title
 */
/* MESURE DU TEMPS
 * long begin = System.currentTimeMillis();
 * CODE
 * long end = System.currentTimeMillis();
 * System.out.println("Temps : " + (((float) (end-begin)) / 1000f));*/
public class Main {
	static public boolean TESTMOD = false;

	public static void main(String[] args) {
		MainArguments mainArgs = new MainArguments();
		try {
			// Input management
			mainArgs = new MainArguments(args);
			String outFileName = mainArgs.getOutFileName();
			File mapFile = new File(mainArgs.getMapFileName());

			// Progression information
			JProgressBar bar = new JProgressBar();
			new Window(mainArgs.getImageFileName(), 600, 400, bar);

			// Output
			File outFile = new File(outFileName);

			// Dialog if the out file already exists
			boolean outFileProblem = outFile.exists();
			while (outFileProblem) {
				String options[] = {mainArgs.getText().yes(),
						mainArgs.getText().no()};
				int option = JOptionPane.showOptionDialog(null,
						mainArgs.getText().warningReplacementMessage(outFileName),
						mainArgs.getText().confirmReplacementTitle(),
						JOptionPane.YES_NO_OPTION,
						JOptionPane.WARNING_MESSAGE,
						null,
						options,
						options[1]);
				if (option == JOptionPane.NO_OPTION){
					// Choose another output file name
					Object objOutFileName = JOptionPane.showInputDialog(null,
							mainArgs.getText().anotherChoiceMessage(),
							mainArgs.getText().anotherChoiceTitle(),
							JOptionPane.QUESTION_MESSAGE, null, null,
							outFileName);
					// Test if the problem persists
					if (objOutFileName != null) {
						outFileName = objOutFileName.toString();
						outFile = new File(outFileName);
						outFileProblem = outFile.exists();
					} else {
						// Restore previous output file name
						outFileName = mainArgs.getOutFileName();
					}
				} else if (option == JOptionPane.CLOSED_OPTION) {
					// Stop the program
					throw new IllegalArgumentException(mainArgs.getText().exitDialog());
				} else {
					// Write on the previous output file is not a problem
					outFileProblem = false;
				}
			}

			// Date
			String date = DateWriting.readDate(mainArgs.getMapFileName(),
					mainArgs.getText(), mainArgs.isYearOnly(),
					mainArgs.isTextualDate());

			// Algorithm
			new Biggest_Pixel(mapFile, outFileName, bar, mainArgs.getText(),
					mainArgs.getNbState(), mainArgs.isAllStates(), date,
					mainArgs.isHarmonize(), mainArgs.getMaxTextSize(),
					mainArgs.isProportional(), mainArgs.isLeftDate(),
					mainArgs.getGameFiles(), mainArgs.getFontName());
		} catch (IOException e) {
			System.out.println(mainArgs.getText().fileNotFound(
					mainArgs.getMapFileName()));
			if (!TESTMOD) {
				System.exit(1);
			}
		} catch (ImageNotFoundException e) {
			System.out.println(mainArgs.getText().fileNotFound(
					mainArgs.getImageFileName()));
			if (!TESTMOD) {
				System.exit(1);
			}
		} catch (IllegalArgumentException e) {
			System.out.println(e.getLocalizedMessage());
			if (!TESTMOD) {
				System.exit(1);
			}
		}
	}
}
