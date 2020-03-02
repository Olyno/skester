package com.olyno.skester.util.effects;

import ch.njol.skript.lang.Effect;
import com.olyno.skester.skript.scopes.ScopeTest;
import com.olyno.skester.testing.Testing;

abstract public class AssertEffect extends Effect {

    protected Testing latestTest;

    protected Boolean isAvailable() {
        if (ScopeTest.tests.size() == 0) return false;
        this.latestTest = ScopeTest.latestTest;
        return true;
    }

}
