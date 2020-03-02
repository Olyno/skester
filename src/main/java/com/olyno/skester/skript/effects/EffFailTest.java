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

@Name("Fail Test")
@Description("Makes the test fail if a condition is respected (or just force to fail the test).")
@Examples({
        "command failTest:\n" +
                "\ttrigger:\n" +
                "\t\tit \"should be failed\":\n" +
                "\t\t\tfail test"
})
@Since("1.0.0")

public class EffFailTest extends AssertEffect {

    static {
        Skript.registerEffect(EffFailTest.class,
                "fail [the] test [with [[error] message] %-string%]",
                "fail [the] test [if] <.+> [with [[error] message] %-string%]"
        );
    }

    private String condition;
    private Expression<String> message;
    private Boolean forceFail;

    @Override
    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        message = (Expression<String>) expr[0];
        forceFail = matchedPattern == 0;
        condition = forceFail ? null : parseResult.regexes.get(0).group();
        return true;
    }

    @Override
    protected void execute(Event e) {
        if (isAvailable()) {
            String msg = message != null ? message.getSingle(e) : "";
            if (forceFail || condition != null && Condition.parse(condition, "Can't understand this condition: " + condition).check(e)) {
                if (msg.isEmpty()) {
                    latestTest.fail();
                } else {
                    latestTest.fail(msg);
                }
            }
        }
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "fail test " + (forceFail ? "" : condition) + (message != null ? " with message " + message.toString(e, debug) : "");
    }
}
