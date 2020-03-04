package com.olyno.skester.testing;

public class TestingAssert {

    private Object input;
    private Object output;
    private String message;
    private Boolean isFailed = false;

    public TestingAssert(Boolean isFailed) {
        this.isFailed = isFailed;
    }

    public TestingAssert(String message, Boolean isFailed) {
        this.message = message;
        this.isFailed = isFailed;
    }

    public TestingAssert(Object input, Object output, Boolean isFailed) {
        this.input = input;
        this.output = output;
        this.isFailed = isFailed;
    }

    public TestingAssert(Object input, Object output, String message, Boolean isFailed) {
        this.input = input;
        this.output = output;
        this.message = message;
        this.isFailed = isFailed;
    }

    public void setOutput(Object output) {
        this.output = output;
    }

    public Object getOutput() {
        return output;
    }

    public void setInput(Object input) {
        this.input = input;
    }

    public Object getInput() {
        return input;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public Boolean isFailed() {
        return isFailed;
    }

}