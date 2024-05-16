package org.speechreco.analyzerservice.service;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.speechreco.analyzerservice.config.RabbitMQConfiguration;
import org.speechreco.analyzerservice.model.Analysis;
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
            analysisService.save(response);
        } catch (JsonSyntaxException e) {
            System.out.println("Error occurred, packet omitted\nPacket data: " + message);
        }


    }

}
