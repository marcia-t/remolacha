package org.unq.compiler.remolacha.grammar.expressions;

import org.unq.compiler.remolacha.compiler.Collector;
import org.unq.compiler.remolacha.compiler.context.Environment;
import org.unq.compiler.remolacha.compiler.utils.CSelector;
import org.unq.compiler.remolacha.grammar.Class;
import org.unq.compiler.remolacha.grammar.Expression;
import org.unq.compiler.remolacha.grammar.Method;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by mtejeda on 13/11/17.
 */
public class Send extends Expression {

    private Expression expr;
    private String ID;
    private List<Expression> arguments = new ArrayList<>();

    public Send(Expression expr, String ID, List<Expression> arguments) {
        this.expr = expr;
        this.ID = ID;
        this.arguments = arguments;
    }


    /*Send puede no tener argumentos*/
    public Send(Expression expr, String ID) {
        this.expr = expr;
        this.ID = ID;
    }

    public Expression getExpr() {
        return expr;
    }

    public void setExpr(Expression expr) {
        this.expr = expr;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public List<Expression> getArguments() {
        return arguments;
    }

    public void setArguments(List<Expression> arguments) {
        this.arguments = arguments;
    }

    @Override
    public List<CSelector> collectMessages(List<CSelector> cselectors) {
        String id = this.getID();
        int sz = this.getArguments().size();
        if (!Collector.existsMessage(id, sz, cselectors)){
            int selCounter = cselectors.size();
            CSelector cs = new CSelector(id, sz, "sel" + selCounter);
            cselectors.add(cs);
        }
        cselectors = this.expr.collectMessages(cselectors);
        return cselectors;
    }

    @Override
    public String compile(Method method, Class aClass, String cclass, Boolean lastLine, HashMap<String, String[]> table, List<CSelector> cSelectors) {
        String ret = "";
        String args ="";
        String receiver = this.getExpr().compile(method,aClass,cclass,lastLine,table,cSelectors);

        String tmp = Environment.assignAndReturn(receiver);

        for (Expression e : this.getArguments()) {
            String arg =e.compile(method,aClass,cclass,lastLine,table,cSelectors);
            String t = Environment.assignAndReturn(arg);
            args+=", "+t;
        }

        String selector = Collector.getSelectorIdByMessage(this.getID(), this.getArguments().size(), cSelectors);
        int nbr = Integer.parseInt(selector.substring(3));

        ret += "PTR_TO_METHOD("+tmp+"->clase->metodos["+nbr+"])("+tmp+args+")";
        return ret;
    }
}
