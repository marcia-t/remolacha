package org.unq.compiler.remolacha.grammar;

import java.util.ArrayList;
import java.util.List;

public class Program {

    protected  List<Class> classes = new ArrayList<Class>();


    public Program(List<Class> classes) {
        this.classes = classes;
    }

    public List<Class> getClasses() {
        return classes;
    }

    public void setClasses(List<Class> classes) {
        this.classes = classes;
    }
}
