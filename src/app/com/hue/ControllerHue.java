package app.com.hue;

import java.util.List;

import com.philips.lighting.hue.sdk.wrapper.HueLog;
import com.philips.lighting.hue.sdk.wrapper.Persistence;
import com.philips.lighting.hue.sdk.wrapper.domain.device.sensor.CompoundSensor;
import com.philips.lighting.hue.sdk.wrapper.domain.resource.Scene;

import app.gui.Config;

public class ControllerHue {

	public BridgeHandler handlerBridge;
	public LightHandler handlerLight;
	public SensorHandler handlerSensor;
	private String lightID;
	private String sensorID;
	
	public ControllerHue() {
		System.loadLibrary("huesdk");
		handlerBridge = new BridgeHandler();
		Persistence.setStorageLocation(Config.SYSTEM_HUE_PERSISTENCE, "Lucy");
        HueLog.setConsoleLogLevel(HueLog.LogLevel.INFO);
        connectBridge(getLastIP());
	}

	public String getLastIP() {
		return handlerBridge.getLastUsedBridgeIp();
	}
	
	public void startSearching() {
		handlerBridge.startBridgeDiscovery();
	}
	
	public void stopSearching() {
		handlerBridge.stopBridgeDiscovery();
	}

	public void connectBridge(String ip) {
		handlerBridge.connectToBridge(ip);
	}

	public void disconnectBridge() {
		handlerBridge.disconnectFromBridge();
	}

	public boolean checkBridgeStatus() {
		return handlerBridge.getStatus();
	}
	
	// ----------------- Start LIGHTS ----------------- //
	public void loadLights() {
		handlerBridge.loadBrigde();
		handlerLight = new LightHandler(handlerBridge.loadLights());
	}
	
	public void printAllLights() {
		List<String> lights = handlerLight.getAllLightNames();
		for (String name : lights) {
			System.out.println("Found light:" + name);
		}
	}
	
	public void activateLight(String name) {
		lightID = handlerLight.loadLight(name);
	}
	
	private void reloadLight() {
		handlerLight.setLight(handlerBridge.getLight(lightID));
	}
	
	public void getType() {
		handlerLight.getType();
	}

	public boolean getLightStatus() {
		reloadLight();
		return handlerLight.getStatus();
	}
	
	public void setLightStatus(boolean isOn) {
		handlerLight.setStatus(isOn);
	}
	
	public int getLightBright() {
		reloadLight();
		return handlerLight.getBrightness();
	}
	
	public void setLightBright(int bright) {
		handlerLight.setBrightness(bright);
	}

	public void setColor(int r, int g, int b) {
		handlerLight.setColor(r, g, b);		
	}
	
	// ------------------ End LIGHTS ------------------ //

	// ---------------- Start SENSORS ---------------- //
	public void loadSensors() {
		handlerBridge.loadBrigde();
		handlerSensor = new SensorHandler(handlerBridge.loadSensors());
	}
	
	public void printAllSensors() {
		List<String> sensors = handlerSensor.getAllSensorNames();
		for (String name : sensors) {
			System.out.println("Found sensor:" + name);
		}
	}
	
	public void activateSensor(String name) {
		sensorID = handlerSensor.loadSensor(name);
	}
	
	private void reloadSensor() {
		handlerSensor.setSensor((CompoundSensor)handlerBridge.getSensor(sensorID));
	}
	
	public void printSensor() {
		reloadSensor();
		handlerSensor.loadSubSensors();
	}
	// ----------------- End SENSORS ----------------- //

	// ---------------- Start SCENES ---------------- //
	
	public void printAllScenes() {
		List <Scene> allScenes = handlerBridge.loadScenes();
		for (Scene scene : allScenes) {
			System.out.print("Name: " + scene.getName());
			System.out.println(" - Ident: " + scene.getIdentifier());
		}
	}
	
	public void setScene(String ident) {
		handlerBridge.setScene(ident);
	}
}
