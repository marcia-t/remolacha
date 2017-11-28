package org.unq.compiler.remolacha.grammar.expressions;

import org.unq.compiler.remolacha.compiler.utils.CSelector;
import org.unq.compiler.remolacha.grammar.Class;
import org.unq.compiler.remolacha.grammar.Expression;
import org.unq.compiler.remolacha.grammar.Method;

import java.util.List;

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

    @Override
    public List<CSelector> collectMessages(List<CSelector> cselectors) {
        return cselectors;
    }

    @Override
    public String compile(Method method, Class aClass) {
        /*ver si ID forma parte de los parámetros o de las vars de la clase*/
        String ret = "return ";
        String compiled = "";
        for (int i = 0; i < aClass.getLocals().size(); i++) {
            if (aClass.getLocals().get(i).getId().equals(this.getID())) {
                compiled += "    PTR_TO_OBJECT(o0->varsInstancia[" + i + "])\n";
            }
        }
        for (int i = 0; i < method.getParameters().size(); i++) {
            if (this.getID().equals(method.getParameters().get(i))) {
                compiled += "    O" + i+"\n";
            }
        }
        return compiled;
    }
}
