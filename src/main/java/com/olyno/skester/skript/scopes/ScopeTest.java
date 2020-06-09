package com.olyno.skester.skript.scopes;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.olyno.skester.testing.Testing;
import com.olyno.skester.testing.TestingAssert;
import com.olyno.skester.util.scope.EffectSection;
import com.vdurmont.emoji.EmojiParser;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.Event;

import java.util.LinkedList;

@Name("Scope Test")
@Description("Create a test. The string is the name of the test.")
@Examples({ "command checkNull:\n" + "\ttrigger:\n" + "\t\tit \"should be null\":\n" + "\t\t\tbroadcast \"Awesome!\"" })
@Since("1.0.0")

public class ScopeTest extends EffectSection {

    static {
        Skript.registerCondition(ScopeTest.class, "it %string%");
    }

    public static Testing latestTest;
    public static LinkedList<Testing> tests = new LinkedList<>();

    private Expression<String> testName;

    @Override
    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean isDelayed,
            SkriptParser.ParseResult parseResult) {
        if (checkIfCondition())
            return false;
        if (!hasSection())
            return false;
        testName = (Expression<String>) expr[0];
        loadSection(true);
        return true;
    }

    @Override
    protected void execute(Event e) {
        latestTest = new Testing(testName.getSingle(e));
        latestTest.setTestId(tests.size());
        tests.add(latestTest);
        runSection(e);
        for (Testing test : tests) {
            Bukkit.getConsoleSender().sendMessage(
                EmojiParser.parseToUnicode(
                    test.isFailed() ?
                        ChatColor.RED + (test.getTestId() == 0 ? "" : repeat( "\t", test.getTestId() )) + ":x: " + test.getTestName()
                    :
                        ChatColor.GREEN + (test.getTestId() == 0 ? "" : repeat( "\t", test.getTestId() )) + ":white_check_mark: " + test.getTestName()
                )
            );
            for (TestingAssert testingAssert : test.getAsserts()) {
                if (testingAssert.isFailed()) {
                    Bukkit.getConsoleSender().sendMessage(
                        EmojiParser.parseToUnicode(
                            ChatColor.RED + repeat( "\t", test.getTestId() + 1 ) + "|- :x: " + (testingAssert.getMessage() != null ? testingAssert.getMessage() : "<Testing>")
                        )
                    );
                    Bukkit.getConsoleSender().sendMessage(
                        EmojiParser.parseToUnicode(
                            ChatColor.RED + repeat( "\t", test.getTestId() + 2 ) + "==> Excepted \"" + testingAssert.getOutput() + "\", got \"" + testingAssert.getInput() + "\""
                        )
                    );
                } else {
                    Bukkit.getConsoleSender().sendMessage(
                        EmojiParser.parseToUnicode(
                            ChatColor.GREEN + repeat( "\t", test.getTestId() + 1 ) + "|- :white_check_mark: " + testingAssert.getMessage()
                        )
                    );
                }
            }
        }
        tests.clear();
    }

    private String repeat(String value, int amount) {
        return new String(new char[amount]).replace("\0", value);
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "it " + testName.toString(e, debug);
    }

}
