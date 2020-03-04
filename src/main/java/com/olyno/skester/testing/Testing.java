package com.olyno.skester.testing;

import java.util.ArrayList;

public class Testing {

    private String testName;
    private int testId;
    private ArrayList<TestingAssert> asserts = new ArrayList<>();
    private Boolean isFailed = false;

    public Testing(String testName) {
        this.testName = testName;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public void assertEquals(Object firstValue, Object secondValue) {
        if (firstValue instanceof String) {
            if (!firstValue.equals(secondValue)) this.fail(secondValue, firstValue);
            this.pass(secondValue, firstValue);
        } else if (firstValue != secondValue) {
            this.fail(secondValue, firstValue);
        } else {
            this.pass(secondValue, firstValue);
        }
    }

    public void assertEquals(Object firstValue, Object secondValue, String message) {
        if (firstValue instanceof String) {
            if (!firstValue.equals(secondValue)) this.fail(secondValue, firstValue, message);
            else this.pass(secondValue, firstValue, message);
        } else if (firstValue != secondValue) {
            this.fail(secondValue, firstValue, message);
        } else {
            this.pass(secondValue, firstValue, message);
        }
    }

    public void assertNotEquals(Object firstValue, Object secondValue) {
        if (firstValue instanceof String) {
            if (firstValue.equals(secondValue)) this.fail(secondValue, firstValue);
            else this.pass(secondValue, firstValue);
        } else if (firstValue == secondValue) {
            this.fail(secondValue, firstValue);
        } else {
            this.pass(secondValue, firstValue);
        }
    }

    public void assertNotEquals(Object firstValue, Object secondValue, String message) {
        if (firstValue instanceof String) {
            if (firstValue.equals(secondValue)) this.fail(secondValue, firstValue, message);
            else this.pass(secondValue, firstValue, message);
        } else if (firstValue == secondValue) {
            this.fail(secondValue, firstValue, message);
        } else {
            this.pass(secondValue, firstValue, message);
        }
    }

    public void assertTrue(Boolean firstValue) {
        if (!firstValue) this.fail("true", firstValue);
        else this.pass("true", firstValue);
    }

    public void assertTrue(Boolean firstValue, String message) {
        if (!firstValue) this.fail("true", firstValue, message);
        else this.pass("true", firstValue, message);
    }

    public void assertFalse(Boolean firstValue) {
        if (firstValue) this.fail("false", firstValue);
        else this.pass("false", firstValue);
    }

    public void assertFalse(Boolean firstValue, String message) {
        if (firstValue) this.fail("false", firstValue, message);
        else this.pass("false", firstValue, message);
    }

    public void assertNull(Object firstValue) {
        if (firstValue != null) this.fail("null", firstValue);
        else this.pass("null", firstValue);
    }

    public void assertNull(Object firstValue, String message) {
        if (firstValue != null) this.fail("null", firstValue, message);
        else this.pass("null", firstValue, message);
    }
    public void assertNotNull(Object firstValue) {
        if (firstValue == null) this.fail("not null", "null");
        else this.pass("not null", "null");
    }

    public void assertNotNull(Object firstValue, String message) {
        if (firstValue == null) this.fail("not null", "null", message);
        else this.pass("not null", "null", message);
    }

    public void pass() {
        this.asserts.add(new TestingAssert(false));
    }

    public void pass(String message) {
        this.asserts.add(new TestingAssert(message, false));
    }

    public void pass(Object output, Object input) {
        this.asserts.add(new TestingAssert(input, output, false));
    }

    public void pass(Object output, Object input, String message) {
        this.asserts.add(new TestingAssert(input, output, message, false));
    }    

    public void fail() {
        this.isFailed = true;
        this.asserts.add(new TestingAssert(true));
    }

    public void fail(String message) {
        this.isFailed = true;
        this.asserts.add(new TestingAssert(message, true));
    }

    public void fail(Object output, Object input) {
        this.isFailed = true;
        this.asserts.add(new TestingAssert(input, output, true));
    }

    public void fail(Object output, Object input, String message) {
        this.isFailed = true;
        this.asserts.add(new TestingAssert(input, output, message, true));
    }

    public void setTestId(int testId) {
        this.testId = testId;
    }

    public int getTestId() {
        return testId;
    }

    public Boolean isFailed() {
        return isFailed;
    }

    public ArrayList<TestingAssert> getAsserts() {
        return asserts;
    }

}
