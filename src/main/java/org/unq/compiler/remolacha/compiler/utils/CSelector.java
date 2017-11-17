package org.unq.compiler.remolacha.compiler.utils;

/**
 * Created by mtejeda on 15/11/17.
 * Clase que representa los selectores de la tabla
 * cuenta con el nombre del método y la cantidad de argumentos que recibirá ese método
 */

public class CSelector {

    private String name;
    private int args;


    public CSelector(String name, int args) {
        this.name = name;
        this.args = args;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getArgs() {
        return args;
    }

    public void setArgs(int args) {
        this.args = args;
    }

}
