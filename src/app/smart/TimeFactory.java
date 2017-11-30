package app.smart;

import java.time.ZoneId;
import java.time.ZonedDateTime;

public final class TimeFactory {

	private static ZonedDateTime time;
	private static String output = "";
	
	private static String[] hours = {"null", "ein", "zwei", "drei", "vier", "f�nf", "sechs", "sieben", "acht", "neun", "zehn", "elf",
			"zw�lf", "dreizehn", "vierzehn", "f�nfzehn", "sechzehn", "siebzehn", "achtzehn", "neunzehn", "zwanzig", "einundzwanzig",
			"zweiundzwanzig", "dreiundzwanzig"};
	
	private static String[] minutes = {"", "eins", "zwei", "drei", "vier", "f�nf", "sechs", "sieben", "acht", "neun", "zehn",
			"elf", "zw�lf", "dreizehn", "vierzehn", "f�nfzehn", "sechzehn", "siebzehn", "achtzehn", "neunzehn", "zwanzig",
			"einundzwanzig", "zweiundzwanzig", "dreiundzwanzig", "vierundzwanzig", "f�nfundzwanzig","sechsundzwanzig","siebenundzwanzig","achtundzwanzig","neunundzwanzig", "drei�ig",
			"einunddrei�ig", "zweiunddrei�g", "dreiunddrei�ig", "vierunddrei�ig", "f�nfunddrei�ig", "sechsunddrei�ig", "siebenunddrei�ig", "achtunddrei�ig", "neununddrei�ig", "vierzig",
			"einundvierzig", "zweiundvierzig", "dreiundvierzig", "vierundvierzig", "f�nfundvierzig", "sechsundvierzig", "siebenundvierzig", "achtundvierzig", "neunundvierzig", "f�nfzig",
			"einundf�nfzig", "zweiundf�nfzig", "dreiundf�nfzig", "vierundf�nfzig", "f�nfundf�nfzig", "sechsundf�nfzig", "siebenundf�nfzig", "achtundf�nfzig", "neunundf�nfzig"};
	
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


