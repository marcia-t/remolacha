package org.unq.compiler.remolacha.grammar.expressions;

import org.unq.compiler.remolacha.compiler.utils.CSelector;
import org.unq.compiler.remolacha.grammar.Class;
import org.unq.compiler.remolacha.grammar.Expression;
import org.unq.compiler.remolacha.grammar.Method;

import java.util.HashMap;
import java.util.List;

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

    @Override
    public List<CSelector> collectMessages(List<CSelector> cselectors) {
        return cselectors;
    }

    @Override
    public String compile(Method method, Class aClass, String cclass, Boolean lastLine, HashMap<String, String[]> table, List<CSelector> cSelectors) {
        if (lastLine){
            return "return constructor_"+cclass+"()";
        }
        else return "constructor_"+cclass+"()";
    }

    /*@Override
    public String getTemps(Method method, Class aClass, String cclass, int i) {
        return "";
    }*/
}
