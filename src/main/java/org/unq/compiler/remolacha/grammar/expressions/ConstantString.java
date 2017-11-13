package org.unq.compiler.remolacha.grammar.expressions;

import org.unq.compiler.remolacha.grammar.Expression;

/**
 * Created by mtejeda on 13/11/17.
 */
public class ConstantString extends Expression{

    private String STRING;

    public ConstantString(String STRING) {
        this.STRING = STRING;
    }

    public String getSTRING() {
        return STRING;
    }

    public void setSTRING(String STRING) {
        this.STRING = STRING;
    }
}
