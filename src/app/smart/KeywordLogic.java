package app.smart;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import app.com.hue.ControllerHue;
import app.com.voice.MP3Player;
import app.com.voice.TextToVoice;
import app.gui.Config;

public final class KeywordLogic {
	
	static String command;
	static MP3Player player;
	ControllerHue controllerHue;
		
	static String trigger = "lucy";
	static String[] keywords = {
			"system", 			//i = 0
			"licht", 			//i = 1
			"musik", 			//i = 2
			"nachrichten", 		//i = 3
			"stop",				//i = 4
			"schlafen", 		//i = 5
			"wie spät ist es", 	//i = 6
			"einkaufsliste", 	//i = 7
			"kaufen", 			//i = 8
			"einkaufen" 		//i = 9
			};			
	
	/*
	 * Keyword Mapping
	 * 		-3: Too many keywords
	 * 		-2: Triggered without usable content
	 * 		-1: Not triggered
	 * 		 0: system
	 * 		 1: light
	 * 		 2: music
	 * 		 3: news
	 * 		 4: stop
	 * 		 5: sleep
	 * 		 6: time
	 * 		 7: shopping-list
	 * 		 8: shopping-list
	 * 		 9: shopping-list 
	 */
	
	public KeywordLogic() {
		System.out.println("Creating HUE Controller");
		controllerHue = new ControllerHue();
	}
	
	public void parseInput(String input) throws IOException {
		int keyIndex;
		command = input.toLowerCase();
		System.out.println("Checking command: " + command);
		
		keyIndex = checkKeyword();
		if(keyIndex < -1)
			playTriggerSound(false);
		
		switch(keyIndex) {
			case -3:
				System.out.println("KeyIndex = " + keyIndex + " : Zu viele Keywords gefunden!");
				break;
			case -2:
				System.out.println("KeyIndex = " + keyIndex + " : Kein passendes Keyword gefunden!");
				break;
			case -1:
				System.out.println("KeyIndex = " + keyIndex + " : Trigger 'Lucy' nicht erkannt!");
				break;
			case 0:
				System.out.println("KeyIndex = " + keyIndex + " : Keyword 'System' erkannt...");
				break;
			case 1:
				System.out.println("KeyIndex = " + keyIndex + " : Keyword 'Licht' erkannt...");
				playTriggerSound(Logic_Light.parseInput(command, controllerHue));
				break;
			case 2:
				System.out.println("KeyIndex = " + keyIndex + " : Keyword 'Musik' erkannt...");
				playTriggerSound(Logic_Musik.parseInput(command));
				break;
			case 3:
				System.out.println("KeyIndex = " + keyIndex + " : Keyword 'Nachrichten' erkannt...");
				playTriggerSound(true);
	    		player = Logic_News.playNews();
				break;
			case 4:
				System.out.println("KeyIndex = " + keyIndex + " : Keyword 'Stop' erkannt...");
				playTriggerSound(true);
				Logic_News.stopNews(player);
				break;
			case 5:
				System.out.println("KeyIndex = " + keyIndex + " : Keyword 'Schlafen' erkannt...");
				break;
			case 6:
				System.out.println("KeyIndex = " + keyIndex + " : Keyword 'Wie spät ist es' erkannt...");
				playTriggerSound(true);
				TextToVoice tts =  new TextToVoice();
    			tts.runTTV(TimeFactory.getTimeString());
				break;
			case 7:
				System.out.println("KeyIndex = " + keyIndex + " : Keyword 'Einkaufsliste' erkannt...");
				break;
			case 8:
				System.out.println("KeyIndex = " + keyIndex + " : Keyword 'kaufen' erkannt...");
				break;
			case 9:
				System.out.println("KeyIndex = " + keyIndex + " : Keyword 'einkaufen' erkannt...");
				break;
		}
	}

	private int checkKeyword() {
		if(checkLucy()) {
			int mapping = -2;
			//check all keywords for a matching
			for(int i = 0; i < keywords.length; i++) {
				if(command.contains(keywords[i])) {
					//if matching -> check if first hit
					if(mapping != -2)
						mapping = -3; //second+ hit -> "Too many keywords"
					else
						mapping = i; //save index					
				}
			}
			return mapping;
		}
		else
			return -1; //Trigger not found
	}
	
	private boolean checkLucy() {
		return command.contains(trigger);
	}
	
	private void playTriggerSound(boolean good_bad) {
		URL url = null;
		File file;
		if(good_bad)
			file = new File(Config.SYSTEM_SOUND_TRIGGERED);
		else
			file = new File(Config.SYSTEM_SOUND_ERROR);
		try {
			url = file.toURI().toURL();
		} 
		catch (MalformedURLException e) {
			e.printStackTrace();
		}
		TextToVoice tts =  new TextToVoice();
		tts.playSound(url.toString());
	}
}
