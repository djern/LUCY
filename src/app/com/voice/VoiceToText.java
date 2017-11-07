package app.com.voice;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import com.google.cloud.speech.v1.RecognitionAudio;
import com.google.cloud.speech.v1.RecognitionConfig;
import com.google.cloud.speech.v1.RecognitionConfig.AudioEncoding;
import com.google.cloud.speech.v1.RecognizeResponse;
import com.google.cloud.speech.v1.SpeechClient;
import com.google.cloud.speech.v1.SpeechContext;
import com.google.cloud.speech.v1.SpeechRecognitionAlternative;
import com.google.cloud.speech.v1.SpeechRecognitionResult;
import com.google.protobuf.ByteString;

public class VoiceToText {
	
	static SpeechClient speech;
	
	public VoiceToText() throws IOException {
	}
	
	public void translate(String fileName) throws Exception, IOException {

		SpeechClient speech = SpeechClient.create();
		System.out.println("Translating...");
	    // Reads the audio file into memory
	    Path path = Paths.get(fileName);
	    byte[] data = Files.readAllBytes(path);
	    ByteString audioBytes = ByteString.copyFrom(data);

	    SpeechContext.Builder context = SpeechContext.newBuilder();
	    context.addPhrases("Lucy");
	    
	    // Builds the sync recognize request
	    RecognitionConfig config = RecognitionConfig.newBuilder()
	        .setEncoding(AudioEncoding.LINEAR16)
	        .setSampleRateHertz(16000)
	        .setLanguageCode("de-DE")
	        .addSpeechContexts(context)
	        .build();
	    RecognitionAudio audio = RecognitionAudio.newBuilder()
	        .setContent(audioBytes)
	        .build();

	    // Performs speech recognition on the audio file
	    RecognizeResponse response = speech.recognize(config, audio);
	    List<SpeechRecognitionResult> results = response.getResultsList();
	    System.out.println("Results found:" + results.size());
	    for (SpeechRecognitionResult result: results) {
	      // There can be several alternative transcripts for a given chunk of speech. Just use the
	      // first (most likely) one here.
	      SpeechRecognitionAlternative alternative = result.getAlternativesList().get(0);
	      System.out.printf("Transcription: %s%n", alternative.getTranscript());
	    }
	    speech.close();
	}
}