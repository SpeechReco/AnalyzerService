package org.speechreco.analyzerservice;

import org.speechreco.analyzerservice.dao.RecordingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//docker run -it --rm --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3.13-management
@SpringBootApplication()
public class AnalyzerServiceApplication {

    public static void main(String[] args) {

        SpringApplication.run(AnalyzerServiceApplication.class, args);
    }

}
