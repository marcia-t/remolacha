package org.unq.compiler.remolacha.compiler;

import org.unq.compiler.remolacha.grammar.*;
import org.unq.compiler.remolacha.grammar.Class;
import org.unq.compiler.remolacha.grammar.expressions.Variable;

import java.util.ArrayList;

/**
 * Created by mtejeda on 14/11/17.
 */
public class Checker {

    public boolean checkProgram(Program p){
        return (existsMain(p)
                && notClassRepeated(p)
                && notSameMethodAndSameSelector(p)
                && notSameVarsNames(p)
                && notParamRepeated(p)
                && notSameParameterAndLocal(p)
                && checkAssignments(p)
                && checkNewClassesAssignments(p));
    }

    private boolean checkNewClassesAssignments(Program p) {
        return true;
    }

    private boolean checkAssignments(Program p) {
        return true;
    }

    private boolean notSameParameterAndLocal(Program p) {
        return true;
    }

    private boolean notParamRepeated(Program p) {
        return true;
    }

    private boolean notSameVarsNames(Program p) {
        for (Class c :
                p.getClasses()) {
            ArrayList<String> vars = new ArrayList<>();
            for (LocalVar l :
                    c.getLocals()) {
                if (vars.contains(l.getId())){
                    System.out.println("Hay dos variables locales con el mismo nombre: "
                            +c.getId()+", "+l.getId()+".\n" +
                            "Se aborta la compilación");
                    return false;
                }
            }
        }
        return true;
    }

    private boolean notSameMethodAndSameSelector(Program p) {
        for (Class c :
                p.getClasses()) {
            if (!Checker.notSameMethodAndSelectorByClass(c)){
                return false;
            }
        }
        return  true;
    }

    private static boolean notSameMethodAndSelectorByClass(Class c) {
        ArrayList<String> methodsSel = new ArrayList<>();
        String sel = "";
        for (Method m :
                c.getMethods()) {
            sel = m.getId()+m.getParameters().size();
            if (methodsSel.contains(sel)){
                System.out.println("Hay dos métodos con el mismo nombre y can parámetros, " +
                        "se aborta la compilación");
                return false;
            }
        }
        return true;
    }

    private boolean notClassRepeated(Program p) {
        ArrayList<String> classes = new ArrayList<>();
        for (Class c :
                p.getClasses()) {
            if (classes.contains(c.getId())) {
                System.out.println("Hay dos clases con el mismo nombre, se aborta la compilación");
                return false;
            }
            else classes.add(c.getId());
        }
        return true;
    }

    private boolean existsMain(Program p) {
        for (Class c : p.getClasses()) {
            if (c.getId().equals("Main")) {
                for (Method m : c.getMethods()) {
                    if (m.getId().equals("main"))  return true;
                }
            }
        }
        System.out.println("El programa no tiene una clase main o bien la misma no tiene el método 'main' implementado.\n" +
                "Se aborta la compilación.");
        return false;
    }

}
