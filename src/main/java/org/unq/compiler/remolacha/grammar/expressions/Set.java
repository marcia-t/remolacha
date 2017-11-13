package org.unq.compiler.remolacha.grammar.expressions;

import org.unq.compiler.remolacha.grammar.Expression;

public class Set extends Expression {

    private String ID;
    private Expression expr;

    public Set(String ID, Expression expr) {
        this.ID = ID;
        this.expr = expr;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public Expression getExpr() {
        return expr;
    }

    public void setExpr(Expression expr) {
        this.expr = expr;
    }
}