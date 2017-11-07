package app.gui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import app.com.hardware.ControllerHW;
import app.com.hue.ControllerHue;
import app.com.voice.TextToVoice;

public class DebugApp {
	private static ControllerHue controllerHue;
	private static ControllerHW controllerHW;

	public static void main(String[] args) throws Exception {
		System.out.println("Starting Debug-App...\n");

		System.out.println("Step 1/3: Creating  Hue-Controller");
		controllerHue = new ControllerHue();
		
		System.out.println("Step 2/3: Creating  HW-Controller");
		controllerHW = new ControllerHW();
		
		System.out.println("Step 3/3: Loading last bridge-IP...\n");
		System.out.println("Last bridge: "+ controllerHue.getLastIP());
		
		//Steuerung
		int menu = 0;
		int choice;
		String input;
		
		while(true) {			
			if(menu == 0) {
			    System.out.println("######################");
			    System.out.println("Select: ");
				System.out.println("1: Start Search");
				System.out.println("2: Connect to Bridge (new IP)");
				System.out.println("3: Connect to Bridge (last IP)");
				System.out.println("4: Load all Lights");
				System.out.println("5: Load all Sensors");
				System.out.println("6: Micro");
				System.out.println("0: End Programm");
				System.out.println("######################\n");
			    choice = Integer.parseInt(getUserInput());
			    
			    switch(choice) {
			    	case 1: 
		    			controllerHue.startSearching();
		    			break;
			    	case 2: 
		    			System.out.println("Enter bridge-IP...");
		    			input = getUserInput();
		    			controllerHue.connectBridge(input);
		    			break;
			    	case 3: 
		    			controllerHue.connectBridge(controllerHue.getLastIP());
		    			break;
			    	case 4: 
		    			if(controllerHue.checkBridgeStatus()) {
		    				controllerHue.loadLights();
		    				controllerHue.printAllLights();
		    				menu = 1;
		    			}
		    			else
			    			System.out.println("Err: No bridge connected.");
		    			break;
			    	case 5: 
		    			if(controllerHue.checkBridgeStatus()) {
		    				controllerHue.loadSensors();
		    				controllerHue.printAllSensors();
		    				menu = 2;
		    			}
		    			else
			    			System.out.println("Err: No bridge connected.");
		    			break;
			    	case 6: 
		    			menu = 3;
		    			break;
			    	case 7: 
		    			TextToVoice tts =  new TextToVoice();
		    			tts.playFile();// readText("Hallo ich bin Lucy!");
		    			break;
			    	case 0: 
	    				return;
			    }
			}
			else if(menu == 1) {
			    System.out.println("######################");
			    System.out.println("Select: ");
				System.out.println("1: Activate Light");
				System.out.println("2: Get Status");
				System.out.println("3: Set Status");
				System.out.println("4: Get Brightness");
				System.out.println("5: Set Brightness");
				System.out.println("0: Back");
				System.out.println("######################\n");
			    choice = Integer.parseInt(getUserInput());
			    
			    switch(choice) {
			    	case 1: 
		    			System.out.println("Enter name of the light...");
		    			input = getUserInput();
		    			controllerHue.activateLight(input);
		    			break;
			    	case 2: 
		    			if(controllerHue.getLightStatus())
		    				System.out.println("The light is on");
		    			else
		    				System.out.println("The light is off");
		    			break;
			    	case 3: 
		    			System.out.println("Turn ON or OFF?");
		    			input = getUserInput();
		    			if(input.equals("ON"))
		    				controllerHue.setLightStatus(true);
		    			else if(input.equals("OFF"))
		    				controllerHue.setLightStatus(false);
		    			else
			    			System.out.println("Err: Undefined input!");
		    			break;
			    	case 4: 
		    			System.out.println("The brightness is: " + controllerHue.getLightBright());
		    			break;
			    	case 5: 
			    		System.out.println("What brightness?");
			    		input = getUserInput();
			    		controllerHue.setLightBright(Integer.parseInt(input));
		    			break;
			    	case 0: 
	    				menu = 0;
		    			break;
			    }
			}
			else if(menu == 2) {
			    System.out.println("######################");
			    System.out.println("Select: ");
				System.out.println("1: Activate Sensor");
				System.out.println("2: Print Sensor-Status");
				System.out.println("0: Back");
				System.out.println("######################\n");
			    choice = Integer.parseInt(getUserInput());
			    
			    switch(choice) {
			    	case 1: 
		    			System.out.println("Enter name of the sensor...");
		    			input = getUserInput();
		    			controllerHue.activateSensor(input);
		    			break;
			    	case 2: 
	    				controllerHue.printSensor();
		    			break;
			    	case 0: 
	    				menu = 0;
		    			break;
			    }
			}
			else if(menu == 3) {
			    System.out.println("######################");
			    System.out.println("Select: ");
				System.out.println("1: Start Recording");
				System.out.println("2: Stop Recording");
				System.out.println("3: Play Record");
				System.out.println("0: Back");
				System.out.println("######################\n");
			    choice = Integer.parseInt(getUserInput());
			    
			    switch(choice) {
			    	case 1: 
		    			System.out.println("Starting Record");
		    			controllerHW.startRecord();
		    			break;
			    	case 2: 
	    				controllerHW.stopRecord();
		    			break;
			    	case 3: 
	    				controllerHW.playRecord();
		    			break;
			    	case 0: 
	    				menu = 0;
		    			break;
			    }
			}
		}
	    		
	}
	
	private static String getUserInput() throws IOException {
		InputStreamReader isr = new InputStreamReader(System.in);
	    BufferedReader reader = new BufferedReader(isr);
		return reader.readLine();
	}

}
