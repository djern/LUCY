package app.gui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import app.com.hardware.ControllerHW;

public class LucyApp {

	private static ControllerHW controllerHW;
	private static boolean running = true;
	
	public static void main(String[] args) throws IOException {
		System.out.println("Starting Lucy...\n");
				
		System.out.println("Creating HW-Controller");
		controllerHW = new ControllerHW();
		
		while(running) {
			System.out.println("START or STOP?");
			String input = getUserInput();
			if(input.equalsIgnoreCase("START"))
				controllerHW.startRecord();
			else if(input.equalsIgnoreCase("STOP"))
				controllerHW.stopRecord();
			else
				running = false;
		}
	}

	private static String getUserInput() throws IOException {
		InputStreamReader isr = new InputStreamReader(System.in);
	    BufferedReader reader = new BufferedReader(isr);
		return reader.readLine();
	}
}
