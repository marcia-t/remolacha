package org.unq.compiler.remolacha.compiler;

import org.unq.compiler.remolacha.grammar.Class;
import org.unq.compiler.remolacha.grammar.*;
import org.unq.compiler.remolacha.grammar.expressions.New;
import org.unq.compiler.remolacha.grammar.expressions.Set;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

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
        for (Class c :
                p.getClasses()) {
            for (Method m :
                    c.getMethods()) {
                for (Expression e :
                        m.getBlock()) {
                    if (e instanceof New){
                        if (!containsClassDef(p, ((New) e).getID())){
                            System.out.println("El programa no tiene definida la clase "+((New) e).getID()+", se " +
                                    "aborta la compilación");
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    private boolean containsClassDef(Program p, String id) {
        for (Class c :
                p.getClasses()) {
            if (c.getId().equals(id))
                return true;
        }
        return false;
    }

    private boolean checkAssignments(Program p) {
        for (Class c :
                p.getClasses()) {
            for (Method m :
                    c.getMethods()) {
                for (Expression e :
                        m.getBlock()) {
                    if (e instanceof Set){
                        if (!notInScope(((Set) e).getID(), c,m)) return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean notInScope(String id, Class c, Method m) {
        if (!classContainsLocalVar(c,id) && !methodContainsParam(m,id)){
            System.out.println("La variable "+id+" se está asignando fuera de scope en el método "+m.getId()+ " " +
                    "de la clase "+c.getId()+", se aborta la compilación.");
            return false;
        }
        return true;
    }

    private boolean methodContainsParam(Method m, String id) {
        for (String param :
                m.getParameters()) {
            if (param.equals(id)) return true;
        }
        return false;
    }

    private boolean classContainsLocalVar(Class c, String id) {
        for (LocalVar lv :
                c.getLocals()) {
            if (lv.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }



    private boolean notSameParameterAndLocal(Program p) {
        for (Class c :
                p.getClasses()) {
            for (LocalVar lv :
                    c.getLocals()) {
                if (!notExistsInMethods(lv,c)){
                    System.out.println("La clase "+c.getId()+" tiene el mismo nombre de variable local " +
                            "("+lv.getId()+") en un parámetro de método. Se aborta la compilación.");
                    return false;
                }
            }
        }
        return true;
    }

    private boolean notExistsInMethods(LocalVar lv, Class c) {
        for (Method m :
                c.getMethods()) {
            for (String param :
                    m.getParameters()) {
                if (param.equals(lv.getId())) return false;
            }
        }
        return true;
    }

    private boolean notParamRepeated(Program p) {
        for (Class c :
                p.getClasses()) {
            if (!notParamRepeatedInClass(c)) {
                return false;
            }
        }
        return true;
    }

    private boolean notParamRepeatedInClass(Class c) {
        for (Method m :
                c.getMethods()) {
            List<String> params    = m.getParameters();
            java.util.Set<String> set = new HashSet<String>(params);
            if(set.size() < params.size()){
                System.out.println("El método "+m.getId()+" de la clase "+c.getId()+"tiene " +
                        "dos parámetros con el mismo nombre, se aborta la compilación");
                return false;
            }
        }
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
