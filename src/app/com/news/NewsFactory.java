package app.com.news;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import app.gui.Config; 

public final class NewsFactory {

	private static String link;

	public static void loadLatestNews() throws IOException {
		getLink();
		download(link, Config.NEWS_VIDEO_PATH);
		convertToAudio();
	}
	
	private static void convertToAudio(){ 
		System.out.println("Removing old news-file...");
		File file = new File(Config.NEWS_AUDIO_PATH);       
        if(file.exists())
            file.delete();
		System.out.println("Trying to convert....");
		try {
            String line;
            // ffmpeg -i input.mp4 output.avi as it's on www.ffmpeg.org
            String cmd = "ffmpeg -i " + Config.NEWS_VIDEO_PATH + " " + Config.NEWS_AUDIO_PATH;
            Process p = Runtime.getRuntime().exec(cmd);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(p.getErrorStream()));
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
            p.waitFor();
            System.out.println("Video converted successfully!");
            in.close();
        } catch (IOException | InterruptedException e) {
            System.out.println(e);
        } 
	}
	
	private static void getLink() {
		System.out.println("Getting Downloadlink...");
		RSSFeedParser parser = new RSSFeedParser("http://www.tagesschau.de/export/video-podcast/webm/tagesschau-in-100-sekunden/");
	    link = parser.readFeed();	
	}
	
	private static Path download(String sourceURL, String targetFile) throws IOException{
		System.out.println("Downloading Video...");
	    URL url = new URL(sourceURL);
//	    String fileName = sourceURL.substring(sourceURL.lastIndexOf('/') + 1, sourceURL.length());
	    Path targetPath = new File(targetFile).toPath(); //new File(targetDirectory + File.separator + fileName).toPath();
	    Files.copy(url.openStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

	    return targetPath;
	}
} 
