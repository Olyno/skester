package com.olyno.skester.testing;

import com.olyno.skester.skript.scopes.ScopeTest;
import com.vdurmont.emoji.EmojiParser;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class Testing {

    private String testName;
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
        if (firstValue != secondValue) this.fail("true", "false");
    }

    public void assertEquals(Object firstValue, Object secondValue, String message) {
        if (firstValue != secondValue) this.fail("true", "false", message);
    }

    public void assertNotEquals(Object firstValue, Object secondValue) {
        if (firstValue == secondValue) this.fail("false", "true");
    }

    public void assertNotEquals(Object firstValue, Object secondValue, String message) {
        if (firstValue == secondValue) this.fail("false", "true", message);
    }

    public void assertTrue(Boolean firstValue) {
        if (!firstValue) this.fail("true", firstValue.toString());
    }

    public void assertTrue(Boolean firstValue, String message) {
        if (!firstValue) this.fail("true", firstValue.toString(), message);
    }

    public void assertFalse(Boolean firstValue) {
        if (firstValue) this.fail("false", firstValue.toString());
    }

    public void assertFalse(Boolean firstValue, String message) {
        if (firstValue) this.fail("false", firstValue.toString(), message);
    }

    public void assertNull(Object firstValue) {
        if (firstValue != null) this.fail("null", firstValue.toString());
    }

    public void assertNull(Object firstValue, String message) {
        if (firstValue != null) this.fail("null", firstValue.toString(), message);
    }
    public void assertNotNull(Object firstValue) {
        if (firstValue == null) this.fail("not null", "null");
    }

    public void assertNotNull(Object firstValue, String message) {
        if (firstValue == null) this.fail("not null", "null", message);
    }

    public void fail() {
        this.isFailed = true;
        Bukkit.getConsoleSender().sendMessage(
                EmojiParser.parseToUnicode(ChatColor.RED + ":x: " + this.testName)
        );
    }

    public void fail(String message) {
        this.isFailed = true;
        Bukkit.getConsoleSender().sendMessage(
                EmojiParser.parseToUnicode(ChatColor.RED + (ScopeTest.tests.get(0) == this ? "" : "\t|- ") + ":x: " + this.testName)
        );
        Bukkit.getConsoleSender().sendMessage(
                EmojiParser.parseToUnicode(ChatColor.RED + "\t|- " + message)
        );
    }

    public void fail(String excepted, String got) {
        this.isFailed = true;
        Bukkit.getConsoleSender().sendMessage(
                EmojiParser.parseToUnicode(ChatColor.RED + (ScopeTest.tests.get(0) == this ? "" : "\t|- ") + ":x: " + this.testName)
        );
        Bukkit.getConsoleSender().sendMessage(
                EmojiParser.parseToUnicode(
                        ChatColor.RED + "\t|- Excepted \"" + excepted + "\", got \"" + got + "\""
                )
        );
    }

    public void fail(String excepted, String got, String message) {
        this.isFailed = true;
        Bukkit.getConsoleSender().sendMessage(
                EmojiParser.parseToUnicode(ChatColor.RED + (ScopeTest.tests.get(0) == this ? "" : "\t|- ") + ":x: " + this.testName)
        );
        Bukkit.getConsoleSender().sendMessage(
                EmojiParser.parseToUnicode(ChatColor.RED + (ScopeTest.tests.get(0) == this ? "" : "\t\t|- ") + ":x: " + message)
        );
        Bukkit.getConsoleSender().sendMessage(
                EmojiParser.parseToUnicode(ChatColor.RED + "\t\t\t|- Excepted \"" + excepted + "\", got \"" + got + "\"")
        );
    }

    public void pass() {
        Bukkit.getConsoleSender().sendMessage(
                EmojiParser.parseToUnicode(ChatColor.GREEN + (ScopeTest.tests.get(0) == this ? "" : "\t|- ") + ":white_check_mark: " + this.testName)
        );
    }

    public void pass(String message) {
        Bukkit.getConsoleSender().sendMessage(
                EmojiParser.parseToUnicode(ChatColor.GREEN + (ScopeTest.tests.get(0) == this ? "" : "\t|- ") + ":white_check_mark: " + message)
        );
    }

    public Boolean getFailed() {
        return isFailed;
    }

}
