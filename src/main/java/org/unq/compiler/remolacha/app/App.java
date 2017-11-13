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





        Method valorActual = new Method("valorActual");

    }
}
