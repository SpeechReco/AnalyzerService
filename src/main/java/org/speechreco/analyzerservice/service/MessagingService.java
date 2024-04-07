package org.speechreco.analyzerservice.service;

import com.google.gson.Gson;
import org.speechreco.analyzerservice.config.RabbitMQConfiguration;
import org.speechreco.analyzerservice.model.Analysis;
import org.speechreco.analyzerservice.model.Recording;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MessagingService {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private AnalysisService analysisService;

    public boolean send(Recording recording) {
        try {
            rabbitTemplate.convertAndSend(RabbitMQConfiguration.MESSAGE_QUEUE, recording.toJson());
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @RabbitListener(queues = "stt-response")
    public void processQuery(String message) {
        Analysis response = new Gson().fromJson(message, Analysis.class);
        analysisService.save(response);

    }

}
