package org.speechreco.analyzerservice.service;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.speechreco.analyzerservice.config.RabbitMQConfiguration;
import org.speechreco.analyzerservice.controller.AnalysisController;
import org.speechreco.analyzerservice.model.Analysis;
import org.speechreco.analyzerservice.model.GPTMessagePacket;
import org.speechreco.analyzerservice.model.Recording;
import org.speechreco.analyzerservice.model.SttMessagePacket;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessagingService {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private AnalysisService analysisService;
    @Autowired
    private AnalysisController analysisController;

    public boolean send(String name, Recording recording, String language, int speakerAmount, boolean generateSummary) {
        try {
            if (name == null || name.isEmpty()) name = "Unnamed";
            SttMessagePacket messagePacket = new SttMessagePacket(name, language, speakerAmount, generateSummary, recording);
            rabbitTemplate.convertAndSend(RabbitMQConfiguration.MESSAGE_QUEUE, messagePacket.toJson());
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @RabbitListener(queues = "stt-response")
    public void processQuery(String message) {
        try {
            Analysis response = new Gson().fromJson(message, Analysis.class);
            if(!response.isEmpty())
                analysisService.save(response);
            else {
                GPTMessagePacket gptResponse = new Gson().fromJson(message, GPTMessagePacket.class);

                // Assuming AnalysisResponse contains analysisId and the result
                String result = gptResponse.getResponse();

                // Notify the controller about the result
                analysisController.completeAnalysis(1, result);
            }
        } catch (JsonSyntaxException e) {

            System.out.println("Error occurred, packet omitted\nPacket data: " + message);
        }


    }

    public boolean sendToGPT(int userId, int audioId, int analysisId, String text, String prompt) {
        try {
            GPTMessagePacket messagePacket = new GPTMessagePacket(userId, audioId, analysisId, text, prompt, "");
            rabbitTemplate.convertAndSend(RabbitMQConfiguration.GPT_QUEUE, messagePacket.toJson());
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
