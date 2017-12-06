package org.unq.compiler.remolacha.compiler;

import org.unq.compiler.remolacha.grammar.*;
import org.unq.compiler.remolacha.grammar.Class;

import java.util.ArrayList;

/**
 * Created by mtejeda on 14/11/17.
 */
public class Checker {

    /*TODO: CHECK!! */
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
        return true;
    }

    private boolean notSameMethodAndSameSelector(Program p) {

        return  true;
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
        System.out.println("El programa no tiene una clase main o bien la misma no tiene el método 'main' implementado");
        return false;
    }

}
