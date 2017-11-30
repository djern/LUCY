package app.com.hardware;

import java.io.IOException;

public class ControllerHW {
	MicroHandler handlerMicro;
	
	public ControllerHW() throws IOException {
		System.out.println("Creating MicroHandler");
		handlerMicro = new MicroHandler();
	}
	
	public void startRecord() {
		try {
			handlerMicro.startMicro();
		} catch (IOException e) {
			System.out.println("Recording failed!");
			e.printStackTrace();
		}
	}
	
	public void stopRecord() throws IOException {
		handlerMicro.stopMicro();
	}
	
	public void playRecord() {
		try {
			System.out.println("Start playing...");
			handlerMicro.playRecord();
			System.out.println("Finished playing!");
		} catch (IOException e) {
			System.out.println("Playing failed!");
			e.printStackTrace();
		}
	}
}
