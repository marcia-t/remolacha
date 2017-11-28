package org.unq.compiler.remolacha.grammar.expressions;

import org.unq.compiler.remolacha.compiler.utils.CSelector;
import org.unq.compiler.remolacha.grammar.Class;
import org.unq.compiler.remolacha.grammar.Expression;
import org.unq.compiler.remolacha.grammar.Method;

import java.util.List;

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

    @Override
    public List<CSelector> collectMessages(List<CSelector> cselectors) {
        cselectors = expr.collectMessages(cselectors);
        return cselectors;
    }

    @Override
    public String compile(Method parameters, Class aClass, String cclass) {

        return null;
    }


}
