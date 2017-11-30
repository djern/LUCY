package app.com.hue;

import java.util.ArrayList;
import java.util.List;

import com.philips.lighting.hue.sdk.wrapper.domain.device.light.LightPoint;
import com.philips.lighting.hue.sdk.wrapper.domain.device.light.LightState;
import com.philips.lighting.hue.sdk.wrapper.utilities.HueColor;
import com.philips.lighting.hue.sdk.wrapper.utilities.HueColor.RGB;

public class LightHandler {

	private List<LightPoint> allLights;
	private LightPoint light;

	public LightHandler(List<LightPoint> lights) {
		allLights = lights;
	}
	
	public List<String> getAllLightNames() {
		List<String> lightNames = new ArrayList<String>();
		for (LightPoint light : allLights) {
			lightNames.add(light.getName());
		}
		return lightNames;
	}
	
	public String loadLight(String name) {
		for (LightPoint lightPoint : allLights) {
			if(lightPoint.getName().equals(name)) {
				light = lightPoint;
				System.out.println("Light loaded!\nName: " + light.getName() +"\nID: " + light.getIdentifier());
				return light.getIdentifier();
			}
		}
		return null;
	}
	
	public void setLight(LightPoint light) {
		this.light = light;
	}
	
	public void getType() {
		System.out.println(light.getLightType());
	}
	
	public boolean getStatus() {
		return light.getLightState().isOn();
	}

	public void setStatus(boolean isOn) {
		LightState state =  light.getLightState();
		state.setOn(isOn);
		light.updateState(state);
	}

	public int getBrightness() {
		return light.getLightState().getBrightness();
	}

	public void setBrightness(int bright) {
		LightState state =  light.getLightState();
		state.setBrightness(bright);
		light.updateState(state);
	}
	
	public void setColor(int r, int g, int b) {
		RGB rgb = new RGB(r, g, b);
		HueColor color = new HueColor (rgb, light.getConfiguration().getModelIdentifier(), light.getConfiguration().getSwVersion());
		LightState state =  light.getLightState();
		state.setXYWithColor(color);
		light.updateState(state);
	}
}
