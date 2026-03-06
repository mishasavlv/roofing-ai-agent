package com.servicewhale.chatbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class ChatbotIntegrationSendPulseApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChatbotIntegrationSendPulseApplication.class, args);
	}

}
