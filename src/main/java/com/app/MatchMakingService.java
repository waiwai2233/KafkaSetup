package com.app;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient;
import java.io.IOException;

public class MatchMakingService {

    private static final String kafkaProducerEndpoint = "http://127.0.0.1:9091/api/kafka/createTopic";

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
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: MatchMakingService <topicName>");
            System.exit(1);
        }
        String topicName = args[0];
        // invoke the api endpoint to create a topic
        HttpResponse<String> response = createTopic(topicName);
        System.out.println(response.body());
    }
}