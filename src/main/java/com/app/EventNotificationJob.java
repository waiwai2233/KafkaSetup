package com.app;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class EventNotificationJob implements Job {

    private static final String kafkaProducerEndpoint = "http://127.0.0.1:9091/api/kafka/createTopic";

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        // Logic to notify users
        String topicName = context.getJobDetail().getJobDataMap().getString("topicName");
        String jobName = context.getJobDetail().getKey().getName();
        System.out.println("Sending notifications for job: " + jobName + " on topic: " + topicName);
        HttpResponse<String> response = createTopic(topicName);
        System.out.println(response.body());
        // You can add more sophisticated notification logic here, like sending an email or SMS
    }

    public static HttpResponse<String> createTopic(String topicName) {
        // invoke the api endpoint to create a topic
        String urlString = kafkaProducerEndpoint + "?name=" + topicName;
        HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(urlString))
				.method("POST", HttpRequest.BodyPublishers.noBody())
				.build();
        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        }
        catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }
}
