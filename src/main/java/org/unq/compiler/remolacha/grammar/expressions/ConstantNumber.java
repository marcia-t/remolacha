package org.unq.compiler.remolacha.grammar.expressions;

import org.unq.compiler.remolacha.grammar.Expression;

/**
 * Created by mtejeda on 13/11/17.
 */
public class ConstantNumber extends Expression {

    private int NUM;

    public ConstantNumber(int NUM) {
        this.NUM = NUM;
    }

    public int getNUM() {
        return NUM;
    }

    public void setNUM(int NUM) {
        this.NUM = NUM;
    }
}
