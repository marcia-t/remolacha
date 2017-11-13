package org.unq.compiler.remolacha.grammar.expressions;

import org.unq.compiler.remolacha.grammar.Expression;

public class Variable extends Expression {

    private String ID;

    public Variable(String ID) {
        this.ID = ID;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}
