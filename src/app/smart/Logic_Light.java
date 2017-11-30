package app.smart;

import app.com.hardware.Light;
import app.com.hue.ControllerHue;
import app.gui.Config;

public class Logic_Light {
		
	public static boolean parseInput(String input, ControllerHue controllerHue) {
		
		int status = -1;//-1= keine Änderung
						//0 = aus
						//1 = an
						//2 = heller
						//3 = dunkler
						//4 = nacht
						//5 = lesen

		int color = -1; //-1 keine Änderung 	
						//0 = weiß;
						//1 = grün;
						//2 = rot;
						//3 = blau;
						//4 = gelb;
						//5 = orange;		
		
		String room; 	 
		String place; 	
		
		
		//Step 1: Check Status
		if(input.contains(" aus")) {
			status = 0;
		}
		else if(input.contains(" an")) {
			status = 1;	
		}
		else if(input.contains("heller")) {
			status = 2;	
		}
		else if(input.contains("dunkler")) {
			status = 3;	
		}
		else if(input.contains("nacht")) {
			status = 4;	
		}
		else if(input.contains("lesen")) {
			status = 5;	
		}
		else if(input.contains("weiß")) {
			color = 0;	
		}
		else if(input.contains("grün")) {
			color = 1;	
		}
		else if(input.contains("rot")) {
			color = 2;	
		}
		else if(input.contains("blau")) {
			color = 3;	
		}
		else if(input.contains("gelb")) {
			color = 4;	
		}
		else if(input.contains("orange")) {
			color = 5;	
		}
		else {
			return false;
		}
		
		//Step 2: Check Room
		if(input.contains("schlafzimmer")) {
			room = "schlafzimmer";
		}
		else {
			room = Config.SYSTEM_ROOM;
		}
		
		//Step 3: Check Place
		if(input.contains("bett")) {
			place = "bett";
		}
		else {
			place = "bett";
		}
		
		Light light= new Light(room, place, status, color, controllerHue);
		return light.activate();
	}

}
