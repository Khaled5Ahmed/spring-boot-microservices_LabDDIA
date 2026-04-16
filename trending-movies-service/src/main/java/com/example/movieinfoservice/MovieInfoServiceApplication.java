package com.example.movieinfoservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import io.grpc.Server;
import io.grpc.ServerBuilder;

@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker

public class MovieInfoServiceApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(MovieInfoServiceApplication.class, args);

        Server server = ServerBuilder
                .forPort(50051)
                .addService(new TrendingServiceImpl())
                .build();

        server.start();
        System.out.println("gRPC Server started on port 50051");

        server.awaitTermination();
    }
}