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

@Name("Assert Null")
@Description("Checks if a value is null. Throw an error if it is not.")
@Examples({
        "command checkNull:\n" +
                "\ttrigger:\n" +
                "\t\tit \"should be null\":\n" +
                "\t\t\tassert null {_test} with message \"This variable is null!\""
})
@Since("1.0.0")

public class EffAssertNull extends AssertEffect {

    static {
        Skript.registerEffect(EffAssertNull.class,
                "assert [is] null %object% [with [[error] message] %-string%]",
                "assert [is] not null %object% [with [message] %-string%]"
        );
    }

    private Expression<Object> value;
    private Expression<String> message;
    private boolean isNegated;

    @Override
    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        value = (Expression<Object>) expr[0];
        message = (Expression<String>) expr[1];
        isNegated = matchedPattern == 1;
        return true;
    }

    @Override
    protected void execute(Event e) {
        if (isAvailable()) {
            String msg = message != null ? message.getSingle(e) : "";
            if (isNegated) {
                if (!msg.isEmpty()) {
                    latestTest.assertNotNull(value.getSingle(e), msg);
                } else {
                    latestTest.assertNotNull(value.getSingle(e));
                }
            } else {
                if (!msg.isEmpty()) {
                    latestTest.assertNull(value.getSingle(e), msg);
                } else {
                    latestTest.assertNull(value.getSingle(e));
                }
            }
        }
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "assert" + (isNegated ? " not" : "") + " null " + value.toString(e, debug) + (message != null ? " with message " + message.toString(e, debug) : "");
    }
}
