package org.unq.compiler.remolacha.compiler.utils;

/**
 * Created by mtejeda on 15/11/17.
 * Clase que representa los selectores de la tabla
 * cuenta con el nombre del método y la cantidad de argumentos que recibirá ese método
 */

public class CSelector {

    private String name;
    private int args;
    private String id;
/*
    public CSelector(String name, int args) {
        this.name = name;
        this.args = args;
    }*/

    public CSelector(String name, int args, String id) {
        this.name = name;
        this.args = args;
        this.id = id;
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


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
