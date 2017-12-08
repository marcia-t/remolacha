package org.unq.compiler.remolacha.compiler.context;

import org.unq.compiler.remolacha.grammar.expressions.Send;

import java.util.HashMap;

/*
* Clase que va a manejar el entorno de los objetos
* temporales en cada método que se vaya a compilar.
* También tiene la lista de objetos temporales
* next es el nro de parámetro tmp que se usará próximo
* */
public class Environment {



    public static String env;
    public static int next = 0;
    public static HashMap <String, String> assignments = new HashMap<>();

    public static void clean(){
        /*vaciar todas las variables.*/
        next = 0;
        env = "";
        assignments = new HashMap<>();
    }

    public static String getNext(){
        next++;
        return "tmp"+next;
    }



    public static String getEnv() {
        return env;
    }


    /*
    * Guarda una asignación a una variable temporal en el entorno actual de ejecución
    * */
    public static String assignAndReturn(String assignment){
        if (assignments.containsKey(assignment)){
            return assignments.get(assignment);
        }
        else {
            String tmpN = Environment.getNext();

            env+="Objeto* "+tmpN+" = "+assignment+";\n";
            assignments.put(assignment, tmpN);
            return tmpN;
        }
    }

    /*Se sabe que siempre va a ser un Send*/
    public static void generateChecker(String assignment, Send send) {
        String msj = send.getID() + "/" +send.getArguments().size();
        String met = assignment.substring(14);
        met = met.substring(0, met.indexOf(']')+1);
        String ret = "if ("+met+" == NULL) {\n" +
                "   fprintf(stderr,\"El objeto no acepta el mensaje ’"+msj+"’.\\n\");\n" +
                "   exit(1);\n" +
                "}\n";
        env += ret;
    }

    /*
    * Guarda una asignación a una variable temporal en el entorno actual de ejecución
    * En el caso de que sea la asignación de un send, se agrega aademás el chequeo
    * para saber si el método está definico en la case.
    * */
    public static String assignAndReturnWCheck(String assignment, Send expr) {
        if (assignments.containsKey(assignment)){
            return assignments.get(assignment);
        }
        else {
            String tmpN = Environment.getNext();


            assignments.put(assignment, tmpN);
            generateChecker(assignment, expr);
            env+="Objeto* "+tmpN+" = "+assignment+";\n";
            return tmpN;
        }
    }
}
