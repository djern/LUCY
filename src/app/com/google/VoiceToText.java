//package app.com.google;
//
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.List;
//
//import com.google.cloud.speech.v1beta1.RecognitionAudio;
//import com.google.cloud.speech.v1beta1.RecognitionConfig;
//import com.google.cloud.speech.v1beta1.RecognitionConfig.AudioEncoding;
//import com.google.cloud.speech.v1beta1.SpeechClient;
//import com.google.cloud.speech.v1beta1.SpeechRecognitionAlternative;
//import com.google.cloud.speech.v1beta1.SpeechRecognitionResult;
//import com.google.protobuf.ByteString;
//
//import app.com.hardware.MicroHandler;
//
//public class VoiceToText {
//	
//	static MicroHandler microHandler;
//	static SpeechClient speech;
//	
//	public static void main(String[] args) throws Exception {
//		microHandler = new MicroHandler();
//		speech = SpeechClient.create();
//	}
//	
//	public void closeSpeech() throws Exception {
//		  speech.close();
//	}
//	
//	public static void syncRecognizeFile(String fileName) throws Exception, IOException {
//	    SpeechClient speech = SpeechClient.create();
//
//	    Path path = Paths.get(fileName);
//	    byte[] data = Files.readAllBytes(path);
//	    ByteString audioBytes = ByteString.copyFrom(data);
//
//	    // Configure request with local raw PCM audio
//	    RecognitionConfig config = RecognitionConfig.newBuilder()
//	        .setEncoding(AudioEncoding.LINEAR16)
//	        .setLanguageCode("en-US")
//	        .setSampleRate(16000)
//	        .build();
//	    RecognitionAudio audio = RecognitionAudio.newBuilder()
//	        .setContent(audioBytes)
//	        .build();
//
//	    // Use blocking call to get audio transcript
//	    RecognizeResponse response = speech.recognize(config, audio);
//	    List<SpeechRecognitionResult> results = response.getResultsList();
//
//	    for (SpeechRecognitionResult result: results) {
//	      // There can be several alternative transcripts for a given chunk of speech. Just use the
//	      // first (most likely) one here.
//	      SpeechRecognitionAlternative alternative = result.getAlternativesList().get(0);
//	      System.out.printf("Transcription: %s%n", alternative.getTranscript());
//	    }
//	    speech.close();
//	  }
//}