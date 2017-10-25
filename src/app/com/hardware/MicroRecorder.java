package app.com.hardware;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import javax.sound.sampled.TargetDataLine;

public class MicroRecorder implements Runnable {
	
	boolean recording; 
	TargetDataLine targetLine;
	byte[] targetData;
	Path file_save;
	
	public MicroRecorder(TargetDataLine line, byte[] data, Path path) {
		recording = false;
		targetLine = line;
		file_save = path;
		targetData = data;
	}

	@Override
	public void run() {
		recording = true;
		try {
			Files.write(file_save, new byte[0]);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		while (recording = true) {
			targetLine.read(targetData, 0, targetData.length);
			try {
				Files.write(file_save, targetData, StandardOpenOption.APPEND);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void setTargetLine(TargetDataLine line) {
		targetLine = line;
	}
	
	public void stopRecording() {
		recording = false;
	}
}
