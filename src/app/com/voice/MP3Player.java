package app.com.voice;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class MP3Player {
	
	private File file;
	private Clip clip;
	
	public MP3Player(String path_str) {
		file = new File(path_str);
	}

	public Clip playFile() {
		try{
		    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
		    clip = AudioSystem.getClip();
		    clip.open(audioInputStream);
		    System.out.println("Playing...");
		    clip.start();
		    return clip;
		}
		catch(Exception ex)
		{
		    System.out.println(ex);
		    return null;
		}
	}
	
	public void stop() {
		clip.stop();
	}
}
