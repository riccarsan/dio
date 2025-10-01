package br.com.challenges.dio;

import com.microsoft.cognitiveservices.speech.ResultReason;
import com.microsoft.cognitiveservices.speech.SpeechConfig;
import com.microsoft.cognitiveservices.speech.SpeechRecognitionResult;
import com.microsoft.cognitiveservices.speech.SpeechRecognizer;
import com.microsoft.cognitiveservices.speech.audio.AudioConfig;

public class SpeechToText {
    public static void main(String[] args) {
        try {
            String speechKey = "7DprcMDfne2xHS73ezOjCGWI89WPCDv9ZRq0OJFvC4gCDTynlCm6JQQJ99BIACYeBjFXJ3w3AAAYACOGij3b";
            String serviceRegion = "eastus";

            SpeechConfig config = SpeechConfig.fromSubscription(speechKey, serviceRegion);
            AudioConfig audioConfig = AudioConfig.fromDefaultMicrophoneInput();

            SpeechRecognizer recognizer = new SpeechRecognizer(config, audioConfig);

            System.out.println("Fale algo...");
            SpeechRecognitionResult result = recognizer.recognizeOnceAsync().get();

            if (result.getReason() == ResultReason.RecognizedSpeech) {
                System.out.println("Você disse: " + result.getText());
            } else {
                System.out.println("Erro: " + result.toString());
            }

            result.close();
            recognizer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
