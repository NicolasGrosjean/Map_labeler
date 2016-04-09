package tests;

import input.DirectoryReader;
import input.MainArguments;

import java.io.File;
import java.util.LinkedList;
import java.util.PriorityQueue;

import org.junit.BeforeClass;
import org.junit.Test;

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
	private static MainArguments ma = new MainArguments();

	@BeforeClass
	public static void SetUp() {
		/* Game parameters */
		gameDirectory = "C:/Jeux/Steam/SteamApps/common/Crusader Kings II";
		modDirectories.add("C:/Users/Nicolas/Documents/Paradox Interactive/Crusader Kings II/MOD/Historical Immersion Project");
		
		/* Sort and read the files */
		landedTitlesFiles = new PriorityQueue<File>(20, ma.new TimeFileComparator());
		localisationFiles = new PriorityQueue<File>(20, ma.new LexicalFileComparator());
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
	}

	@Test
	public void testPrintLocalisationOrder() {
		for (String f : localisationFilesNames) {
			System.out.println(f);
		}
	}
}
