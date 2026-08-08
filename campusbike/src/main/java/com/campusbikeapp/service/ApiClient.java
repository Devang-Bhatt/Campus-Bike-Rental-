package com.campusbikeapp.service;

import com.google.gson.Gson;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Web equivalent of Android ApiClient.java
 * Uses OkHttp directly (same library concept as Retrofit under the hood)
 * Points to the same Spring Boot backend: http://localhost:8080
 */
@Service
public class ApiClient {

    @Value("${app.api.base-url:http://localhost:8080}")
    private String baseUrl;

    private final OkHttpClient client;
    private final Gson gson = new Gson();

    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    public ApiClient() {
        this.client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();
    }

    /**
     * POST request with JSON body
     */
    public String post(String endpoint, Object body) throws IOException {
        String json = gson.toJson(body);
        RequestBody requestBody = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                .url(baseUrl + endpoint)
                .post(requestBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body() != null ? response.body().string() : "";
        }
    }

    /**
     * POST request with Authorization header
     */
    public String postAuth(String endpoint, Object body, String token) throws IOException {
        String json = gson.toJson(body);
        RequestBody requestBody = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                .url(baseUrl + endpoint)
                .post(requestBody)
                .header("Authorization", "Bearer " + token)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body() != null ? response.body().string() : "";
        }
    }

    /**
     * GET request with Authorization header
     */
    public String getAuth(String endpoint, String token) throws IOException {
        Request request = new Request.Builder()
                .url(baseUrl + endpoint)
                .get()
                .header("Authorization", "Bearer " + token)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body() != null ? response.body().string() : "";
        }
    }

    /**
     * GET request (no auth)
     */
    public String get(String endpoint) throws IOException {
        Request request = new Request.Builder()
                .url(baseUrl + endpoint)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body() != null ? response.body().string() : "";
        }
    }

    public Gson getGson() { return gson; }
}
