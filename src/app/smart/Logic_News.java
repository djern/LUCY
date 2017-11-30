package app.smart;

import java.io.IOException;

import app.com.news.NewsFactory;
import app.com.voice.MP3Player;
import app.gui.Config;

public final class Logic_News {
	
	public static void loadNews() {

		try {
			NewsFactory.loadLatestNews();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static MP3Player playNews() {
		MP3Player player = new MP3Player(Config.NEWS_AUDIO_PATH);
		player.playFile();
		return player;
	}

	public static void stopNews(MP3Player player) {
		player.stop();
	}

}
