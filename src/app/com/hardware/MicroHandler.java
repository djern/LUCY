package app.com.hardware;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

import app.com.voice.SpeechDetector;
import app.com.voice.VoiceToText;
import app.gui.Config;

public class MicroHandler {

	int numBytesRead;
	byte[] targetData;
	byte[] tempData;
	Path file_save;
	Path file_toText;
	Runnable recorder;
	boolean microActive;
	boolean speaking = false;
	boolean speaking_before = false;
	AudioFormat format;
	DataLine.Info targetInfo;
	DataLine.Info sourceInfo;
	TargetDataLine targetLine;
	SourceDataLine sourceLine;
	SpeechDetector speechDetector;
	VoiceToText translator;
	AudioFormat.Encoding encoding = AudioFormat.Encoding.PCM_SIGNED;
	
	public MicroHandler() throws IOException {
		System.out.println("Creating SpeechDetector");
		speechDetector = new SpeechDetector();
		System.out.println("Creating VoiceToText");
		translator = new VoiceToText();
		
		format = new AudioFormat(16000, 16, 1, true, false);
		targetInfo = new DataLine.Info(TargetDataLine.class, format);
		sourceInfo = new DataLine.Info(SourceDataLine.class, format);
		file_save = Paths.get(Config.MICRO_AUDIO_SAVE);
		file_toText = Paths.get(Config.MICRO_AUDIO_TOTEXT);

		try {
			targetLine = (TargetDataLine) AudioSystem.getLine(targetInfo);
			targetLine.open(format);
			
			sourceLine = (SourceDataLine) AudioSystem.getLine(sourceInfo);
			sourceLine.open(format);

			targetData = new byte[targetLine.getBufferSize() / 5];
		}
		catch (Exception e) {
			System.err.println(e);
		}
		
		recorder = new Runnable() {
			@Override
			public void run() {
				microActive = true;
				try {
					Files.write(file_save, new byte[0]);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				while (microActive == true) {
					targetLine.read(targetData, 0, targetData.length);
					
					speaking = speechDetector.checkIfSpeaking(targetData);
					try {
						if(speaking) {
							Files.write(file_save, targetData, StandardOpenOption.APPEND);
						}
						else if(speaking == false && speaking_before == true) {
							Files.copy(file_save, file_toText, StandardCopyOption.REPLACE_EXISTING);
							Files.write(file_save, new byte[0]);
//							System.out.println("DETECTED: End of Sentence (" + speechDetector.getDurationInSec() + ")");
							if(speechDetector.getDurationInSec() > Config.SPEECH_SENTENCE_DURATION)
								translator.translate("./resources/toText.pcm");
						}		
					} catch (Exception e) {
						e.printStackTrace();
					}
					speaking_before = speaking;
				}
			}
		};
	}

	public void startMicro() throws IOException {
		targetLine.start();
		new Thread(recorder).start();
	}

	public void stopMicro() throws IOException {
		microActive = false;
		targetLine.stop();
	}
	
	public void playRecord() throws IOException {
		sourceLine.start();
		tempData = Files.readAllBytes(file_toText);
		sourceLine.write(tempData, 0, tempData.length);
		sourceLine.stop();		
	}
}