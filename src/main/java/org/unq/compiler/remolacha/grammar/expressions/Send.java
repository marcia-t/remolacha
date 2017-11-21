package org.unq.compiler.remolacha.grammar.expressions;

import org.unq.compiler.remolacha.compiler.Collector;
import org.unq.compiler.remolacha.compiler.utils.CSelector;
import org.unq.compiler.remolacha.grammar.Expression;

import java.util.ArrayList;
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
}
