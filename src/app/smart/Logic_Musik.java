package app.smart;

import app.com.hardware.Sound;

public class Logic_Musik {
	
	public static boolean parseInput(String input) {
		
		if(input.contains(" an") || input.contains("spiel")) {
			Sound.setVolumeON();
			return true;
		}
		else if(input.contains(" aus") || input.contains(" weg") || input.contains("stop")) {
			Sound.setVolumeOFF();
			return true;			
		}
		else if(input.contains("lauter")) {
			Sound.setVolumeUp();
			return true;			
		}
		else if(input.contains("leiser")) {
			Sound.setVolumeDown();
			return true;			
		}
		else {
			return false;
		}
	}

}
