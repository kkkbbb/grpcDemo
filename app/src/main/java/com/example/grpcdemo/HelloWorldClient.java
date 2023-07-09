package com.example.grpcdemo;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;

public class HelloWorldClient {

    private final GreeterGrpc.GreeterBlockingStub greeterStub;

    public HelloWorldClient(String host, int port) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build();
        greeterStub = GreeterGrpc.newBlockingStub(channel);
    }

    public void greet(String name) {
        hellworld.HelloRequest request = hellworld.HelloRequest.newBuilder().setName(name).build();
        hellworld.HelloReply response;
        try {
            response = greeterStub.sayHello(request);
        } catch (StatusRuntimeException e) {
            return;
        }
        System.out.println("Greeting: " + response.getMessage());
    }
}
