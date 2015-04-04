package textWriting;

import Text.AbstractText;

public class DateWriting {

	public static String readDate(String fileName, AbstractText text,
			boolean yearOnly, boolean textualDate) {
		String [] words = fileName.split("[_.]");
		int intNb = 0;
		int day = 0;
		int month = 0;
		int year = 0;
		for (int i = 0; i < words.length; i++) {
			if(!yearOnly) {
				try {
					int readInt = Integer.parseInt(words[i]);
					intNb++;
					if (intNb == 1) {
						day = readInt;
					} else if (intNb == 2) {
						month = readInt;
					} else if (intNb == 3) {
						year = readInt;
					}
				} catch (NumberFormatException e) {
				}
			} else {
				try {
					year = Integer.parseInt(words[i]);
				} catch (NumberFormatException e) {
				}
			}
		}
		if (!yearOnly && (day < 1 || day > 31)) {
			throw new IllegalArgumentException(text.invalidDay());
		}
		if (year < 0) {
			throw new IllegalArgumentException(text.invalidYear());
		}
		if (yearOnly) {
			return year + "";
		}
		if (textualDate) {
			return day + " " + monthToString(month, text) + " " + year;
		} else {
			return numberToString(day) + "/" + numberToString(month) + "/" +
					year;
		}
	}

	private static String monthToString(int month, AbstractText text) {
		switch (month) {
			case 1 :
				return "Janvier";
			case 2 :
				return "Février";
			case 3 :
				return "Mars";
			case 4 :
				return "Avril";
			case 5 :
				return "May";
			case 6 :
				return "Juin";
			case 7 :
				return "Juillet";
			case 8 :
				return "Août";
			case 9 :
				return "Septembre";
			case 10 :
				return "Octobre";
			case 11 :
				return "Novembre";
			case 12 :
				return "Décembre";
		}
		throw new IllegalArgumentException(text.invalidMonth());
	}

	private static String numberToString(int number) {
		if (number < 10) {
			return "0" + number;
		} else {
			return "" + number;
		}
	}
}
