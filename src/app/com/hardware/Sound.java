package app.com.hardware;

import java.io.IOException;

public final class Sound {

	public static void setVolume(int volume) {
		String command = "amixer sset 'PCM' " + volume + "%";
		try {
			Runtime.getRuntime().exec(command);
		} catch (IOException e) {
			System.out.println("Sound_Error: " + e);
			e.printStackTrace();
		}
	}
	
	public static void setVolumeUp() {
		String command = "amixer sset 'PCM' 20%+";
		try {
			Runtime.getRuntime().exec(command);
		} catch (IOException e) {
			System.out.println("Sound_Error: " + e);
			e.printStackTrace();
		}
	}
	
	public static void setVolumeDown() {
		String command = "amixer sset 'PCM' 20%-";
		try {
			Runtime.getRuntime().exec(command);
		} catch (IOException e) {
			System.out.println("Sound_Error: " + e);
			e.printStackTrace();
		}
	}
	
	public static void setVolumeON() {
		String command = "amixer sset 'PCM' 60%";
		try {
			Runtime.getRuntime().exec(command);
		} catch (IOException e) {
			System.out.println("Sound_Error: " + e);
			e.printStackTrace();
		}
	}

	public static void setVolumeOFF() {
		String command = "amixer sset 'PCM' 0%";
		try {
			Runtime.getRuntime().exec(command);
		} catch (IOException e) {
			System.out.println("Sound_Error: " + e);
			e.printStackTrace();
		}
	}
	
	

}
