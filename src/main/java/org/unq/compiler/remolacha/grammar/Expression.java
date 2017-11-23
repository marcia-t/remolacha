package org.unq.compiler.remolacha.grammar;

import org.unq.compiler.remolacha.compiler.utils.CSelector;

import java.util.List;

public abstract class Expression {

    public abstract List<CSelector> collectMessages(List<CSelector> cselectors);

    public abstract String compile(List<String> parameters);

}
