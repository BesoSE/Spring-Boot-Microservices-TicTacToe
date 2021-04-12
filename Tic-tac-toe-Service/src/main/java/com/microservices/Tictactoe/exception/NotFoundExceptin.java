package com.microservices.Tictactoe.exception;

public class NotFoundExceptin extends Exception{
    private  String message;

    public NotFoundExceptin(String message) {

        this.message = message;
    }


    public String getMessage() {
        return message;
    }

}
