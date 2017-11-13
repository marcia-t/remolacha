package org.unq.compiler.remolacha.grammar;

public class LocalVar {

    protected String id;
    protected Expression e;

    public LocalVar(String id, Expression e) {
        this.id = id;
        this.e = e;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Expression getE() {
        return e;
    }

    public void setE(Expression e) {
        this.e = e;
    }
}
