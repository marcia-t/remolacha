package org.unq.compiler.remolacha.compiler;

import org.unq.compiler.remolacha.grammar.Program;

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
        return true;
    }

    private boolean notClassRepeated(Program p) {
        return true;
    }

    private boolean existsMain(Program p) {
        return true;
    }

}
