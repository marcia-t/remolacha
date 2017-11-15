package org.unq.compiler.remolacha.compiler;

import org.unq.compiler.remolacha.grammar.Program;
import org.unq.compiler.remolacha.compiler.utils.CClass;
import org.unq.compiler.remolacha.compiler.utils.CSelector;

import java.util.List;

/**
 * Created by mtejeda on 15/11/17.
 */
public class Compiler {

    private Program program;
    private Collector collector = new Collector();


    public Compiler(Program program) {
        this.program = program;
    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    public Collector getCollector() {
        return collector;
    }

    public void setCollector(Collector collector) {
        this.collector = collector;
    }

    public List<CClass> collectClasses(){
        return this.collector.collectClasses(program);
    }

    public List<CSelector> collectSelectors(){
        return this.collector.collectSelectors(program);
    }


}
