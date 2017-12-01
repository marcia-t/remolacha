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
    public String compile(Method method, Class aClass, String cclass) {
        String ret = "";
        for (int i = 0; i < aClass.getLocals().size(); i++) {
            if (aClass.getLocals().get(i).getId().equals(this.getID())) {
                ret += "o0->varsInstancia[" + i + "] =";
            }
        }
        for (int i = 0; i < method.getParameters().size(); i++) {
            if (this.getID().equals(method.getParameters().get(i))) {
                ret += "o" + i+1 + " =";
            }
        }
        ret += this.getExpr().compile(method, aClass, cclass);
        return ret;
    }

    @Override
    public String getTemps(Method method, Class aClass, String cclass, int i) {
        return expr.getTemps(method, aClass, cclass, i);
    }


}
