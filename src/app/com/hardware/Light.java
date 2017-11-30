package app.com.hardware;

import app.com.hue.ControllerHue;
import app.gui.Config;

public class Light {
	
	private String room;
	private String place;
	private int status;
	private int color;
	
	private String name;
	private String type;

	ControllerHue controllerHue;
	
	public Light(String room, String place, int status, int color, ControllerHue controllerHue) {
		this.room = room;
		this.place = place;
		this.status = status;
		this.color = color;
		this.controllerHue = controllerHue;
	}
	
	public boolean activate() {
		
		if (getName() == false)
			return false;
		
		if (type.equals(Config.LIGHTS_TYPE_HUE_WHITE)) {
			activateHueLight();
			
			//check allowed parameters
			if(status > 3)
				return false;
			setHueStatus();
			
			//check allowed parameters
			if(color != -1)
				return false;
			setHueColor();
		}

		if (type.equals(Config.LIGHTS_TYPE_HUE_COLOR)) {
			activateHueLight();
			setHueStatus();
			setHueColor();
		}
		
		return true;
	}
		
	private boolean getName() {
		name = room + "_" + place;
		for(int i = 0; i < Config.LIGHTS_NAMES.length; i++) {
			if(Config.LIGHTS_NAMES[i].equals(name)) {
				type = Config.LIGHTS_TYPES[i];
				return true;
			}
		}
		return false;
	}

	private void setHueStatus() {
		System.out.println("Status: " + status);
		if(status == -1) //Keine Änderung
			return;
		else if(status == 0) //aus
			controllerHue.setLightStatus(false);
		else if(status == 1) { //an
			controllerHue.setLightStatus(true);
			controllerHue.setLightBright(100);
		}
		else if(status == 2) //heller
		{
			int brightness = controllerHue.getLightBright();
			brightness += 50;
			if(brightness > 254)
				brightness = 254;
			controllerHue.setLightBright(brightness);
		}
		else if(status == 3) //dunkler
		{
			int brightness = controllerHue.getLightBright();
			brightness -= 50;
			if(brightness < 25)
				brightness = 25;
			controllerHue.setLightBright(brightness);
		}
		else if(status == 4) //nacht
			controllerHue.setScene(Config.LIGHT_SCENES_NIGHT);
		else if(status == 5) //lesen
			controllerHue.setScene(Config.LIGHT_SCENES_READ);			
	}
	
	private void setHueColor() {
		if(color == -1) //Keine Änderung
			return;	
		else if(color == 0) //weiß
			controllerHue.setColor(255, 255, 255);	
		else if(color == 1) //grün
			controllerHue.setColor(0, 255, 0);	
		else if(color == 2) //rot
			controllerHue.setColor(255, 0, 0);	
		else if(color == 3) //blau
			controllerHue.setColor(0, 0, 255);	
		else if(color == 4) //gelb
			controllerHue.setColor(255, 255, 0);		
		else if(color == 5) //orange
			controllerHue.setColor(255, 128, 0);	
	}
	
	private void activateHueLight() {
		if(controllerHue.checkBridgeStatus()) {
			controllerHue.loadLights();
			controllerHue.printAllLights();
			controllerHue.activateLight(name);
		}
		else
			System.out.println("Bridge Status-Check failed!");
	}

}
