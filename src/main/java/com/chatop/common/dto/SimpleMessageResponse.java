package com.chatop.common.dto;

import lombok.Getter;

@Getter
public class SimpleMessageResponse {

    private final String message;

    public SimpleMessageResponse(String message) {
        this.message = message;
    }
}
