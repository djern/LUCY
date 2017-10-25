package app.com.hardware;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

public class MicroHandler {

	int numBytesRead;
	byte[] targetData;
	byte[] tempData;
	Path file_save;
	Path file_toText;
	MicroRecorder recording;
	AudioFormat format;
	DataLine.Info targetInfo;
	DataLine.Info sourceInfo;
	TargetDataLine targetLine;
	SourceDataLine sourceLine;
	
	public MicroHandler() {
		format = new AudioFormat(16000, 16, 2, true, true);
		targetInfo = new DataLine.Info(TargetDataLine.class, format);
		sourceInfo = new DataLine.Info(SourceDataLine.class, format);
		file_save = Paths.get("./resources/micro_save.pcm");
		file_toText = Paths.get("./resources/toText.pcm");

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
		
		recording = new MicroRecorder(targetLine, targetData, file_save);
	}

	public void startMicro() throws IOException {
		targetLine.start();
		recording.run();
	}

	public void stopMicro() throws IOException {
		targetLine.stop();
		Files.copy(file_save, file_toText, StandardCopyOption.REPLACE_EXISTING);
	}
	
	public void playRecord() throws IOException {
		sourceLine.start();
		tempData = Files.readAllBytes(file_toText);
		sourceLine.write(tempData, 0, tempData.length);
		sourceLine.stop();		
	}
	public static int calculateRMSLevel(byte[] audioData)
	{ 
	    long lSum = 0;
	    for(int i=0; i < audioData.length; i++)
	        lSum = lSum + audioData[i];

	    double dAvg = lSum / audioData.length;
	    double sumMeanSquare = 0d;

	    for(int j=0; j < audioData.length; j++)
	        sumMeanSquare += Math.pow(audioData[j] - dAvg, 2d);

	    double averageMeanSquare = sumMeanSquare / audioData.length;

	    return (int)(Math.pow(averageMeanSquare,0.5d) + 0.5);
	}

}