package app.gui;

public final class Config {

	public static String NEWS_VIDEO_PATH = "./resources/news_in_100sec.mp4";
	public static String NEWS_AUDIO_PATH = "./resources/sounds/lucy_news.wav";  
	public static String MICRO_AUDIO_SAVE = "./resources/micro_save.pcm";  
	public static String MICRO_AUDIO_TOTEXT = "./resources/toText.pcm";  

	public static String SYSTEM_SOUND_TRIGGERED = "./resources/sounds/lucy_trigger.ogg";  
	public static String SYSTEM_SOUND_ERROR = "./resources/sounds/lucy_err.ogg";  
	public static String SYSTEM_HUE_PERSISTENCE = "C:/Users/Tobi/Desktop/LUCY/APIs/HueSDK/1.21/HueSDK/Windows";  
	public static String SYSTEM_ROOM = "schlafzimmer";
	
	public static String TTS_LOGIN_ACC = "59ff7b268da85";
	public static String TTS_LOGIN_PWD = "uSCYDryNJP";    
	public static String TTS_VOICE_SPEAKER = "Gudrun";    
	
	public static double SPEECH_SENTENCE_DURATION = 1.00;
	public static double SPEECH_dBSPL_THRESHOLD = -71;
	public static double SPEECH_SENTENCE_PAUSE = 0.66;


	public static String LIGHT_SCENES_NIGHT = "gmFVss-JNKp5NIz";    
	public static String LIGHT_SCENES_READ = "2jHASKorsPpTUen";     
	public static String LIGHT_SCENES_SLEEP = "jJpU6fI6F7iGKTY";     
	public static String LIGHT_SCENES_DAY = "SRCu4sjz1uhM9zN";    
	
	public static String LIGHTS_TYPE_HUE_WHITE = "HUE_WHITE";
	public static String LIGHTS_TYPE_HUE_COLOR = "HUE_COLOR";
	
	public static String[] LIGHTS_NAMES = {"schlafzimmer_bett"};
	public static String[] LIGHTS_TYPES = {LIGHTS_TYPE_HUE_COLOR};
	
	public static String SENSORS_HUE_SZ_TRIGGER = "00:17:88:01:02:11:2b:7a";    	

}
