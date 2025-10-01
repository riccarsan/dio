package br.com.challenges;

import com.microsoft.cognitiveservices.speech.*;
import com.microsoft.cognitiveservices.speech.audio.*;

//
public class SpeechFlowExample {

    private static final String speechKey = "7DprcMDfne2xHS73ezOjCGWI89WPCDv9ZRq0OJFvC4gCDTynlCm6JQQJ99BIACYeBjFXJ3w3AAAYACOGij3b";
    private static final String serviceRegion = "eastus";

    public static void main(String[] args) {
        try {
            // Configuração inicial
            SpeechConfig config = SpeechConfig.fromSubscription(speechKey, serviceRegion);

            // 🎤 1. Capturar fala e converter em texto
            AudioConfig audioInput = AudioConfig.fromDefaultMicrophoneInput();
            SpeechRecognizer recognizer = new SpeechRecognizer(config, audioInput);

            System.out.println("Fale algo no microfone...");
            SpeechRecognitionResult speechResult = recognizer.recognizeOnceAsync().get();

            if (speechResult.getReason() == ResultReason.RecognizedSpeech) {
                String recognizedText = speechResult.getText();
                System.out.println("Você disse: " + recognizedText);

                // 🔊 2. Converter texto em fala (TTS)
                config.setSpeechSynthesisVoiceName("en-US-JennyNeural"); // escolha de voz neural
                SpeechSynthesizer synthesizer = new SpeechSynthesizer(config);

                SpeechSynthesisResult ttsResult = synthesizer.SpeakTextAsync(recognizedText).get();

                if (ttsResult.getReason() == ResultReason.SynthesizingAudioCompleted) {
                    System.out.println("Texto convertido em fala com sucesso!");
                } else {
                    System.err.println("Erro na síntese: " + ttsResult.getReason());
                }

                synthesizer.close();
            } else {
                System.err.println("Não foi possível reconhecer a fala: " + speechResult.toString());
            }

            recognizer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
