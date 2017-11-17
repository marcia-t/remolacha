package org.unq.compiler.remolacha.app;
import org.unq.compiler.remolacha.compiler.Compiler;
import org.unq.compiler.remolacha.compiler.utils.CSelector;
import org.unq.compiler.remolacha.grammar.*;
import org.unq.compiler.remolacha.grammar.Class;
import org.unq.compiler.remolacha.grammar.expressions.*;
import org.unq.compiler.remolacha.compiler.utils.CClass;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mtejeda on 13/11/17.
 */
public class App {

    public static void main(String[] args) {
        Class contador = new Class("Contador");
        Class main =  new Class("Main");
        Self self = new Self();


        /*Variables de contador*/
        List<LocalVar> contadorLocals = new ArrayList<LocalVar>();
        LocalVar valor = new LocalVar("valor");
        contadorLocals.add(valor);
        contador.setLocals(contadorLocals);

        /*Métodos de Contador*/

        /*Inicializar*/
        Method inicializar = new Method("inicializar");
        /*Parámetros de inicializar*/
        List<String> parametersInicializar = new ArrayList<>();
        parametersInicializar.add("valorInicial");
        /*Bloque de inicializar*/
        List<Expression> blockInicializar = new ArrayList<>();
        /*Expresiones del bloque de inicializar*/
        Variable var = new Variable("valorInicial");
        Set e1 = new Set("valor", var);

        blockInicializar.add(e1);
        blockInicializar.add(self);
        inicializar.setBlock(blockInicializar);
        inicializar.setParameters(parametersInicializar);

        contador.addMethod(inicializar);

        /*Incrementar*/
        Method incrementar = new Method("incrementar");
        /*Bloque de incrementar*/
        List<Expression> blockIncrementar = new ArrayList<>();
        /*Expresiones del bloque de incrementar*/
        ConstantNumber n1 = new ConstantNumber(1);
        List<Expression> argumentse3 = new ArrayList<>();
        argumentse3.add(n1);
        Send e3 = new Send(self, "incrementarEn", argumentse3);
        blockIncrementar.add(e3);
        incrementar.setBlock(blockIncrementar);

        contador.addMethod(incrementar);

        /*IncrementarEn*/
        Method incrementarEn = new Method ("incrementarEn");
         /*Bloque de incrementaren*/
        List<Expression> blockIncrementarEn = new ArrayList<>();
         /*Parámetros de incrementarEn*/
        List<String> parametersIncrementarEn = new ArrayList<>();
        parametersIncrementarEn.add("x");
        incrementarEn.setParameters(parametersIncrementarEn);
        /*Expresiones del bloque de incrementarEn*/
        Variable e6 = new Variable("valor");
        List<Expression> lArg = new ArrayList<>();
        Variable e7 = new Variable("x");
        lArg.add(e7);
        Send e4 = new Send(e6, "add", lArg) ;
        Set e5 = new Set("valor", e4);
        blockIncrementarEn.add(e5);
        incrementarEn.setBlock(blockIncrementarEn);

        contador.addMethod(incrementarEn);



        /*valorActual*/
        Method valorActual = new Method("valorActual");
        /*Bloque de valorActual*/
        List<Expression> blockValorActual = new ArrayList<>();
        Variable e8 = new Variable("valor");
        blockValorActual.add(e8);
        valorActual.setBlock(blockValorActual);

        contador.addMethod(valorActual);


        /*Variables de clase main*/
        List<LocalVar> mainLocals = new ArrayList<LocalVar>();
        LocalVar c = new LocalVar("c");

        main.addLocalVar(c);

        /*Métodos de main*/
        Method mainM = new Method("main");

        /*Bloque de método main*/
        List<Expression> blockMain = new ArrayList<>();
        /*Expresiones de línea 1*/
        /*set c = new Contador . inicializar (0)*/
        Expression e10 = new New("Contador");
        List<Expression> e12 = new ArrayList<>();
        ConstantNumber e13 = new ConstantNumber(0);
        e12.add(e13);
        Expression e11 = new Send (e10, "inicializar", e12);
        Set e9 = new Set ("c", e11);

        blockMain.add(e9);

        /*Expresiones línea 2*/
        /*c . incrementar (). valorActual (). print ()*/
        Variable c2 = new Variable("c");
        Send e14 =  new Send(c2, "incrementar");
        Send e15 = new Send(e14, "valorActual");
        Send e16 = new Send(e15, "print");

        blockMain.add(e16);

        /*Expresiones línea 3*/
        /*c . incrementar (). valorActual (). print ()*/
        Variable c3 = new Variable("c");
        Send e17 =  new Send(c3, "incrementar");
        Send e18 = new Send(e17, "valorActual");
        Send e19 = new Send(e18, "print");

        blockMain.add(e19);
        /*Expresiones línea 4*/
        /*c . incrementarEn (10). valorActual (). print ()*/
        ConstantNumber n2 = new ConstantNumber(10);

        List<Expression> lArg2 = new ArrayList<>();
        lArg2.add(n2);
        Send e20 =  new Send(c2, "incrementarEn", lArg2);
        //Send e20 =  new Send(c2, "incrementarEn PEPE", lArg2);
        Send e21 = new Send(e20, "valorActual");
        Send e22 = new Send(e21, "print");

        blockMain.add(e22);
        mainM.setBlock(blockMain);
        main.addMethod(mainM);

        List<Class> classes = new ArrayList<>();
        classes.add(main);
        classes.add(contador);
        Program p = new Program(classes);

        Compiler comp = new Compiler(p);

        comp.collect();





    }
}
