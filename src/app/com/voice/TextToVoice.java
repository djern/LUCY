package app.com.voice;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.xml.ws.Holder;

import be.tarsos.dsp.io.jvm.AudioPlayer;
import https.cerevoice_com.soap.cloudservice.CereVoiceCloud;
import https.cerevoice_com.soap.cloudservice.CereVoiceCloudPortType;

public class TextToVoice {
	
	public void TextToVoice() {
		
	}
	
	public void readText(String text) {
//		try {
//			 CereVoiceCloud cere = new CereVoiceCloud();
//			 Holder<String> url = new Holder<String>();
//			 Holder<Integer> char_count = new Holder<Integer>();
//			 Holder<Integer> res_code = new Holder<Integer>();
//			 Holder<String> res_des = new Holder<String>();
//			 cere.getCereVoiceCloudPort().speakSimple("59ff7b268da85", "uSCYDryNJP", "Heather",
//			 "Java soap speak simple", url,
//			 char_count, res_code, res_des);
//			 if(res_code.value != 1)
//			 System.out.println("ERROR: request failed - " + res_des.value);
//			 else
//			 System.out.println("URL: " + url.value);
			 playSound();
//			 } catch (Exception e) {
//			 System.err.println(e.toString());
//			 }
	}
	
	public void playSound() {
		try {
		    AudioInputStream in = AudioSystem.getAudioInputStream(new File("https://cerevoice.s3.amazonaws.com/Heather480001c8c2e94d4650f64f7d951bf76b08b0eb.ogg"));
		    if (in != null) {
		        AudioFormat baseFormat = in.getFormat();

		        AudioFormat targetFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, baseFormat.getSampleRate(),
		        16, baseFormat.getChannels(), baseFormat.getChannels() * 2, baseFormat.getSampleRate(), false);

		        AudioInputStream dataIn = AudioSystem.getAudioInputStream(targetFormat, in);

		        byte[] buffer = new byte[4096];

		        // get a line from a mixer in the system with the wanted format
		        DataLine.Info info = new DataLine.Info(SourceDataLine.class, targetFormat);
		        SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);

		        if (line != null) {
		            line.open();

		            line.start();
		            int nBytesRead = 0, nBytesWritten = 0;
		            while (nBytesRead != -1) {
		                nBytesRead = dataIn.read(buffer, 0, buffer.length);
		                if (nBytesRead != -1) {
		                    nBytesWritten = line.write(buffer, 0, nBytesRead);
		                }
		            }

		            line.drain();
		            line.stop();
		            line.close();

		            dataIn.close();
		        }

		        in.close();
		        // playback finished
		    }
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
		    System.out.println("error: " + e);
		} 
	}
	
	public void playFile() {
		AudioInputStream din = null;
	    try {
	        AudioInputStream in = AudioSystem.getAudioInputStream(new URL("https://cerevoice.s3.amazonaws.com/Heather480001c8c2e94d4650f64f7d951bf76b08b0eb.ogg"));
	        AudioFormat baseFormat = in.getFormat();
	        AudioFormat decodedFormat = new AudioFormat(
	                AudioFormat.Encoding.PCM_SIGNED,
	                baseFormat.getSampleRate(), 16, baseFormat.getChannels(),
	                baseFormat.getChannels() * 2, baseFormat.getSampleRate(),
	                false);
	        din = AudioSystem.getAudioInputStream(decodedFormat, in);
	        DataLine.Info info = new DataLine.Info(SourceDataLine.class, decodedFormat);
	        SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);
	        if(line != null) {
	            line.open(decodedFormat);
	            byte[] data = new byte[4096];
	            // Start
	            line.start();

	            int nBytesRead;
	            while ((nBytesRead = din.read(data, 0, data.length)) != -1) {
	                line.write(data, 0, nBytesRead);
	            }
	            // Stop
	            line.drain();
	            line.stop();
	            line.close();
	            din.close();
	        }

	    }
	    catch(Exception e) {
	        e.printStackTrace();
	    }
	    finally {
	        if(din != null) {
	            try { din.close(); } catch(IOException e) { }
	        }
	    }
	}

}
