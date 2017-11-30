package app.com.voice;

import javax.xml.ws.Holder;

import app.com.voice.cerevoice.CereVoiceCloud;
import app.gui.Config;

public class TextToVoice {
	OggPlayer player;
	
	public TextToVoice() {
	}
	
	public String getURLforText(String text) {
		try {
			 CereVoiceCloud cere = new CereVoiceCloud();
			 Holder<String> url = new Holder<String>();
			 Holder<Integer> char_count = new Holder<Integer>();
			 Holder<Integer> res_code = new Holder<Integer>();
			 Holder<String> res_des = new Holder<String>();
			 cere.getCereVoiceCloudPort().speakSimple(Config.TTS_LOGIN_ACC, Config.TTS_LOGIN_PWD, Config.TTS_VOICE_SPEAKER, text, url, char_count, res_code, res_des);
			 if(res_code.value != 1)
				 System.out.println("ERROR: request failed - " + res_des.value);
			 else
				 return url.value;
			 } 
		catch (Exception e) {
			 System.err.println(e.toString());
			 }
		return null;
	}
		
	public void playSound(String url) {
		OggPlayer player = new OggPlayer(url);
        player.start();
        while(player.isAlive()) {
        	
        }
	}
	
	public void runTTV(String text) {
		String url =  getURLforText(text);
//		System.out.println("URL: " + url);
		playSound(url);
	}
	
}
