package org.unq.compiler.remolacha.grammar;

import java.util.ArrayList;
import java.util.List;

public class Class {

    protected String id;
    protected List<LocalVar> locals = new ArrayList<LocalVar>();
    protected List<Method> methods = new ArrayList<Method>();

    public Class(String id, List<LocalVar> locals, List<Method> methods) {
        this.id = id;
        this.locals = locals;
        this.methods = methods;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<LocalVar> getLocals() {
        return locals;
    }

    public void setLocals(List<LocalVar> locals) {
        this.locals = locals;
    }

    public List<Method> getMethods() {
        return methods;
    }

    public void setMethods(List<Method> methods) {
        this.methods = methods;
    }
}
