package app.com.hue;

import java.util.ArrayList;
import java.util.List;


import com.philips.lighting.hue.sdk.wrapper.domain.DomainType;
import com.philips.lighting.hue.sdk.wrapper.domain.device.sensor.Sensor;
import com.philips.lighting.hue.sdk.wrapper.domain.device.sensor.CompoundSensor;
import com.philips.lighting.hue.sdk.wrapper.domain.device.sensor.presence.PresenceSensor;
import com.philips.lighting.hue.sdk.wrapper.domain.device.sensor.environment.LightLevelSensor;
import com.philips.lighting.hue.sdk.wrapper.domain.device.sensor.environment.TemperatureSensor;


public class SensorHandler {

	private List<Sensor> allSensors;
	private CompoundSensor sensorComp;
	private TemperatureSensor sensorTemp;
	private PresenceSensor sensorPres;
	private LightLevelSensor sensorLight;

	public SensorHandler(List<Sensor> sensors) {
		allSensors = sensors;
	}
	
	public List<String> getAllSensorNames() {
		List<String> sensorNames = new ArrayList<String>();
		for (Sensor sensor : allSensors) {
			if(sensor.getType() == DomainType.PRESENCE_LIGHT_LEVEL_TEMPERATURE_SENSOR)
					sensorNames.add(sensor.getName());
		}
		return sensorNames;
	}
	
	public String loadSensor(String name) {
		for (Sensor item : allSensors) {
			if(item.getName().equals(name)) {
				sensorComp = (CompoundSensor) item;
				System.out.println("Sensor loaded!\nName: " + sensorComp.getName() +"\nID: " + sensorComp.getIdentifier());
				return sensorComp.getIdentifier();
			}
		}
		return null;
	}
	
	public void setSensor(CompoundSensor sensor) {
		sensorComp = sensor;
	}
	
	public void loadSubSensors() {
		sensorTemp = (TemperatureSensor) sensorComp.getDevices(DomainType.TEMPERATURE_SENSOR).get(0);
		sensorLight = (LightLevelSensor) sensorComp.getDevices(DomainType.LIGHT_LEVEL_SENSOR).get(0);
		sensorPres = (PresenceSensor) sensorComp.getDevices(DomainType.PRESENCE_SENSOR).get(0);
		double temp = ((double)sensorTemp.getState().getTemperature())/100;
		System.out.println("Temperatur: " + temp + "°C");
		System.out.println("LightLevel: " + sensorLight.getState().getLightLevel());
		System.out.println("Presence: " + sensorPres.getState().getPresence().toString());
		System.out.println("Presence Updated: " + sensorPres.getState().getLastUpdated());
	}
}
