package org.unq.compiler.remolacha.compiler;

import org.unq.compiler.remolacha.grammar.Program;
import org.unq.compiler.remolacha.compiler.utils.CClass;
import org.unq.compiler.remolacha.compiler.utils.CSelector;

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
            code += this.compileClassesDefinition();
            code += CodeHelper.getNativeClassesDef();
            code += this.compileClassesContructors();
        }
        else {
            code = "Falló la compilación";
        }
        System.out.println(code);
    }

    private String compileClassesContructors() {
        
        return null;
    }


    /*
    * Método que armará las definiciones de clases
    * Ej:
    *  Clase* cls2 ;
    *  Clase* cls3 ;
    **/
    private String compileClassesDefinition() {
        return CodeHelper.getClassesHeader(cClasses);
    }

}
