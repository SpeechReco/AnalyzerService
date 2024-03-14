package org.speechreco.analyzerservice;

import org.speechreco.analyzerservice.dao.RecordingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//git token: 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
@SpringBootApplication()
public class AnalyzerServiceApplication {

    public static void main(String[] args) {

        SpringApplication.run(AnalyzerServiceApplication.class, args);
    }

}
