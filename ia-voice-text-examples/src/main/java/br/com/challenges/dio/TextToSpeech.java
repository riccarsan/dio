package br.com.challenges.dio;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.microsoft.cognitiveservices.speech.ResultReason;
import com.microsoft.cognitiveservices.speech.SpeechConfig;
import com.microsoft.cognitiveservices.speech.SpeechSynthesisResult;
import com.microsoft.cognitiveservices.speech.SpeechSynthesizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Controller
public class TextToSpeech {
    private static final String speechKey = "";
    private static final String serviceRegion = "eastus";
    private static final String endpoint_translate = "https://tradutor-aula-val-ai102.cognitiveservices.azure.com";
    private static final String translate_Key = "";

    private static final Logger log = LoggerFactory.getLogger(TextToSpeech.class);
    private static final Gson gson = new Gson();

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/speak")
    public String convertToSpeech(@RequestParam("text") String text, Model model) {
        try {
            // -----------------------------
            // 1️⃣ Síntese de fala
            // -----------------------------
            SpeechConfig config = SpeechConfig.fromSubscription(speechKey, serviceRegion);
            config.setSpeechSynthesisVoiceName("en-US-JennyNeural");

            SpeechSynthesizer synthesizer = new SpeechSynthesizer(config);
            SpeechSynthesisResult result = synthesizer.SpeakText(text);

            if (result.getReason() == ResultReason.SynthesizingAudioCompleted) {
                model.addAttribute("message", "Texto convertido em fala com sucesso!");
            } else {
                model.addAttribute("message", "Erro na síntese: " + result.getReason());
            }

            synthesizer.close();

            // -----------------------------
            // 2️⃣ Tradução usando mesmo recurso
            // -----------------------------
            String translatedText = translateText(text, "en", "pt");
            model.addAttribute("translation", translatedText);

        } catch (Exception e) {
            log.error("Ocorreu um erro", e);
            model.addAttribute("message", "Erro interno: " + e.getMessage());
        }

        return "index";
    }

    private String translateText(String text, String fromLang, String toLang) {
        try {
            log.debug("Iniciando tradução do texto: {}", text);
            HttpClient client = HttpClient.newHttpClient();
            String url = endpoint_translate + "/translator/text/v3.0/translate?from=" + fromLang + "&to=" + toLang;

            String body = "[{\"Text\":\"" + text + "\"}]";

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Ocp-Apim-Subscription-Key", translate_Key)
                    .header("Ocp-Apim-Subscription-Region", serviceRegion)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();


            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            log.debug("Status code da tradução: {}", response.statusCode());
            log.debug("Resposta do serviço de tradução: {}", response.body());

            // A resposta é JSON, exemplo: [{"translations":[{"text":"Oi Jude, não faça isso ruim...","to":"pt"}]}]
            // Vamos extrair o texto traduzido de forma simples (regex ou biblioteca JSON)
            // Aqui vai um parse simples Gson
            JsonArray jsonArray = gson.fromJson(response.body(), JsonArray.class);
            if (jsonArray.size() > 0) {
                JsonObject firstObj = jsonArray.get(0).getAsJsonObject();
                JsonArray translations = firstObj.getAsJsonArray("translations");
                if (translations != null && translations.size() > 0) {
                    String translatedText = translations.get(0).getAsJsonObject().get("text").getAsString();
                    log.debug("Texto traduzido: {}", translatedText);
                    return translatedText;
                }
            }

        } catch (Exception e) {
            log.error("Não foi possível extrair o texto traduzido do JSON",e);
        }
        return "Erro ao traduzir";
    }
}
