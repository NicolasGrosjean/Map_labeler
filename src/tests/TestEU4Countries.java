package tests;

import java.io.File;
import java.util.LinkedList;
import java.util.PriorityQueue;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import Text.AbstractText;
import Text.TextFrancais;
import stateNames.EU4Countries;
import stateNames.EU4DirectoryReader;
import stateNames.EU4Files;
import stateNames.GameFiles.TimeFileComparator;

public class TestEU4Countries {
	private static EU4Countries countries;

	@BeforeClass
	public static void SetUp() {
		/* Game parameters */
		String gameDirectory = "C:/Jeux/Steam/SteamApps/common/Europa Universalis IV";
		LinkedList<String> modDirectories = new LinkedList<String>();
		AbstractText text = new TextFrancais();
//		modDirectories.add("C:/Users/Nicolas/Documents/Paradox Interactive/Crusader Kings II/MOD/Historical Immersion Project");
		EU4Files files = new EU4Files(gameDirectory, modDirectories, text);
		
		// Read the directories and sort the files
		PriorityQueue<File> countryFiles = new PriorityQueue<File>(20, files.new TimeFileComparator());
		EU4DirectoryReader.readAndSortDirectoryFiles(gameDirectory, text,
				countryFiles);
		while (!modDirectories.isEmpty()) {
			EU4DirectoryReader.readAndSortDirectoryFiles(modDirectories.removeFirst(),
					text, countryFiles);
		}
		
		// Transform Files priority queues list into String list
		LinkedList<String> countryFileNames = new LinkedList<String>();
		while (!countryFiles.isEmpty()) {
			countryFileNames.addLast(countryFiles.remove().toString());
		}
		
		// Check that all is good
		if (countryFileNames.isEmpty()) {
			throw new IllegalArgumentException(text.missingCountryFiles());
		}
		
		// Final storage of the useful information
		countries = new EU4Countries(countryFileNames);
	}

	@Test
	public void testOneCountryOneColor() {
		LinkedList<String> doubleStates = countries.getDouble();
		//System.out.println(doubleStates);
		Assert.assertEquals(0, doubleStates.size());
	}

	@Test
	public void testCountryColor() {
		Assert.assertEquals("Aachen", countries.getStateName((157 << 16) + (51 << 8) + 167));
		Assert.assertEquals("Chimu", countries.getStateName((39 << 16) + (123 << 8) + 126));
		// TODO : More examples
	}
}
