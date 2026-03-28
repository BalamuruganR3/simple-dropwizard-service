package com.example;

import com.example.resources.HelloWorldResource;
import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Environment;

public class HelloWorldApplication extends Application<HelloWorldConfiguration> {

    public static void main(String[] args) throws Exception {
        new HelloWorldApplication().run(args);
    }

    @Override
    public void run(HelloWorldConfiguration configuration, Environment environment) {
        environment.jersey().register(new HelloWorldResource());
    }
}
