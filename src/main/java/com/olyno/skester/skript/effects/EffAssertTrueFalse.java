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

@Name("Assert True/False")
@Description("Checks if a value is true/false. Fails if it is not.")
@Examples({
        "command checkTrue:\n" +
                "\ttrigger:\n" +
                "\t\tit \"should be true\":\n" +
                "\t\t\tassert true if sender is console with message \"The console is the sender!\""
})
@Since("1.0.0")

public class EffAssertTrueFalse extends AssertEffect {

    static {
        Skript.registerEffect(EffAssertTrueFalse.class,
                "assert [is] true [if] <.+> [with [[error] message] %-string%]",
                "assert [is] false [if] <.+> [with [[error] message] %-string%]"
        );
    }

    private String value;
    private Expression<String> message;
    private Boolean isFalse;

    @Override
    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        value = parseResult.regexes.get(0).group();
        message = (Expression<String>) expr[0];
        isFalse = matchedPattern == 1;
        return true;
    }

    @Override
    protected void execute(Event e) {
        if (isAvailable()) {
            String msg = message != null ? message.getSingle(e) : "";
            Boolean finalValue = value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false") ?
                    Boolean.parseBoolean(value)
                    : Condition.parse(value, "Can't understand this condition: " + value).check(e);
            if (isFalse) {
                if (msg.isEmpty()) {
                    latestTest.assertFalse(finalValue);
                } else {
                    latestTest.assertFalse(finalValue, msg);
                }
            } else {
                if (msg.isEmpty()) {
                    latestTest.assertTrue(finalValue);
                } else {
                    latestTest.assertTrue(finalValue, msg);
                }
            }
        }
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "assert " + (isFalse ? "false" : "true") + " " + value + (message != null ? " with message " + message.toString(e, debug) : "");
    }
}
