package org.unq.compiler.remolacha.grammar.expressions;

import org.unq.compiler.remolacha.grammar.Expression;

/**
 * Created by mtejeda on 13/11/17.
 */
public class New extends Expression {

    private String ID;

    public New(String ID) {
        this.ID = ID;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}
