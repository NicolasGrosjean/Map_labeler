package tests;


import java.io.File;
import java.util.LinkedList;
import java.util.PriorityQueue;

import org.junit.BeforeClass;
import org.junit.Test;

import stateNames.Ck2DirectoryReader;
import stateNames.Ck2Files;
import Text.AbstractText;
import Text.TextFrancais;

public class TestLocalisation {
	private static PriorityQueue<File> landedTitlesFiles;
	private static PriorityQueue<File> localisationFiles;
	private static LinkedList<String> landedTitlesFilesNames = new LinkedList<String>();
	private static LinkedList<String> localisationFilesNames = new LinkedList<String>();
	private static String gameDirectory;
	private static LinkedList<String> modDirectories = new LinkedList<String>();
	private static AbstractText text = new TextFrancais();
	private static Ck2Files files;

	@BeforeClass
	public static void SetUp() {
		/* Game parameters */
		gameDirectory = "C:/Jeux/Steam/SteamApps/common/Crusader Kings II";
		modDirectories.add("C:/Users/Nicolas/Documents/Paradox Interactive/Crusader Kings II/MOD/Historical Immersion Project");
		files = new Ck2Files(gameDirectory, modDirectories, text, null);
		
		/* Sort and read the files */
		landedTitlesFiles = new PriorityQueue<File>(20, files.new TimeFileComparator());
		localisationFiles = new PriorityQueue<File>(20, files.new LexicalFileComparator());
		Ck2DirectoryReader.readAndSortDirectoryFiles(gameDirectory, text,
				landedTitlesFiles, localisationFiles);
		while (!modDirectories.isEmpty()) {
			Ck2DirectoryReader.readAndSortDirectoryFiles(modDirectories.removeFirst(),
					text, landedTitlesFiles, localisationFiles);
		}
		// Transform Files priority queues list into String list
		while (!landedTitlesFiles.isEmpty()) {
			landedTitlesFilesNames.addLast(landedTitlesFiles.remove().toString());
		}
		while (!localisationFiles.isEmpty()) {
			localisationFilesNames.addLast(localisationFiles.remove().toString());
		}	
	}

	@Test
	public void testPrintLocalisationOrder() {
		for (String f : localisationFilesNames) {
			System.out.println(f);
		}
	}
}
