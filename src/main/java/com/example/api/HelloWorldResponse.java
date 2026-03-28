package com.example.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HelloWorldResponse {
    private String content;

    public HelloWorldResponse() {
        // Jackson deserialization
    }

    public HelloWorldResponse(String content) {
        this.content = content;
    }

    @JsonProperty
    public String getContent() {
        return content;
    }
}
