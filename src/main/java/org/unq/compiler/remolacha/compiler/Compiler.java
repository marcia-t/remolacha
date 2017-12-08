package org.unq.compiler.remolacha.compiler;

import org.unq.compiler.remolacha.compiler.utils.CClass;
import org.unq.compiler.remolacha.compiler.utils.CSelector;
import org.unq.compiler.remolacha.grammar.Class;
import org.unq.compiler.remolacha.grammar.Program;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by mtejeda on 15/11/17.
 */
public class Compiler {

    private Program program;
    private Checker checker = new Checker();

    public static List<CClass> cClasses = new ArrayList<>();
    public static List<CSelector> cSelectors = new ArrayList<>();
    public static HashMap<String, String[]> table = new HashMap<>();


    public Compiler(Program program) {
        this.program = program;
    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    public void collectClasses(){
        cClasses = Collector.collectClasses(program);
    }

    public void collectSelectors(){
        cSelectors = Collector.collectSelectors(program);
    }

    private void generateTable() {
        table = Collector.generateTable(program, cClasses, cSelectors);
    }

    public void collect(){
        this.collectClasses();
        this.collectSelectors();
        this.generateTable();
    }


    /*
    * Compila todo el programa.
    * */
    public void compile(){
        String code = "";
        code+= CodeHelper.getHeader();
        if (checker.checkProgram(program)){
            this.collect();
            code += CodeHelper.getClassesHeader(cClasses);
            code += CodeHelper.getNativeClassesDef();
            code += this.compileClassesContructors();
            code += this.compileMethods();
            code += this.compileInitialization();
            System.out.println(code);
        }
        else {
            System.out.println("Falló la compilación");
        }
    }

    /*
    * Compila el método que inicializará todas las clases
    * */
    private String compileInitialization() {
        int methodsSize = this.cSelectors.size();
        String ret ="int main () {\n";
        ret += CodeHelper.getNativeInitializations(methodsSize);
        for (String c : table.keySet()) {
            ret+=CodeHelper.getInitialization(table.get(c), c, methodsSize);
        }
        ret += CodeHelper.executeMain();
        ret += "    return 0;\n";
        ret += "}\n";
        return ret;
    }

    /*
    * Compila los métodos de cada clase
    * */
    private String compileMethods() {
        String methods = "";
        for (int i = 0; i < program.getClasses().size(); i++) {
            Class aClass = program.getClasses().get(i);
            methods += CodeHelper.compileMethods(aClass, cClasses, cSelectors, table);
        }
        return methods;
    }


    /*
    * Compila los constructores de todas las clases
    * */
    private String compileClassesContructors() {
        String constructors = "";
        for(Class cl :  program.getClasses()){
            String compiledName = Collector.getCClassName(cClasses, cl.getId());
            constructors += CodeHelper.getClassConstructor(cl, compiledName);
            constructors += "\n";
        }
        return constructors;
    }




}
