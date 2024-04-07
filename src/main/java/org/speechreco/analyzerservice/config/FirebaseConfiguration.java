package org.speechreco.analyzerservice.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.StorageClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;

@Configuration
public class FirebaseConfiguration {
    @Bean
    public Bucket storageBucket() throws IOException {
        FileInputStream serviceAccount = new FileInputStream("src/main/resources/key/speechrec-b38d7-firebase-adminsdk-l42lb-98d4587256.json");

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setStorageBucket("speechrec-b38d7.appspot.com")
                .build();
        FirebaseApp.initializeApp(options);
        return StorageClient.getInstance().bucket();
    }
}
