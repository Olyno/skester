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
import com.olyno.skester.util.scope.EffectSection;
import org.bukkit.event.Event;

import java.util.LinkedList;

@Name("Scope Test")
@Description("Create a test. The string is the name of the test.")
@Examples({
        "command checkNull:\n" +
                "\ttrigger:\n" +
                "\t\tit \"should be null\":\n" +
                "\t\t\tbroadcast \"Awesome!\""
})
@Since("1.0.0")

public class ScopeTest extends EffectSection {

    static {
        Skript.registerCondition(ScopeTest.class,
                "it %string%"
        );
    }

    public static Testing latestTest;
    public static LinkedList<Testing> tests = new LinkedList<>();

    private Expression<String> testName;

    @Override
    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        if (checkIfCondition()) return false;
        if (!hasSection()) return false;
        testName = (Expression<String>) expr[0];
        loadSection(true);
        return true;
    }

    @Override
    protected void execute(Event e) {
        latestTest = new Testing(testName.getSingle(e));
        tests.add(latestTest);
        runSection(e);
        if (!latestTest.getFailed()) {
            latestTest.pass();
            tests.remove(latestTest);
            if (tests.size() > 0) {
                latestTest = tests.get(tests.size() - 1);
            }
        }
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "it " + testName.toString(e, debug);
    }

}
