package org.unq.compiler.remolacha.grammar.expressions;

import org.unq.compiler.remolacha.compiler.Collector;
import org.unq.compiler.remolacha.compiler.utils.CSelector;
import org.unq.compiler.remolacha.grammar.Class;
import org.unq.compiler.remolacha.grammar.Expression;
import org.unq.compiler.remolacha.grammar.Method;

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

    @Override
    public String compile(Method method, Class aClass, String cclass) {
        String ret = "soy un send --->";
        ret+= expr.compile(method, aClass, cclass);
        /*levantar el selector del mensate y enviarlo con los argumentos*/
       
        return ret;
    }

    @Override
    public String getTemps(Method method, Class aClass, String cclass, int j) {
        String ret = "";
        for (int i = 0; i < this.getArguments().size(); i++) {
            Expression e = this.getArguments().get(i);
            j++;
            ret+= "Objeto* tmp"+j+"= "+ e.compile(method, aClass, cclass) +"\n";
            ret+= e.getTemps(method, aClass, cclass, j);
        }

        return ret;
    }
}
