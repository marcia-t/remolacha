package org.unq.compiler.remolacha.compiler;

import org.unq.compiler.remolacha.compiler.utils.CClass;
import org.unq.compiler.remolacha.compiler.utils.CSelector;
import org.unq.compiler.remolacha.grammar.Class;
import org.unq.compiler.remolacha.grammar.Program;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mtejeda on 15/11/17.
 */
public class Compiler {

    private Program program;
    private Checker checker = new Checker();

    private List<CClass> cClasses = new ArrayList<>();
    private List<CSelector> cSelectors = new ArrayList<>();



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

    public void collect(){
        this.collectClasses();
        this.collectSelectors();
    }

    public void compile(){
        String code = "";
        code+= CodeHelper.getHeader();
        if (checker.checkProgram(program)){
            this.collect();
            code += CodeHelper.getClassesHeader(cClasses);
            code += CodeHelper.getNativeClassesDef();
            code += this.compileClassesContructors();
            //code += this.compileMethods();
        }
        else {
            code = "Falló la compilación";
        }
        System.out.println(code);
    }

    /*
    * Compila los métodos de cada clase
    * TODO: ver si compilar o no los métodos de main
    * */
    private String compileMethods() {
        String methods = "";
        for (int i = 0; i < program.getClasses().size(); i++) {
            methods += this.compileClassMethods(program.getClasses().get(i));
        }
        return null;
    }

    /*
    * Compila los métodos de una clase
    * */
    private String compileClassMethods(Class aClass) {

        return null;
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
