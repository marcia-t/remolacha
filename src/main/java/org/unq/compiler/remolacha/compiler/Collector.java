package org.unq.compiler.remolacha.compiler;

import org.unq.compiler.remolacha.compiler.utils.CClass;
import org.unq.compiler.remolacha.compiler.utils.CSelector;
import org.unq.compiler.remolacha.grammar.Class;
import org.unq.compiler.remolacha.grammar.Expression;
import org.unq.compiler.remolacha.grammar.Method;
import org.unq.compiler.remolacha.grammar.Program;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by mtejeda on 15/11/17.
 * Clase que se encarga de recolectar los selectores y las clases del programa a compilar
 */

public class Collector {

    public Collector() {
    }

    /*
    * Método que recoleta las clases y las guarda en una lista con su id compilado
    * (el que usaremos en C++)*/
    public static List<CClass> collectClasses(Program program){
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


    /*
    * Método que colecta selectores de un programa
    * Primero los nombres de los métodos y luego busca dentro las expresiones
    * que se utilizan en los bloques*/
    public static List<CSelector> collectSelectors(Program program) {
        List<CSelector> cselectors = new ArrayList<>();
        CSelector printSel = new CSelector("print", 0, "sel0");
        CSelector addSel = new CSelector("add", 1, "sel1");
        cselectors.add(addSel);
        cselectors.add(printSel);
        List<Class> classes = program.getClasses();
        for (Class cl:classes) {
            List<Method> methods = cl.getMethods();
            for(Method m : methods){
                if (!exists(m, cselectors)){
                    int args = m.getParameters().size();
                    int selCounter = cselectors.size();
                    CSelector sc = new CSelector(m.getId(), args, "sel" + selCounter);
                    cselectors.add(sc);
                }
                cselectors = collectOtherMethods(cselectors, m);
            }
        }

        return cselectors;
    }

    /*
    * Método que colecta selectores dentro de las expresiones de los bloques de los métodos*/
    private static List<CSelector> collectOtherMethods(List<CSelector> cselectors, Method m) {
        List<Expression> exprs= m.getBlock();
        for (Expression exp: exprs){
            cselectors = exp.collectMessages(cselectors);
        }
        return cselectors;
    }


    /*
    * Chequea si un mensaje existe en la lista de selectores*/
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
    * Chequea si el método ya fue agregado a la lista de selectores
    * */
    public static boolean exists(Method m, List<CSelector> cselectors) {
        for (CSelector cs :
                cselectors) {
            if (m.getId().equals(cs.getName()) && m.getParameters().size() == cs.getArgs()){
                return true;
            }
        }
        return false;
    }


    /*
    * Retorna el nombre de la clase compilada
    * */
    public static String getCClassName(List<CClass> cClasses, String name) {
        for (CClass ccl : cClasses){
            if (ccl.getName().equals(name)) return ccl.getID();
        }
        return null;
    }

    /*
    * Retorna el identificador del selector (sel1, sel2....)
    * */
    public static String getSelectorId(Method m, List<CSelector> cSelectors) {
        for (CSelector cs : cSelectors) {
            if (cs.getName().equals(m.getId()) && cs.getArgs() == m.getParameters().size()){
                return cs.getId();
            }
        }
       return null;
    }

    /*
    * Retorna una tabla con los nombres de las clases compiladas y los métodos a los cuales
    * responden
    * */
    public static HashMap<String, String[]> generateTable(Program program, List<CClass> cClasses, List<CSelector> cSelectors) {
        HashMap <String, String[]> result = new HashMap();
        int size = cSelectors.size();
        for (Class c : program.getClasses()){
            String[] selectorsArray = new String[size];
            String compiledName = Collector.getCClassName(cClasses, c.getId());
            result.put(compiledName, selectorsArray);
            for (CSelector cs : cSelectors){
                if (c.existsMethod(cs)){
                    int l = Collector.getLocation(cs);
                    selectorsArray[l] = cs.getId();
                }
            }
        }
        return result;
    }

    private static int getLocation(CSelector cs) {
        String num = cs.getId().substring(3);
        return Integer.valueOf(num);
    }


}
