package org.unq.compiler.remolacha.compiler.context;

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
}
