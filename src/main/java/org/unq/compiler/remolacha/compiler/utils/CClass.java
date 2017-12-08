package org.unq.compiler.remolacha.compiler.utils;

/**
 * Created by mtejeda on 15/11/17.
 *
 * Clase que representa los nombres de las clases de Remolacha compiladas
 */
public class CClass {

    private String name;
    private String ID;

    public CClass(String name, String ID) {
        this.name = name;
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public String getID() {
        return ID;
    }


}
