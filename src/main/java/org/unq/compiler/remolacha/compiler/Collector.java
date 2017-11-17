package org.unq.compiler.remolacha.compiler;

import org.unq.compiler.remolacha.grammar.Class;
import org.unq.compiler.remolacha.grammar.Expression;
import org.unq.compiler.remolacha.grammar.Method;
import org.unq.compiler.remolacha.grammar.Program;
import org.unq.compiler.remolacha.compiler.utils.CClass;
import org.unq.compiler.remolacha.compiler.utils.CSelector;
import org.unq.compiler.remolacha.grammar.expressions.Send;
import sun.security.pkcs11.wrapper.CK_SLOT_INFO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mtejeda on 15/11/17.
 * Clase que se encarga de recolectar los selectores y las clases del programa a compilar
 */

public class Collector {

    public Collector() {
    }

    public List<CClass> collectClasses(Program program){
        List<CClass> cclasses = new ArrayList<>();
        CClass int_ = new CClass("Int", "cls0");
        CClass string_ = new CClass("String", "cls1");
        cclasses.add(int_);
        cclasses.add(string_);
        int classCounter = 2;

        List<Class> classes = program.getClasses();
        for (Class cl:classes) {
            String name = cl.getId();
            String ID = "cls"+classCounter;
            CClass cc = new CClass(name, ID);
            cclasses.add(cc);
            classCounter++;
        }
        return cclasses;
    }

    public List<CSelector> collectSelectors(Program program) {
        List<CSelector> cselectors = new ArrayList<>();
        CSelector addSel = new CSelector("add", 1);
        CSelector printSel = new CSelector("print", 0);
        cselectors.add(addSel);
        cselectors.add(printSel);
        List<Class> classes = program.getClasses();
        for (Class cl:classes) {
            List<Method> methods = cl.getMethods();
            for(Method m : methods){
                if (!exists(m, cselectors)){
                    int args = m.getParameters().size();
                    CSelector sc = new CSelector(m.getId(), args);
                    cselectors.add(sc);
                }
                cselectors = this.collectOtherMethods(cselectors, m);
            }
        }

        return cselectors;
    }

    private List<CSelector> collectOtherMethods(List<CSelector> cselectors, Method m) {
        List<Expression> exprs= m.getBlock();
        for (Expression exp: exprs){
            cselectors = exp.collectMessages(cselectors);
        }
        return cselectors;
    }

    public static boolean existsMessage(String id, int sz, List<CSelector> cselectors) {
        for (CSelector cs :
                cselectors) {
            if (id.equals(cs.getName()) && sz == cs.getArgs()){
                return true;
            }
        }
        return false;
    }


    /*
    * Chequea si el método ya fue agregado a la lista de selectores*/
    public static boolean exists(Method m, List<CSelector> cselectors) {
        for (CSelector cs :
                cselectors) {
            if (m.getId().equals(cs.getName()) && m.getParameters().size() == cs.getArgs()){
                return true;
            }
        }
        return false;

    }
}
