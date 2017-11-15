package org.unq.compiler.remolacha.compiler.utils;

/**
 * Created by mtejeda on 15/11/17.
 */
public class CSelector {

    private String name;
    private int args;
    private String ID;

    public CSelector(String name, int args, String selector) {
        this.name = name;
        this.args = args;
        this.ID = selector;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getArgs() {
        return args;
    }

    public void setArgs(int args) {
        this.args = args;
    }

    public String getSelector() {
        return ID;
    }

    public void setSelector(String selector) {
        this.ID = selector;
    }
}
