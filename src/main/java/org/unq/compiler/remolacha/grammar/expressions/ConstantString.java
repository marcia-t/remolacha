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
public class ConstantString extends Expression{

    private String STRING;

    public ConstantString(String STRING) {
        this.STRING = STRING;
    }

    public String getSTRING() {
        return STRING;
    }

    public void setSTRING(String STRING) {
        this.STRING = STRING;
    }

    @Override
    public List<CSelector> collectMessages(List<CSelector> cselectors) {
        return cselectors;
    }

    @Override
    public String compile(Method method, Class aClass, String cclass, Boolean lastLine, HashMap<String, String[]> table, List<CSelector> cSelectors) {
        if (lastLine) {
            return "return constructor_cls1(\""+this.getSTRING()+"\")";
        }
        else return  "constructor_cls1(\""+this.getSTRING()+"\")";
    }

    /*@Override
    public String getTemps(Method method, Class aClass, String cclass, int i) {
        return "";
    }*/
}
