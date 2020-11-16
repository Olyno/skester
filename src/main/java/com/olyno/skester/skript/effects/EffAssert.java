package com.olyno.skester.skript.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.olyno.skester.util.effects.AssertEffect;
import org.bukkit.event.Event;

@Name("Assert Condition")
@Description("Checks if condition is respected. Fails if it is not.")
@Examples({
    "command check:\n" +
        "\ttrigger:\n" +
        "\t\tit \"should be work\":\n" +
        "\t\t\tassert if sender is console with message \"This sender is not the console!\""
})
@Since("1.0.0")

public class EffAssert extends AssertEffect {

    static {
        Skript.registerEffect(EffAssert.class,
            "assert if <.+> [with [[error] message] %-string%]"
        );
    }

    private String condition;
    private Expression<String> message;

    @Override
    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        condition = parseResult.regexes.get(0).group();
        message = (Expression<String>) expr[0];
        return true;
    }

    @Override
    protected void execute(Event e) {
        if (isAvailable()) {
            String msg = message != null ? message.getSingle(e) : "";
            if (msg.isEmpty()) {
                latestTest.assertTrue(Condition.parse(condition, "Can't understand this condition: " + condition).check(e));
            } else {
                latestTest.assertTrue(Condition.parse(condition, "Can't understand this condition: " + condition).check(e), msg);
            }
        }
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "assert if " + condition + (message != null ? " with message " + message.toString(e, debug) : "");
    }
}
