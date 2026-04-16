package com.moviecatalogservice.resources;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import trending.*;

public class TrendingServiceClient {

    public MovieList getTopMovies() {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 50051)
                .usePlaintext()
                .build();

        TrendingServiceGrpc.TrendingServiceBlockingStub stub =
                TrendingServiceGrpc.newBlockingStub(channel);

        MovieList response = stub.getTopMovies(Empty.newBuilder().build());

        channel.shutdown();

        return response;
    }
}