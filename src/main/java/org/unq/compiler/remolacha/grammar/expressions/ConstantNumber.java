package org.unq.compiler.remolacha.grammar.expressions;

import org.unq.compiler.remolacha.compiler.utils.CSelector;
import org.unq.compiler.remolacha.grammar.Class;
import org.unq.compiler.remolacha.grammar.Expression;
import org.unq.compiler.remolacha.grammar.Method;

import java.util.List;

/**
 * Created by mtejeda on 13/11/17.
 */
public class ConstantNumber extends Expression {

    private int NUM;

    public ConstantNumber(int NUM) {
        this.NUM = NUM;
    }

    public int getNUM() {
        return NUM;
    }

    public void setNUM(int NUM) {
        this.NUM = NUM;
    }

    @Override
    public List<CSelector> collectMessages(List<CSelector> cselectors) {
        return cselectors;
    }

    @Override
    public String compile(Method parameters, Class aClass) {
        return "constructor_cls0("+this.getNUM()+");";
    }
}
