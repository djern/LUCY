package app.smart;

import java.time.ZoneId;
import java.time.ZonedDateTime;

public final class TimeFactory {

	private static ZonedDateTime time;
	private static String output = "";
	
	private static String[] hours = {"null", "ein", "zwei", "drei", "vier", "fünf", "sechs", "sieben", "acht", "neun", "zehn", "elf",
			"zwölf", "dreizehn", "vierzehn", "fünfzehn", "sechzehn", "siebzehn", "achtzehn", "neunzehn", "zwanzig", "einundzwanzig",
			"zweiundzwanzig", "dreiundzwanzig"};
	
	private static String[] minutes = {"", "eins", "zwei", "drei", "vier", "fünf", "sechs", "sieben", "acht", "neun", "zehn",
			"elf", "zwölf", "dreizehn", "vierzehn", "fünfzehn", "sechzehn", "siebzehn", "achtzehn", "neunzehn", "zwanzig",
			"einundzwanzig", "zweiundzwanzig", "dreiundzwanzig", "vierundzwanzig", "fünfundzwanzig","sechsundzwanzig","siebenundzwanzig","achtundzwanzig","neunundzwanzig", "dreißig",
			"einunddreißig", "zweiunddreißg", "dreiunddreißig", "vierunddreißig", "fünfunddreißig", "sechsunddreißig", "siebenunddreißig", "achtunddreißig", "neununddreißig", "vierzig",
			"einundvierzig", "zweiundvierzig", "dreiundvierzig", "vierundvierzig", "fünfundvierzig", "sechsundvierzig", "siebenundvierzig", "achtundvierzig", "neunundvierzig", "fünfzig",
			"einundfünfzig", "zweiundfünfzig", "dreiundfünfzig", "vierundfünfzig", "fünfundfünfzig", "sechsundfünfzig", "siebenundfünfzig", "achtundfünfzig", "neunundfünfzig"};
	
	public static String getTimeString() {
		getTime();
		int hr = time.getHour();
		int min = time.getMinute();
		output = "Es ist " + hours[hr] + " Uhr " + minutes[min] +".";
		return output;
	}

	public static int getTimeHours() {
		getTime();
		return time.getHour(); 
	}

	public static int getTimeMins() {
		getTime();
		return time.getMinute(); 
	}
	
	public static int getTimeSec() {
		getTime();
		return time.getSecond(); 
	}
	
	private static void getTime() {
		time = ZonedDateTime.now(ZoneId.of("UTC+1"));
	}
}


