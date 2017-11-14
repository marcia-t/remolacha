package org.unq.compiler.remolacha.app;
import org.unq.compiler.remolacha.grammar.*;
import org.unq.compiler.remolacha.grammar.Class;
import org.unq.compiler.remolacha.grammar.expressions.*;

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
        Variable e  = new Variable("valor");
        LocalVar valor = new LocalVar("valor", e);
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




        Method valorActual = new Method("valorActual");

    }
}
