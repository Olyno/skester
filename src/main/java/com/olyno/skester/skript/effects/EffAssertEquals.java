package com.olyno.skester.skript.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.olyno.skester.util.effects.AssertEffect;
import org.bukkit.event.Event;

@Name("Assert Equals")
@Description("Checks if a value is equal to another. Fails if it is not.")
@Examples({
    "command checkConsole:\n" +
        "\ttrigger:\n" +
        "\t\tit \"should be equal\":\n" +
        "\t\t\tassert equals sender to console with message \"This sender is not the console!\""
})
@Since("1.0.0")

public class EffAssertEquals extends AssertEffect {

    static {
        Skript.registerEffect(EffAssertEquals.class,
            "assert [is] equal[s] %object% to %object% [with [[error] message] %-string%]",
            "assert [is] not equal[s] %object% to %object% [with [[error] message] %-string%]"
        );
    }

    private Expression<Object> firstValue;
    private Expression<Object> secondValue;
    private Expression<String> message;
    private boolean isNegated;

    @Override
    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        firstValue = (Expression<Object>) expr[0];
        secondValue = (Expression<Object>) expr[1];
        message = (Expression<String>) expr[2];
        isNegated = matchedPattern == 1;
        return true;
    }

    @Override
    protected void execute(Event e) {
        if (isAvailable()) {
            String msg = message != null ? message.getSingle(e) : "";
            if (isNegated) {
                if (!msg.isEmpty()) {
                    latestTest.assertNotEquals(firstValue.getSingle(e), secondValue.getSingle(e), msg);
                } else {
                    latestTest.assertNotEquals(firstValue.getSingle(e), secondValue.getSingle(e));
                }
            } else {
                if (!msg.isEmpty()) {
                    latestTest.assertEquals(firstValue.getSingle(e), secondValue.getSingle(e), msg);
                } else {
                    latestTest.assertEquals(firstValue.getSingle(e), secondValue.getSingle(e));
                }
            }
        }
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "assert" + (isNegated ? " not" : "") + " equals " + firstValue.toString(e, debug) + " to " + secondValue.toString(e, debug) + (message != null ? " with message " + message.toString(e, debug) : "");
    }
}
